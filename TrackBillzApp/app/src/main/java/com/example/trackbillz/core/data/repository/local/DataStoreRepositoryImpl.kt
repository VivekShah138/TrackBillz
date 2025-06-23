package com.example.trackbillz.core.data.repository.local

import androidx.datastore.core.DataStore
import com.example.trackbillz.core.domain.model.UserInfo
import com.example.trackbillz.core.domain.model.UserPreferences
import com.example.trackbillz.core.domain.repository.local.DataStoreRepository
import kotlinx.coroutines.flow.first

class DataStoreRepositoryImpl(
    private val userPrefDataStore: DataStore<UserPreferences>,
    private val userInfoDataStore: DataStore<UserInfo>
): DataStoreRepository {
    override suspend fun saveUserPin(pin: String) {
        userInfoDataStore.updateData {
            it.copy(pin = pin)
        }
    }

    override suspend fun saveBillNo(billNo: String) {
        userPrefDataStore.updateData {
            it.copy(billNumber = billNo)
        }
    }

    override suspend fun savePreviousDate(date: Long) {
        userPrefDataStore.updateData {
            it.copy(previewBillDate = date)
        }
    }


    override suspend fun setPreviousBillNumberState(state: Boolean) {
        userPrefDataStore.updateData {
            it.copy(previousBillNumberState = state)
        }
    }

    override suspend fun setUserId(userId: String?) {
        userInfoDataStore.updateData {
            it.copy(userId = userId)
        }
    }

    override suspend fun saveUserInfo(userGoogleInfo: UserInfo) {
        userInfoDataStore.updateData {
            it.copy(
                userId = userGoogleInfo.userId,
                email = userGoogleInfo.email,
                name = userGoogleInfo.name,
                giveName = userGoogleInfo.giveName,
                familyName = userGoogleInfo.familyName,
                profileURI = userGoogleInfo.profileURI
            )
        }
    }

    override suspend fun getUserInfo(): UserInfo {
        return userInfoDataStore.data.first()
    }

    override suspend fun getUserPreferences(): UserPreferences {
        return userPrefDataStore.data.first()
    }
}