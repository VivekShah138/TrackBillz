package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class SetPreviousBillDate(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(date: Long){
        return dataStoreRepository.savePreviousDate(date = date)
    }

}