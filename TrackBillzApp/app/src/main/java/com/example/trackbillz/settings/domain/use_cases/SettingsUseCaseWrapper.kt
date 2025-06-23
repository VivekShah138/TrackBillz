package com.example.trackbillz.settings.domain.use_cases

import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNumberState
import com.example.trackbillz.core.domain.usecases.local.GetUserInfo
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillNumberState
import com.example.trackbillz.core.domain.usecases.remote.SignOutUser

data class SettingsUseCaseWrapper (
    val setPreviousBillNo: SetPreviousBillNo,
    val getPreviousBillNo: GetPreviousBillNo,
    val setPreviousBillNumberState: SetPreviousBillNumberState,
    val getPreviousBillNumberState: GetPreviousBillNumberState,
    val getUserInfo: GetUserInfo,
    val signOutUser: SignOutUser
)