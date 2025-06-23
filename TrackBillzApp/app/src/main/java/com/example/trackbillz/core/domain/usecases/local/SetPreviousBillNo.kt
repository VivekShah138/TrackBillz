package com.example.trackbillz.core.domain.usecases.local

import com.example.trackbillz.core.domain.repository.local.DataStoreRepository

class SetPreviousBillNo(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(billNo: String){
        return dataStoreRepository.saveBillNo(billNo = billNo)
    }

}