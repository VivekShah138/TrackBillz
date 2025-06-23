package com.example.trackbillz.view_bills.presentation

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.view_bills.presentation.util.FilterLeftPanel
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper

sealed class ViewBillsEvents {

    data class OnClickShowFilter(val visibility: Boolean): ViewBillsEvents()
    data class OnSelectedSectionChange(val section: FilterLeftPanel): ViewBillsEvents()
    data class OnFilterChange(val filter: FilterWrapper): ViewBillsEvents()
    data object OnApplyButtonClick: ViewBillsEvents()
    data object OnAllClearButtonClick: ViewBillsEvents()
    data object OnCancelButtonClick: ViewBillsEvents()
    data class OnApplyButtonVisibilityChange(val visibility: Boolean): ViewBillsEvents()

    // Transaction Selection
    data class ToggleItemSelection(val itemId: Int): ViewBillsEvents()
    data object EnterSelectionMode: ViewBillsEvents()
    data object ExitSelectionMode: ViewBillsEvents()
    data object SelectAllItems: ViewBillsEvents()
    data object DeselectAllItems: ViewBillsEvents()

    // After Selection Actions
    data object DeleteSelectedItems: ViewBillsEvents()

    // Get Selected Transaction
    data class GetSingleItem(val itemId: Int): ViewBillsEvents()

    // Download Excel
    data object SaveToExcel: ViewBillsEvents()

    data class ChangeDeleteAlertBoxState(val state: Boolean): ViewBillsEvents()

}