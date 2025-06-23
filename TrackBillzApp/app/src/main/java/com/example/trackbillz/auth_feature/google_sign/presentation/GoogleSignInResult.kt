package com.example.trackbillz.auth_feature.google_sign.presentation

import com.example.trackbillz.core.domain.model.UserInfo

sealed class GoogleSignInResult {
    data class Success(val userInfo: UserInfo): GoogleSignInResult()
    data object Cancelled: GoogleSignInResult()
    data class Failure(val errorMessage: String): GoogleSignInResult()
    data class NotAuthorized(val email: String): GoogleSignInResult()
}