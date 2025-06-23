package com.example.trackbillz.core.domain.usecases.remote

import com.example.trackbillz.core.domain.repository.remote.FirebaseRemoteRepository

class DeleteBillFromCloud(
    private val firebaseRemoteRepository: FirebaseRemoteRepository
) {

    suspend operator fun invoke(billId: Int){
        firebaseRemoteRepository.deleteBill(billId = billId)
    }

}