package com.example.trackbillz.settings.presentation

data class SettingsStates(
    val autoFillBillNo: Boolean = false,
    val isInitialBillsDialogVisible: Boolean = false,
    val initialBillNo: String = "",
    val billNumberError: String? = null,
    val userImageUri: String? = null,
    val userEmail: String? = null,
    val userName: String? = null
)