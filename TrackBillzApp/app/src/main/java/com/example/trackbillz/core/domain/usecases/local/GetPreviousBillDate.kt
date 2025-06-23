package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class GetPreviousBillDate(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): Long?{
        return dataStoreRepository.getUserPreferences().previewBillDate
    }

}