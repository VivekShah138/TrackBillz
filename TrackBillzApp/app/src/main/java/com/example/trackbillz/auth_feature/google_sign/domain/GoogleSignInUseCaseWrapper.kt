package com.example.trackbillz.auth_feature.google_sign.domain

import com.example.trackbillz.core.domain.usecases.local.GetUserPin
import com.example.trackbillz.core.domain.usecases.local.SetUserGoogleInfo
import com.example.trackbillz.core.domain.usecases.local.SetUserId

data class GoogleSignInUseCaseWrapper (
    val getUserPin: GetUserPin,
    val setUserId: SetUserId,
    val setUserGoogleInfo: SetUserGoogleInfo
)