package com.example.trackbillz.view_bills.presentation

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.view_bills.presentation.util.FilterLeftPanel
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper

data class ViewBillsStates(
    val list: List<Bill> = emptyList(),

    // Filter
    val selectedSection: FilterLeftPanel = FilterLeftPanel.SortBy,
    val showApplyButton: Boolean = true,
    val showFilter: Boolean = false,
    val filter: FilterWrapper = FilterWrapper(),

    // Selection Mode
    val selectionMode: Boolean = false,
    val selectedItems: Set<Int> = emptySet(),

    val isLoading: Boolean = false,
    val showDeleteAlertBox: Boolean = false

)