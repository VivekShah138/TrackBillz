package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.model.DeletedBill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository

class InsertDeletedBill(
    private val addBillLocalRepository: AddBillLocalRepository
) {
    suspend operator fun invoke(deletedBill: DeletedBill){
        addBillLocalRepository.insertDeletedBill(deletedBill = deletedBill)
    }
}