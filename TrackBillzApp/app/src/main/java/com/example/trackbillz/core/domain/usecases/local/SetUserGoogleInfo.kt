package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.model.UserInfo
import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class SetUserGoogleInfo(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(userInfo: UserInfo){
        return dataStoreRepository.saveUserInfo(userGoogleInfo = userInfo)
    }

}