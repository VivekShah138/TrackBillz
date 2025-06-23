package com.example.trackbillz.view_bills.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackbillz.add_bills.domain.model.DeletedBill
import com.example.trackbillz.view_bills.data.SaveFileResult
import com.example.trackbillz.view_bills.domain.usecases.ViewBillsUseCaseWrapper
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class ViewBillsViewModel(
    private val viewAllBillsUseCaseWrapper: ViewBillsUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(ViewBillsStates())
    val state: StateFlow<ViewBillsStates> = _state.asStateFlow()

    private val _saveFileEvent = Channel<SaveFileEvent>()
    val saveFileEvent = _saveFileEvent.receiveAsFlow()

    init {
        getFilteredRecords(filterWrapper = _state.value.filter)
        loadInitialData()
    }

    fun onEvent(events: ViewBillsEvents) {
        when (events) {
            // Filter
            is ViewBillsEvents.OnAllClearButtonClick -> {
                _state.value = _state.value.copy(
                    filter = FilterWrapper(),
                    showFilter = false,
                    showApplyButton = true
                )
                getFilteredRecords(filterWrapper = FilterWrapper())
            }

            is ViewBillsEvents.OnApplyButtonClick -> {
                val filter = _state.value.filter
                getFilteredRecords(filterWrapper = filter)
            }

            is ViewBillsEvents.OnApplyButtonVisibilityChange -> {
                _state.value = _state.value.copy(showApplyButton = events.visibility)
            }

            is ViewBillsEvents.OnCancelButtonClick -> {
                _state.value = _state.value.copy(
                    filter = FilterWrapper(),
                    showFilter = false
                )
            }

            is ViewBillsEvents.OnClickShowFilter -> {
                _state.value = _state.value.copy(showFilter = events.visibility)
            }

            is ViewBillsEvents.OnFilterChange -> {
                _state.value = _state.value.copy(filter = events.filter)
            }

            is ViewBillsEvents.OnSelectedSectionChange -> {
                _state.value = _state.value.copy(selectedSection = events.section)
            }

            is ViewBillsEvents.DeleteSelectedItems -> {
                val selectedId = _state.value.selectedItems

                if(selectedId.isNotEmpty()){
                    selectedId.forEach {
                        viewModelScope.launch {
                            viewAllBillsUseCaseWrapper.deleteBillById(billId = it)
                            viewAllBillsUseCaseWrapper.insertDeletedBill(DeletedBill(billId = it))
                        }
                    }
                }
            }

            is ViewBillsEvents.EnterSelectionMode -> {
                _state.value = _state.value.copy(
                    selectionMode = true
                )
            }

            is ViewBillsEvents.ExitSelectionMode -> {
                _state.value = _state.value.copy(
                    selectionMode = false,
                    selectedItems = emptySet()
                )
            }

            is ViewBillsEvents.GetSingleItem -> {

            }

            is ViewBillsEvents.SelectAllItems -> {
                val selectedItems = _state.value.selectedItems.toMutableSet()

                _state.value.list.forEach { item ->
                    selectedItems.add(item.billId ?: 0)
                }

                _state.value = _state.value.copy(
                    selectedItems = selectedItems
                )
            }

            is ViewBillsEvents.DeselectAllItems -> {
                _state.value = _state.value.copy(
                    selectedItems = emptySet()
                )
            }

            is ViewBillsEvents.ToggleItemSelection -> {
                val selectedItems = _state.value.selectedItems.toMutableSet()
                if(selectedItems.contains(events.itemId)){
                    selectedItems.remove(events.itemId)
                }else{
                    selectedItems.add(events.itemId)
                }

                _state.value = _state.value.copy(
                    selectedItems = selectedItems
                )
            }

            is ViewBillsEvents.SaveToExcel -> {

                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                    val result = viewAllBillsUseCaseWrapper.saveExcel(bills = _state.value.list, filterWrapper = _state.value.filter)
                    when(result){
                        is SaveFileResult.Failure -> {
                            _saveFileEvent.send(SaveFileEvent.Result(result.result))
                        }
                        is SaveFileResult.Success -> {
                            _saveFileEvent.send(SaveFileEvent.Result(result.result))
                        }
                    }
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }

            is ViewBillsEvents.ChangeDeleteAlertBoxState -> {
                _state.value = _state.value.copy(
                    showDeleteAlertBox = events.state
                )
            }
        }
    }

    private fun getFilteredRecords(filterWrapper: FilterWrapper){
        Log.d("ViewBillsViewModel","filter $filterWrapper")
        viewModelScope.launch {
            viewAllBillsUseCaseWrapper.getAllFilteredRecords(filterWrapper).collect{ bills->
                Log.d("ViewBillsViewModel","bills $bills")
                _state.value = _state.value.copy(
                    list = bills
                )
            }
        }
    }

    private fun loadInitialData(){
        _state.value = _state.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            viewAllBillsUseCaseWrapper.deleteMultipleBillFromCloud()
            viewAllBillsUseCaseWrapper.insertMultipleBillsToCloud()
        }
        _state.value = _state.value.copy(
            isLoading = false
        )
    }

    sealed class SaveFileEvent {
        data class Result(val message: String) : SaveFileEvent()
    }
}