package com.example.trackbillz.core.domain.usecases.remote

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.core.domain.repository.remote.FirebaseRemoteRepository

class GetBillsFromCloud(
    private val firebaseRemoteRepository: FirebaseRemoteRepository
) {

    suspend operator fun invoke(): List<Bill>{
        return firebaseRemoteRepository.getBill()
    }

}