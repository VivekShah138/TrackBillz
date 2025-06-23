package com.example.trackbillz.view_bills.presentation.util

data class FilterWrapper(
    val itemType: ItemType = ItemType.Both,
    val itemSortType: ItemSortType = ItemSortType.Date(SortOrder.Descending),
    val itemDuration: ItemDuration = ItemDuration.ThisMonth
)
