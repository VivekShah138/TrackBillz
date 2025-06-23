package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.model.UserPreferences
import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class GetUserPreferences(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): UserPreferences{
        return dataStoreRepository.getUserPreferences()
    }

}