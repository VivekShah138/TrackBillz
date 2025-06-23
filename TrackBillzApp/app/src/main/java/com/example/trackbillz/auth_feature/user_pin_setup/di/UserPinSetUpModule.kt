package com.example.trackbillz.auth_feature.user_pin_setup.di

import com.example.trackbillz.auth_feature.user_pin_setup.domain.usecases.UserPinSetUpUseCaseWrapper
import com.example.trackbillz.auth_feature.user_pin_setup.presentation.UserPinSetUpViewModel
import com.example.trackbillz.core.domain.usecases.local.GetUserPin
import com.example.trackbillz.core.domain.usecases.local.SetUserPin
import com.example.trackbillz.core.domain.usecases.remote.SignOutUser
import com.example.trackbillz.core.domain.usecases.remote.SyncRemoteBillsToLocal
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val userPinSetUpModule = module {

    single<UserPinSetUpUseCaseWrapper> {
        UserPinSetUpUseCaseWrapper(
            getUserPin = GetUserPin(get()),
            setUserPin = SetUserPin(get()),
            signOutUser = SignOutUser(
                dataStoreRepository = get(),
                firebaseRemoteRepository = get()
            ),
            syncRemoteBillsToLocal = SyncRemoteBillsToLocal(get())
        )
    }

    viewModel {
        UserPinSetUpViewModel(get())
    }
}