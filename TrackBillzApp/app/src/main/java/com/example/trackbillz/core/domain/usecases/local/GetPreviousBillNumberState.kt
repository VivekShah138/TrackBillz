package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class GetPreviousBillNumberState(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): Boolean{
        return dataStoreRepository.getUserPreferences().previousBillNumberState
    }

}