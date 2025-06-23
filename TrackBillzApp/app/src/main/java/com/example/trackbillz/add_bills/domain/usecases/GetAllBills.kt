package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllBills(
    private val addBillLocalRepository: AddBillLocalRepository
) {
    operator fun invoke(): Flow<List<Bill>> {
        return addBillLocalRepository.getAllBills()
    }
}