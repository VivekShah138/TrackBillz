package com.example.trackbillz.core.domain.repository.local

import com.example.trackbillz.core.domain.model.UserInfo
import com.example.trackbillz.core.domain.model.UserPreferences

interface DataStoreRepository {

    suspend fun saveUserPin(pin: String)
    suspend fun saveBillNo(billNo: String)
    suspend fun savePreviousDate(date: Long)
    suspend fun setPreviousBillNumberState(state: Boolean)
    suspend fun setUserId(userId: String?)
    suspend fun saveUserInfo(userGoogleInfo: UserInfo)
    suspend fun getUserInfo(): UserInfo
    suspend fun getUserPreferences(): UserPreferences

}