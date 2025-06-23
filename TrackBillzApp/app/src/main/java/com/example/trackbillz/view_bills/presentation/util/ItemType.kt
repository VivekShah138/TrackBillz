package com.example.trackbillz.view_bills.presentation.util

sealed class ItemType{
    data object Sales: ItemType()
    data object Purchase: ItemType()
    data object Both: ItemType()
}
