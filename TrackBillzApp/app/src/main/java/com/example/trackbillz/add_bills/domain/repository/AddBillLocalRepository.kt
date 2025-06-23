package com.example.trackbillz.add_bills.domain.repository

import com.example.trackbillz.add_bills.data.data_source.BillEntity
import com.example.trackbillz.add_bills.data.data_source.DeletedBillsEntity
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.model.DeletedBill
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper
import kotlinx.coroutines.flow.Flow

interface AddBillLocalRepository {

    suspend fun insertBill(bill: Bill)
    suspend fun insertBillReturningId(bill: Bill): Long
    suspend fun deleteBill(bill: Bill)
    suspend fun deleteBillById(billId: Int)
    fun getAllBills(): Flow<List<Bill>>
    fun getAllFilteredBills(filterWrapper: FilterWrapper): Flow<List<Bill>>
    suspend fun doesBillExist(billId: Int): Boolean

    suspend fun insertDeletedBill(deletedBill: DeletedBill)
    suspend fun deleteDeletedBillById(billId: Int)
    suspend fun getAllDeletedBills(): List<DeletedBill>


}