package com.example.trackbillz.traders.presentation

import com.example.trackbillz.traders.domain.model.Traders

data class TradersStates(
    val salesTradersList: List<Traders> = emptyList(),
    val purchaseTradersList: List<Traders> = emptyList(),
    val filteredTraderList: List<Traders> = emptyList(),
    val searchQuery: String = "",
    val traderType: String = "",
    val traderName: String = "",
    val traderGST: String = "",
    val traderAddAlertBoxState: Boolean = false,
    val traderDeleteAlertBoxState: Boolean = false,
    val selectedTrader: Traders = Traders(),
    val isLoading: Boolean = false
)