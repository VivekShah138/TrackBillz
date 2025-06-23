package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository

class DeleteDeletedBillById(
    private val addBillLocalRepository: AddBillLocalRepository
) {
    suspend operator fun invoke(billId: Int){
        addBillLocalRepository.deleteDeletedBillById(billId = billId)
    }
}