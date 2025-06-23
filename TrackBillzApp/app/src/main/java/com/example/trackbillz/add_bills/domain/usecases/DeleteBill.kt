package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository

class DeleteBill(
    private val addBillLocalRepository: AddBillLocalRepository
) {
    suspend operator fun invoke(bill: Bill){
        addBillLocalRepository.deleteBill(bill = bill)
    }
}