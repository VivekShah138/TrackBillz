package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class SetUserPin(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(pin: String){
        return dataStoreRepository.saveUserPin(pin = pin)
    }

}