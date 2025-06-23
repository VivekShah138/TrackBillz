package com.example.trackbillz.add_bills.data.utils

import com.example.trackbillz.add_bills.data.data_source.BillEntity
import com.example.trackbillz.add_bills.data.data_source.DeletedBillsEntity
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.model.DeletedBill

fun Bill.toEntity(): BillEntity{
    return BillEntity(
        billId = this.billId ?: 0,
        billNumber = this.billNumber,
        billType = this.billType,
        date = this.date,
        traderName =  this.traderName,
        traderGst = this.traderGst,
        billCGSTAmount = this.billCGSTAmount,
        traderCGSTPercent = this.traderCGSTPercent,
        traderSGSTPercent = this.traderSGSTPercent,
        billSGSTAmount = this.billSGSTAmount,
        billTotalAmount = this.billTotalAmount,
        billGrandTotal = this.billGrandTotal,
        isSynced = this.isSynced
    )
}

fun BillEntity.toModel():Bill{
    return Bill(
        billId = this.billId,
        billNumber = this.billNumber,
        billType = this.billType,
        date = this.date,
        traderName =  this.traderName,
        traderGst = this.traderGst,
        billCGSTAmount = this.billCGSTAmount,
        traderCGSTPercent = this.traderCGSTPercent,
        traderSGSTPercent = this.traderSGSTPercent,
        billSGSTAmount = this.billSGSTAmount,
        billTotalAmount = this.billTotalAmount,
        billGrandTotal = this.billGrandTotal,
        isSynced = this.isSynced
    )
}

fun DeletedBill.toEntity(): DeletedBillsEntity{
    return DeletedBillsEntity(
        billId = this.billId
    )
}

fun DeletedBillsEntity.toDomain(): DeletedBill{
    return DeletedBill(
        billId = this.billId
    )
}