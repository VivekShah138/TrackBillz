package com.example.trackbillz.add_bills.presentation

import org.apache.commons.math3.analysis.function.Add

sealed interface AddBillsEvents {
    data class SelectDate(val year:Int,val month:Int,val day:Int): AddBillsEvents
    data class ChangeTraderGst(val gst: String): AddBillsEvents
    data class ChangeSearchTraderName(val name: String): AddBillsEvents
    data class ShowDetailedBillTotal(val expanded: Boolean): AddBillsEvents
    data class SetTraderName(val name: String,val expanded: Boolean): AddBillsEvents
    data object LoadTraderList: AddBillsEvents
    data class ChangeBillNumber(val billNumber: String): AddBillsEvents
    data class ChangeBillType(val billType: String,val expanded: Boolean): AddBillsEvents
    data class ChangeBillTotal(val billTotal: String): AddBillsEvents
    data class ChangeBillCGSTPercent(val cgstPercent: String): AddBillsEvents
    data class ChangeBillSGSTPercent(val sgstPercent: String): AddBillsEvents
    data object AddBill: AddBillsEvents
    data object ResetState: AddBillsEvents
}