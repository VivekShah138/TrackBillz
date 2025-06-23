package com.example.trackbillz.splash_screen.di

import com.example.trackbillz.core.domain.usecases.local.GetUserId
import com.example.trackbillz.core.domain.usecases.remote.DeleteMultipleBillFromCloud
import com.example.trackbillz.core.domain.usecases.remote.InsertMultipleBillsToCloud
import com.example.trackbillz.core.domain.usecases.remote.SyncRemoteBillsToLocal
import com.example.trackbillz.splash_screen.domain.SplashScreenUseCaseWrapper
import com.example.trackbillz.splash_screen.presentation.SplashViewModel
import com.example.trackbillz.traders.domain.usecases.InsertAllPredefinedTraders
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashScreenModule = module {

    single<SplashScreenUseCaseWrapper> {
        SplashScreenUseCaseWrapper(
            getUserId = GetUserId(get()),
            insertAllPredefinedTraders = InsertAllPredefinedTraders(get())
        )
    }

    viewModel<SplashViewModel> {
        SplashViewModel(get())
    }

}