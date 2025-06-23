package com.example.trackbillz.auth_feature.google_sign.presentation

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.trackbillz.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigInteger
import java.security.MessageDigest
import android.util.Base64
import com.example.trackbillz.core.domain.model.UserInfo

class AccountManager(
    private val activity: Activity
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(activity)
    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    suspend fun signInWithGoogle() : GoogleSignInResult {
        return try{

            remoteConfig.fetchAndActivate().await()
            val allowedEmails = parseAllowedEmails(remoteConfig.getString("allowed_default_emails"))

            val response = credentialManager.getCredential(
                context = activity,
                request = getSignInRequest()
            )

            val googleCredential = GoogleIdTokenCredential.createFrom(response.credential.data)
            val idToken = googleCredential.idToken

            val email = googleCredential.id
            val hashedEmail = md5(input = email)


            Log.d("AccountManager", "Email $email ")
            Log.d("AccountManager", "Hashed Email $hashedEmail")
            Log.d("AccountManager", "Allowed Email $allowedEmails")
            if(hashedEmail !in allowedEmails){
                Log.d("AccountManager", "Email $email is not allowed.")
                Log.d("AccountManager", "Hashed Email $hashedEmail is not allowed.")
                return GoogleSignInResult.NotAuthorized(email)
            }
            val payload = decodeIdToken(idToken)

            val firebaseCredential = GoogleAuthProvider.getCredential(googleCredential.idToken,null)
            val auth = firebaseAuth.signInWithCredential(firebaseCredential).await()
            val info = getUserInfo(payload = payload).copy(userId = auth?.user?.uid)
            Log.d("AccountManager","Success with UserInfo: $info")
            GoogleSignInResult.Success(userInfo = info)
        }catch (e: GetCredentialCancellationException){
            e.printStackTrace()
            Log.d("AccountManager","error ${e.localizedMessage}")
            GoogleSignInResult.Cancelled
        }catch (e: GetCredentialException){
            e.printStackTrace()
            Log.d("AccountManager","error ${e.localizedMessage}")
            GoogleSignInResult.Failure(e.localizedMessage ?: "")
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("AccountManager","error ${e.localizedMessage}")
            GoogleSignInResult.Failure(e.localizedMessage ?: "")
        }
    }

    private fun getSignInRequest() : GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(activity.getString(R.string.web_client_id))
            .build()

        // It packages the Google Sign-In option inside a request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return request
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.trim().lowercase().toByteArray()))
            .toString(16).padStart(32, '0')
    }

    private fun parseAllowedEmails(jsonString: String): List<String> {
        return try {
            val jsonArray = JSONArray(jsonString)
            List(jsonArray.length()) { index -> jsonArray.getString(index) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun decodeIdToken(idToken: String): JSONObject {
        val parts = idToken.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Invalid ID token")

        val payload = parts[1]
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
        val decodedString = String(decodedBytes, Charsets.UTF_8)
        return JSONObject(decodedString)
    }

    fun getUserInfo(payload: JSONObject): UserInfo {
        val email = payload.optString("email")
        val name = payload.optString("name")
        val picture = payload.optString("picture")
        val givenName = payload.optString("given_name")
        val familyName = payload.optString("family_name")

        return UserInfo(
            email = email,
            name = name,
            profileURI = picture,
            familyName = familyName,
            giveName = givenName
        )
    }
}