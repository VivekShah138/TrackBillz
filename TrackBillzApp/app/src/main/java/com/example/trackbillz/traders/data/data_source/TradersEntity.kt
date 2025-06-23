package com.example.trackbillz.traders.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["name", "gstNumber"])
data class TradersEntity(
    val name : String,
    val gstNumber: String,
    val type: String
)
