package com.example.trackbillz.add_bills.data.data_source

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.trackbillz.view_bills.domain.usecases.ViewBillsUseCaseWrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DeleteAllBillsFromCloudDatabaseWorker (
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters), KoinComponent {

    private val viewBillsUseCaseWrapper: ViewBillsUseCaseWrapper by inject()

    override suspend fun doWork(): Result {
        Log.d("WorkManagerDeletedBills", "Worker started Deleted All Bills From Cloud")


        return try {

            val allDeletedBills = viewBillsUseCaseWrapper.getAllDeletedBills()

            Log.d("WorkManagerDeletedBills","deletedBills $allDeletedBills")


            if (allDeletedBills.isEmpty() ) {
                Log.d("WorkManagerDeletedBills", "No deleted transactions to sync.")
                return Result.success()

            }
            else{
                allDeletedBills.forEach { deletedBill ->
                    viewBillsUseCaseWrapper.deleteBillFromCloud(billId = deletedBill.billId)
                    viewBillsUseCaseWrapper.deleteDeletedBillById(billId = deletedBill.billId)
                }
                Log.d("WorkManagerDeletedBills", "All Cloud bills deleted from cloud successfully.")


                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerDeletedBills", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}