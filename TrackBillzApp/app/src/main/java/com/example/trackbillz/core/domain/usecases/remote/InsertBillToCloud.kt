package com.example.trackbillz.core.domain.usecases.remote

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.core.domain.repository.remote.FirebaseRemoteRepository

class InsertBillToCloud(
    private val firebaseRemoteRepository: FirebaseRemoteRepository
) {

    suspend operator fun invoke(bill: Bill){
        firebaseRemoteRepository.insertBill(bill = bill)
    }

}