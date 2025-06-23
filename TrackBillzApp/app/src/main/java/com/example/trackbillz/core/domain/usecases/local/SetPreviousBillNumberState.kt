package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class SetPreviousBillNumberState(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(state: Boolean){
        return dataStoreRepository.setPreviousBillNumberState(state = state)
    }

}