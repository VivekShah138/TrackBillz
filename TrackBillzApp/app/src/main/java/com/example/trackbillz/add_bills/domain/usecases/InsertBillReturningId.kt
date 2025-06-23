package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository

class InsertBillReturningId(
    private val addBillLocalRepository: AddBillLocalRepository
) {
    suspend operator fun invoke(bill: Bill): Long{
        return addBillLocalRepository.insertBillReturningId(bill = bill)
    }
}