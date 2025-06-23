package com.example.trackbillz.add_bills.data.data_source

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.trackbillz.add_bills.domain.usecases.AddBillsLocalUseCaseWrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InsertAllBillsToLocalDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams),KoinComponent  {

    private val addBillsLocalUseCaseWrapper: AddBillsLocalUseCaseWrapper by inject()

    override suspend fun doWork(): Result {
        Log.d("WorkManagerInsertBillToLocal", "Worker started Insert All Bills To Local Db from Cloud")


        return try {
            val allRemoteBills = addBillsLocalUseCaseWrapper.getBillsFromCloud()
            Log.d("WorkManagerInsertBillToLocal", "allLocalTransactions $allRemoteBills")


            if (allRemoteBills.isEmpty()) {
                Log.d("WorkManagerInsertBillToLocal", "No Bills to Load.")
                return Result.failure()
            }
            else{
                allRemoteBills.forEach { bill ->
                    val billId = bill.billId!!

                    val doesExists = addBillsLocalUseCaseWrapper.doesBillExist(billId = billId)

                    if(!doesExists){
                        addBillsLocalUseCaseWrapper.insertBill(bill = bill)
                        Log.d("WorkManagerInsertBillToLocal", "Remote Bill $billId inserted to Local Database successfully.")
                    }
                    else{
                        Log.d("WorkManagerInsertBillToLocal", "Remote Bill $billId already exits in Local Database")
                    }
                }
                Result.success()
            }
        } catch (e: Exception) {
            Log.e("WorkManagerInsertBillToLocal", "Error during sync: ${e.message}")
            e.printStackTrace()
            Result.retry()
        }
    }
}