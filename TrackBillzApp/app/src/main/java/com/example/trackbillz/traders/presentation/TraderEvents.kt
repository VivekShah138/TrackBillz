package com.example.trackbillz.traders.presentation

import com.example.trackbillz.traders.domain.model.Traders

sealed interface TraderEvents {
    data class ChangeTraderType(val type: String): TraderEvents
    data class ChangeTraderName(val name: String): TraderEvents
    data class ChangeSearchQuery(val query: String): TraderEvents
    data class ChangeTraderGST(val gst: String): TraderEvents
    data class ChangeTraderAddAlertBoxState(val state: Boolean): TraderEvents
    data class ChangeTraderDeleteAlertBoxState(val state: Boolean): TraderEvents
    data class ChangeSelectedTrader(val trader: Traders): TraderEvents
    data object DeleteTrader: TraderEvents
    data object AddTrader: TraderEvents
}