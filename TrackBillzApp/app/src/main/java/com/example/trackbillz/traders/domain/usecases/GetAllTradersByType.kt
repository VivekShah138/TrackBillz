package com.example.trackbillz.traders.domain.usecases

import com.example.trackbillz.traders.domain.model.Traders
import com.example.trackbillz.traders.domain.repository.TradersLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllTradersByType(
    private val tradersLocalRepository: TradersLocalRepository
) {
    suspend operator fun invoke(type: String): Flow<List<Traders>> = tradersLocalRepository.getTradersByType(type = type)
}