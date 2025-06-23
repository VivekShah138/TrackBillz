package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class SetUserId(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(userId: String?){
        return dataStoreRepository.setUserId(userId = userId)
    }

}