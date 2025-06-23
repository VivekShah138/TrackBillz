package com.example.trackbillz.add_bills.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val billId:Int,
    val billNumber:String,
    val billType: String,
    val date: Long,
    val traderName: String,
    val traderGst: String,
    val traderCGSTPercent: Double,
    val billCGSTAmount: Double,
    val traderSGSTPercent: Double,
    val billSGSTAmount: Double,
    val billTotalAmount: Double,
    val billGrandTotal: Double,
    val isSynced: Boolean
)
