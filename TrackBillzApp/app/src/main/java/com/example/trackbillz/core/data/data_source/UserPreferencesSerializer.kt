package com.example.trackbillz.core.data.data_source

import androidx.datastore.core.Serializer
import com.example.trackbillz.core.data.utils.Crypto
import com.example.trackbillz.core.domain.model.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

object UserPreferencesSerializer: Serializer<UserPreferences> {
    override val defaultValue: UserPreferences
        get() = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        val encryptedBytes = withContext(Dispatchers.IO){
            input.use {
                it.readBytes()
            }
        }
        val encryptedBytesDecoded = Base64.getDecoder().decode(encryptedBytes)
        val decryptedBytes = Crypto.decrypt(encryptedBytesDecoded)
        val decryptedJson = decryptedBytes.decodeToString()
        return  Json.decodeFromString(decryptedJson)
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        val json = Json.encodeToString(t)
        val bytes = json.toByteArray()
        val encryptedByte = Crypto.encrypt(bytes)
        val encryptedBytesBase64 = Base64.getEncoder().encode(encryptedByte)
        withContext(Dispatchers.IO){
            output.use {
                it.write(encryptedBytesBase64)
            }
        }
    }
}