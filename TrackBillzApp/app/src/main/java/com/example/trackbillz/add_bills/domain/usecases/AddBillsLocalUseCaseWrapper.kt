package com.example.trackbillz.add_bills.domain.usecases

import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillDate
import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNumberState
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillDate
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.remote.GetBillsFromCloud
import com.example.trackbillz.core.domain.usecases.remote.InsertBillToCloud
import com.example.trackbillz.traders.domain.usecases.GetAllTradersByType

data class AddBillsLocalUseCaseWrapper(
    val deleteBill: DeleteBill,
    val getAllBills: GetAllBills,
    val insertBill: InsertBill,
    val insertBillReturningId: InsertBillReturningId,
    val insertBillToCloud: InsertBillToCloud,
    val getBillsFromCloud: GetBillsFromCloud,
    val getAllTradersByType: GetAllTradersByType,
    val validateBillTotal: ValidateBillTotal,
    val validateBillNumber: ValidateBillNumber,
    val validateTradeName: ValidateTradeName,
    val getPreviousBillNumberState: GetPreviousBillNumberState,
    val getPreviousBillNo: GetPreviousBillNo,
    val setPreviousBillNo: SetPreviousBillNo,
    val setPreviousBillDate: SetPreviousBillDate,
    val getPreviousBillDate: GetPreviousBillDate,
    val doesBillExist: DoesBillExist
)
