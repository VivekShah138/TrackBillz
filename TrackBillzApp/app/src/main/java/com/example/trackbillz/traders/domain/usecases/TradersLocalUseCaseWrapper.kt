package com.example.trackbillz.traders.domain.usecases

data class TradersLocalUseCaseWrapper(
    val deleteTrader: DeleteTrader,
    val insertTrader: InsertTrader,
    val insertAllPredefinedTraders: InsertAllPredefinedTraders,
    val getAllTraders: GetAllTraders,
    val getAllTradersByType: GetAllTradersByType
)
