package com.example.trackbillz.settings.presentation

sealed interface SettingsEvents {
    data class ChangeAutomateBills(val state: Boolean): SettingsEvents
    data class ChangeInitialBillsDialogVisible(val state: Boolean): SettingsEvents
    data class ChangeInitialBillNo(val text: String): SettingsEvents
    data object SaveBillNumber: SettingsEvents
    data object Logout: SettingsEvents
}