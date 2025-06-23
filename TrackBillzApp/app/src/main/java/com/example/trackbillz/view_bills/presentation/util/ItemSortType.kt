package com.example.trackbillz.view_bills.presentation.util

import android.service.quicksettings.Tile

sealed class ItemSortType(val sortOrder: SortOrder){
    class Title(sortOrder: SortOrder): ItemSortType(sortOrder)
    class Date(sortOrder: SortOrder): ItemSortType(sortOrder)

    fun copy(sortOrder: SortOrder): ItemSortType{
        return when(this){
            is Title -> Title(sortOrder)
            is Date -> Date(sortOrder)
        }
    }
}

sealed class SortOrder{
    data object Ascending: SortOrder()
    data object Descending: SortOrder()
}
