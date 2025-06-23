package com.example.trackbillz.core.domain.usecases.remote

import com.example.trackbillz.core.domain.repository.remote.FirebaseRemoteRepository

class SyncRemoteBillsToLocal(
    private val firebaseRemoteRepository: FirebaseRemoteRepository
) {
    suspend operator fun invoke(){
        firebaseRemoteRepository.syncRemoteBillsToLocal()
    }
}