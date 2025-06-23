package com.example.trackbillz.view_bills.presentation.util

sealed class ItemDuration{
    data object Today : ItemDuration()
    data object ThisMonth : ItemDuration()
    data object LastMonth : ItemDuration()
    data object Last3Months : ItemDuration()
    data class CustomRange(val from: Long, val to: Long) : ItemDuration()
}
