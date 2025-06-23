package com.example.trackbillz.core.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val screen: Screens,
    val icon: ImageVector,
    val label: String
)

val BottomNavItemsList = listOf(
    BottomNavItem(Screens.AllBillRecordsScreen, icon = Icons.Default.Home, label = "Home"),
    BottomNavItem(Screens.AddBillScreen, icon = Icons.Default.AddCircle, label = "Add"),
    BottomNavItem(Screens.ViewTradersScreen, icon = Icons.AutoMirrored.Filled.List, label = "View Traders"),
    BottomNavItem(Screens.SettingsScreen, icon = Icons.Default.Settings, label = "Settings"),
)