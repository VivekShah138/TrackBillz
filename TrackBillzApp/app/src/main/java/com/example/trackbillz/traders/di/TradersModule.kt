package com.example.trackbillz.traders.di

import androidx.room.Room
import com.example.trackbillz.traders.data.data_source.TradersDao
import com.example.trackbillz.traders.data.data_source.TradersDatabase
import com.example.trackbillz.traders.data.repository.TradersLocalRepositoryImpl
import com.example.trackbillz.traders.domain.repository.TradersLocalRepository
import com.example.trackbillz.traders.domain.usecases.DeleteTrader
import com.example.trackbillz.traders.domain.usecases.GetAllTraders
import com.example.trackbillz.traders.domain.usecases.GetAllTradersByType
import com.example.trackbillz.traders.domain.usecases.InsertAllPredefinedTraders
import com.example.trackbillz.traders.domain.usecases.InsertTrader
import com.example.trackbillz.traders.domain.usecases.TradersLocalUseCaseWrapper
import com.example.trackbillz.traders.presentation.TradersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val tradersModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            TradersDatabase::class.java,
            TradersDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single<TradersDao> {
        get<TradersDatabase>().tradersDao
    }

    single<TradersLocalRepository> {
        TradersLocalRepositoryImpl(tradersDao = get<TradersDao>(), context = androidContext())
    }

    single<TradersLocalUseCaseWrapper>{
        val tradersLocalRepository = get<TradersLocalRepository>()
        TradersLocalUseCaseWrapper(
            insertTrader = InsertTrader(tradersLocalRepository = tradersLocalRepository),
            insertAllPredefinedTraders = InsertAllPredefinedTraders(tradersLocalRepository = tradersLocalRepository),
            deleteTrader = DeleteTrader(tradersLocalRepository = tradersLocalRepository),
            getAllTraders = GetAllTraders(tradersLocalRepository = tradersLocalRepository),
            getAllTradersByType = GetAllTradersByType(tradersLocalRepository = tradersLocalRepository)
        )
    }

    viewModel {
        TradersViewModel(get())
    }
}