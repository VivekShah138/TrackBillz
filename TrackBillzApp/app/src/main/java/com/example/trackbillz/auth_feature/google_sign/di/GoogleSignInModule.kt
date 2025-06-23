package com.example.trackbillz.auth_feature.google_sign.di

import com.example.trackbillz.core.domain.usecases.local.GetUserPin
import com.example.trackbillz.auth_feature.google_sign.domain.GoogleSignInUseCaseWrapper
import com.example.trackbillz.auth_feature.google_sign.presentation.GoogleSignInViewModel
import com.example.trackbillz.core.domain.usecases.local.SetUserGoogleInfo
import com.example.trackbillz.core.domain.usecases.local.SetUserId
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val googleSignInModule = module {

    single<GoogleSignInUseCaseWrapper> {
        GoogleSignInUseCaseWrapper(
            getUserPin = GetUserPin(get()),
            setUserId = SetUserId(get()),
            setUserGoogleInfo = SetUserGoogleInfo(get())
        )
    }

    viewModel<GoogleSignInViewModel> {
        GoogleSignInViewModel(get())
    }

}