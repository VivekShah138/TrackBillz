package com.example.trackbillz.add_bills.data.data_source

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.trackbillz.add_bills.domain.usecases.AddBillsLocalUseCaseWrapper
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class UploadAllBillsToCloudDatabaseWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters), KoinComponent  {

    private val addBillsLocalUseCaseWrapper: AddBillsLocalUseCaseWrapper by inject()

    override suspend fun doWork(): Result {
        Log.d("WorkManagerUploadBills", "Worker started Upload All Billss To Cloud")


        return try {
            val allLocalBills = addBillsLocalUseCaseWrapper.getAllBills().first().filter { bill ->
                !bill.isSynced
            }

            Log.d("WorkManagerUploadBills", "allLocalTransactions $allLocalBills")

            if (allLocalBills.isEmpty()) {
                Log.d("WorkManagerUploadBills", "No transactions to sync.")
                return Result.failure()

            }
            else{

                allLocalBills.forEach { bill ->
                    val billId = bill.billId
                    val billWithId = bill.copy(billId = billId, isSynced = true)
                    addBillsLocalUseCaseWrapper.insertBillToCloud(billWithId)
                    addBillsLocalUseCaseWrapper.insertBill(billWithId)

                }
                Log.d("WorkManagerUploadBills", "All local transactions inserted to cloud successfully.")
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerUploadBills", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}