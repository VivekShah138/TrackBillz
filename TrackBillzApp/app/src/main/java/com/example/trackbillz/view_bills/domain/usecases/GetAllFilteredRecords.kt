package com.example.trackbillz.view_bills.domain.usecases


import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper
import kotlinx.coroutines.flow.Flow


class GetAllFilteredRecords(
    private val addBillLocalRepository: AddBillLocalRepository
) {

    operator fun invoke(filter: FilterWrapper): Flow<List<Bill>> {
        return addBillLocalRepository.getAllFilteredBills(filter)
    }
}