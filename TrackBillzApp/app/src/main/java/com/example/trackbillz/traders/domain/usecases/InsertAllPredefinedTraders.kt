package com.example.trackbillz.traders.domain.usecases

import com.example.trackbillz.traders.domain.model.Traders
import com.example.trackbillz.traders.domain.repository.TradersLocalRepository

class InsertAllPredefinedTraders(
    private val tradersLocalRepository: TradersLocalRepository
) {
    suspend operator fun invoke(){
        tradersLocalRepository.insertAllPredefinedTraders()
    }

}