package com.example.trackbillz.traders.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackbillz.traders.domain.model.Traders
import com.example.trackbillz.traders.domain.usecases.TradersLocalUseCaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class TradersViewModel(
    private val tradersLocalUseCaseWrapper: TradersLocalUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(TradersStates())
    val state: StateFlow<TradersStates> = _state.asStateFlow()

    init {
        loadTraders()
    }

    fun onEvent(events: TraderEvents) {
        when (events) {

            is TraderEvents.ChangeTraderType -> {
                _state.value = _state.value.copy(
                    traderType = events.type
                )
            }

            is TraderEvents.ChangeTraderAddAlertBoxState -> {
                _state.value = _state.value.copy(
                    traderAddAlertBoxState = events.state
                )
            }

            is TraderEvents.ChangeTraderDeleteAlertBoxState -> {
                _state.value = _state.value.copy(
                    traderDeleteAlertBoxState = events.state
                )
            }

            is TraderEvents.ChangeTraderGST -> {
                _state.value = _state.value.copy(
                    traderGST = events.gst
                )
            }

            is TraderEvents.ChangeTraderName -> {
                _state.value = _state.value.copy(
                    traderName = events.name
                )
            }

            TraderEvents.AddTrader -> {
                insertTrader()
            }

            is TraderEvents.ChangeSelectedTrader -> {
                _state.value = _state.value.copy(
                    selectedTrader = events.trader,
                    traderName = events.trader.name,
                    traderGST = events.trader.gstNumber,
                    traderType = events.trader.type
                )
            }

            is TraderEvents.DeleteTrader -> {
                viewModelScope.launch {
                    tradersLocalUseCaseWrapper.deleteTrader(state.value.selectedTrader)
                }
            }

            is TraderEvents.ChangeSearchQuery -> {
                _state.value = _state.value.copy(
                    searchQuery = events.query
                )
            }
        }
    }

    private fun insertTrader() {
        viewModelScope.launch {
            val traderName = _state.value.traderName
            val traderGst = _state.value.traderGST
            val traderType = _state.value.traderType

            tradersLocalUseCaseWrapper.insertTrader(
                Traders(
                    name = traderName,
                    type = traderType,
                    gstNumber = traderGst
                )
            )
        }
    }

    private fun loadTraders() {
        viewModelScope.launch {
            combine(
                tradersLocalUseCaseWrapper.getAllTradersByType(type = "sales"),
                tradersLocalUseCaseWrapper.getAllTradersByType(type = "purchase")
            ) { sales, purchase ->
                sales to purchase
            }.collect { (sales, purchase) ->
                _state.value = _state.value.copy(
                    salesTradersList = sales,
                    purchaseTradersList = purchase
                )
            }
        }
    }
}