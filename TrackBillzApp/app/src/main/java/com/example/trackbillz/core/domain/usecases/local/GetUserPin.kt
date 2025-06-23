package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class GetUserPin(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): String?{
        return dataStoreRepository.getUserInfo().pin
    }

}