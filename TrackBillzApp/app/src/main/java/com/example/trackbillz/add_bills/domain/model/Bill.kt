package com.example.trackbillz.add_bills.domain.model

data class Bill(
    val billId:Int? = null,
    val billNumber:String = "",
    val billType: String = "",
    val date: Long = 0L,
    val traderName: String = "",
    val traderGst: String = "",
    val traderCGSTPercent: Double = 6.0,
    val billCGSTAmount: Double = 0.0,
    val traderSGSTPercent: Double = 6.0,
    val billSGSTAmount: Double = 0.0,
    val billTotalAmount: Double = 0.0,
    val billGrandTotal: Double = 0.0,
    val isSynced: Boolean = false
)
