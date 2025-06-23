package com.example.trackbillz.add_bills.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeletedBillsEntity(
    @PrimaryKey
    val billId: Int
)
