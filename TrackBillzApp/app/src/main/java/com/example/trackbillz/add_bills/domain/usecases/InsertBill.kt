package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository

class InsertBill(
    private val addBillLocalRepository: AddBillLocalRepository
) {
    suspend operator fun invoke(bill: Bill){
        addBillLocalRepository.insertBill(bill = bill)
    }
}