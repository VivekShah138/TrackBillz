package com.example.trackbillz.auth_feature.google_sign.presentation

import com.example.trackbillz.core.domain.model.UserInfo

sealed interface GoogleSignInEvents {
    data class GoogleSignInClick(val result: GoogleSignInResult): GoogleSignInEvents
    data object SetLoadingTrue: GoogleSignInEvents
}