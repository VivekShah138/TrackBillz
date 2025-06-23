package com.example.trackbillz.settings.di

import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNumberState
import com.example.trackbillz.core.domain.usecases.local.GetUserInfo
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillNumberState
import com.example.trackbillz.core.domain.usecases.remote.SignOutUser
import com.example.trackbillz.settings.domain.use_cases.SettingsUseCaseWrapper
import com.example.trackbillz.settings.presentation.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    single<SettingsUseCaseWrapper> {
        SettingsUseCaseWrapper(
            setPreviousBillNo = SetPreviousBillNo(get()),
            getPreviousBillNo = GetPreviousBillNo(get()),
            setPreviousBillNumberState = SetPreviousBillNumberState(get()),
            getPreviousBillNumberState = GetPreviousBillNumberState(get()),
            signOutUser = SignOutUser(
                dataStoreRepository = get(),
                firebaseRemoteRepository = get()
            ),
            getUserInfo = GetUserInfo(get())
        )
    }

    viewModel {
        SettingsViewModel(get())
    }

}