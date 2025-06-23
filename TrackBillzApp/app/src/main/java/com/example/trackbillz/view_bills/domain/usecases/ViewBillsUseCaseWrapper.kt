package com.example.trackbillz.view_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.usecases.DeleteBillById
import com.example.trackbillz.add_bills.domain.usecases.DeleteDeletedBillById
import com.example.trackbillz.add_bills.domain.usecases.GetAllBills
import com.example.trackbillz.add_bills.domain.usecases.GetAllDeletedBills
import com.example.trackbillz.add_bills.domain.usecases.InsertDeletedBill
import com.example.trackbillz.core.domain.usecases.remote.DeleteBillFromCloud
import com.example.trackbillz.core.domain.usecases.remote.DeleteMultipleBillFromCloud
import com.example.trackbillz.core.domain.usecases.remote.InsertMultipleBillsToCloud

data class ViewBillsUseCaseWrapper(
    val getAllFilteredRecords: GetAllFilteredRecords,
    val getAllBills: GetAllBills,
    val deleteBillById: DeleteBillById,
    val insertDeletedBill: InsertDeletedBill,
    val getAllDeletedBills: GetAllDeletedBills,
    val deleteDeletedBillById: DeleteDeletedBillById,
    val deleteBillFromCloud: DeleteBillFromCloud,
    val saveExcel: SaveExcel,
    val insertMultipleBillsToCloud: InsertMultipleBillsToCloud,
    val deleteMultipleBillFromCloud: DeleteMultipleBillFromCloud,
)
