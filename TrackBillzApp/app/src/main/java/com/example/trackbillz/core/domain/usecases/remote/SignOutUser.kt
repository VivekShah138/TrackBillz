package com.example.trackbillz.core.domain.usecases.remote

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository
import com.example.trackbillz.core.domain.repository.remote.FirebaseRemoteRepository

class SignOutUser(
    private val dataStoreRepository: DataStoreRepository,
    private val firebaseRemoteRepository: FirebaseRemoteRepository
) {

    suspend operator fun invoke(){
        dataStoreRepository.setUserId(userId = null)
        firebaseRemoteRepository.logout()
    }

}