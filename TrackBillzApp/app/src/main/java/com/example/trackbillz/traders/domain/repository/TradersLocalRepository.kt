package com.example.trackbillz.traders.domain.repository

import com.example.trackbillz.traders.domain.model.Traders
import kotlinx.coroutines.flow.Flow

interface TradersLocalRepository {

    suspend fun insertTrader(trader: Traders)
    suspend fun insertAllPredefinedTraders()
    suspend fun deleteTrader(trader: Traders)
    suspend fun getAllTraders(): Flow<List<Traders>>
    suspend fun getTradersByType(type: String): Flow<List<Traders>>

}