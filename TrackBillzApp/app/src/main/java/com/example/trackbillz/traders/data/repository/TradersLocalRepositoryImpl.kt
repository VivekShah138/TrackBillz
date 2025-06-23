package com.example.trackbillz.traders.data.repository

import android.content.Context
import com.example.trackbillz.traders.data.data_source.TradersDao
import com.example.trackbillz.traders.data.util.TradersJsonParser
import com.example.trackbillz.traders.data.util.toEntity
import com.example.trackbillz.traders.data.util.toModel
import com.example.trackbillz.traders.domain.model.Traders
import com.example.trackbillz.traders.domain.repository.TradersLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TradersLocalRepositoryImpl(
    private val tradersDao: TradersDao,
    private val context: Context
): TradersLocalRepository {
    override suspend fun insertTrader(trader: Traders) = withContext(Dispatchers.IO) {
        tradersDao.insertTrader(trader.toEntity())
    }

    override suspend fun insertAllPredefinedTraders()  = withContext(Dispatchers.IO){
        val jsonString = context.assets.open("traders_gst.json").bufferedReader().use {
            it.readText()
        }
        val tradersList = TradersJsonParser.parseJsonToTraders(jsonString = jsonString)

        tradersList.forEach {
            tradersDao.insertTrader(it.toEntity())
        }
    }

    override suspend fun deleteTrader(trader: Traders) = withContext(Dispatchers.IO) {
         tradersDao.deleteTrader(trader.toEntity())
    }

    override suspend fun getAllTraders(): Flow<List<Traders>> = withContext(Dispatchers.IO) {
         tradersDao.getAllTraders().map{tradersList ->
             tradersList.map { it.toModel() }
         }
    }

    override suspend fun getTradersByType(type: String): Flow<List<Traders>> = withContext(Dispatchers.IO) {
        tradersDao.getTradersByType(type = type).map{tradersList ->
            tradersList.map { it.toModel() }
        }
    }
}