package com.example.trackbillz.traders.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TradersDao {

    @Upsert
    suspend fun insertTrader(tradersEntity: TradersEntity)

    @Delete
    suspend fun deleteTrader(tradersEntity: TradersEntity)

    @Query("SELECT * FROM TradersEntity")
    fun getAllTraders(): Flow<List<TradersEntity>>

    @Query("SELECT * FROM TradersEntity WHERE type = :type")
    fun getTradersByType(type: String): Flow<List<TradersEntity>>
}