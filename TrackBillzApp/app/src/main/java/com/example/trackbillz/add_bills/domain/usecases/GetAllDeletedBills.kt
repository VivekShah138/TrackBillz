package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.model.DeletedBill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllDeletedBills(
    private val addBillLocalRepository: AddBillLocalRepository
) {
    suspend operator fun invoke(): List<DeletedBill> {
        return addBillLocalRepository.getAllDeletedBills()
    }
}