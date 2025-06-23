package com.example.trackbillz.auth_feature.user_pin_setup.domain.usecases

import com.example.trackbillz.core.domain.usecases.local.GetUserPin
import com.example.trackbillz.core.domain.usecases.local.SetUserPin
import com.example.trackbillz.core.domain.usecases.remote.SignOutUser
import com.example.trackbillz.core.domain.usecases.remote.SyncRemoteBillsToLocal

data class UserPinSetUpUseCaseWrapper(
    val getUserPin: GetUserPin,
    val setUserPin: SetUserPin,
    val signOutUser: SignOutUser,
    val syncRemoteBillsToLocal: SyncRemoteBillsToLocal,
)
