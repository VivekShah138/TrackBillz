package com.example.trackbillz.core.presentation.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val routes: String){

    @Serializable
    data object AllBillRecordsScreen: Screens("all_bill_records_screen")

    @Serializable
    data object AddBillScreen: Screens("add_bill_screen")

    @Serializable
    data object ViewTradersScreen: Screens("view_traders_screen")

    @Serializable
    data object SettingsScreen: Screens("settings_screen")

    @Serializable
    data object GoogleSignUpScreen: Screens(routes = "google_signup_screen")

    @Serializable
    data class UserPinSetUpScreen(val isResetPin: Boolean): Screens(routes = "user_pin_setup_screen")

    @Serializable
    data object SplashScreen: Screens(routes = "splash_screen")

    @Serializable
    data class DetailedTradersScreen(val type: String): Screens(routes = "detailed_traders_screen")



    companion object {
        fun fromRoute(route: String?): Screens? = when(route) {
            AllBillRecordsScreen.routes -> AllBillRecordsScreen
            AddBillScreen.routes -> AddBillScreen
            ViewTradersScreen.routes -> ViewTradersScreen
            SettingsScreen.routes -> SettingsScreen
            else -> null
        }
    }


}
