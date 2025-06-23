package com.example.trackbillz.add_bills.presentation

import com.example.trackbillz.traders.domain.model.Traders

data class AddBillsStates(
    val billTypeList: List<String> = listOf("Sales","Purchase"),
    val billType: String = billTypeList[0],
    val billTypeExpanded: Boolean = false,
    val traderName: String = "",
    val traderNameErrorMessage: String? = null,
    val tradersList: List<Traders> = emptyList(),
    val searchTraderName: String = "",
    val traderNameExpanded: Boolean = false,
    val traderGst: String = "",
    val billNumber: String = "",
    val billNumberErrorMessage: String? = null,
    val billTotal: String = "",
    val billTotalErrorMessage: String? = null,
    val billGrandTotal: String = "",
    val billCGSTAmount: String = "",
    val billCGSTPercent: String = "",
    val billSGSTAmount: String = "",
    val billSGSTPercent: String = "",
    val selectedDay:Int = 0,
    val selectedMonth:Int = 0,
    val selectedYear:Int = 0,
    val formattedDate:String = "",
    val sameDateAsPrevious: Boolean = false,
    val showDetailedBillTotal: Boolean = false
)