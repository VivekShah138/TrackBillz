package com.example.trackbillz.core.data.repository.remote


import android.content.Context
import android.util.Log
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.core.domain.repository.remote.FirebaseRemoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.trackbillz.add_bills.data.data_source.DeleteAllBillsFromCloudDatabaseWorker
import com.example.trackbillz.add_bills.data.data_source.InsertAllBillsToLocalDatabaseWorker
import com.example.trackbillz.add_bills.data.data_source.UploadAllBillsToCloudDatabaseWorker
import com.google.firebase.firestore.Source

class FirebaseRemoteRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: Context
): FirebaseRemoteRepository {

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun insertBill(bill: Bill) {
        try{
            val billId = bill.billId.toString()

            firestore.collection("shared_data")
                .document(billId)
                .set(bill.copy(isSynced = true))
                .await()
        }catch(e: Exception){
            Log.e("FirestoreAddRemoteBill", "Error inserting bill", e)
            throw e
        }
    }

    override suspend fun insertMultipleBills() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UploadAllBillsToCloudDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "upload_local_bills_to_cloud",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun syncRemoteBillsToLocal() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<InsertAllBillsToLocalDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "insert_remote_bills_to_local",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override suspend fun deleteBill(billId: Int) {
        try {
            firestore.collection("shared_data")
                .document(billId.toString())
                .delete()
                .await()
        } catch (e: Exception) {
            Log.e("FirestoreDeleteRemoteBill", "Error deleting bill $billId", e)
            throw e
        }
    }

    override suspend fun deleteMultipleBills() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DeleteAllBillsFromCloudDatabaseWorker>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                backoffDelay = 30, TimeUnit.SECONDS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "delete_bills_from_cloud",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }


    override suspend fun getBill(): List<Bill> {
        return try {
            val snapshot = firestore.collection("shared_data")
                .get(Source.SERVER)
                .await()

            snapshot.documents.mapNotNull { doc ->
                val bill = doc.toObject(Bill::class.java)
                val id = doc.id.toIntOrNull()
                if (bill != null && id != null) {
                    bill.copy(billId = id)
                } else null
            }
        } catch (e: Exception) {
            Log.e("FirestoreGetRemoteBill", "Failed to get transaction from cloud: ${e.localizedMessage}")
            emptyList()
        }
    }
}