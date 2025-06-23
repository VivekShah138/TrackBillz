package com.example.trackbillz.core.domain.repository.remote

import com.example.trackbillz.add_bills.domain.model.Bill

interface FirebaseRemoteRepository {

    suspend fun logout()
    suspend fun insertBill(bill: Bill)
    suspend fun insertMultipleBills()
    suspend fun syncRemoteBillsToLocal()
    suspend fun deleteBill(billId: Int)
    suspend fun deleteMultipleBills()
    suspend fun getBill(): List<Bill>

}