package com.example.trackbillz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.trackbillz.add_bills.presentation.AddBillsRoot
import com.example.trackbillz.core.presentation.utils.Screens
import com.example.trackbillz.settings.presentation.SettingsRoot
import com.example.trackbillz.auth_feature.google_sign.presentation.GoogleSignInRoot
import com.example.trackbillz.auth_feature.user_pin_setup.presentation.components.UserPinSetUpRoot
import com.example.trackbillz.splash_screen.presentation.SplashRoot
import com.example.trackbillz.traders.presentation.TradersViewModel
import com.example.trackbillz.traders.presentation.components.DetailedTradersScreen
import com.example.trackbillz.traders.presentation.components.ViewTradersRoot
import com.example.trackbillz.ui.theme.TrackBillzTheme
import com.example.trackbillz.view_bills.presentation.ViewBillsRoot
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackBillzTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screens.SplashScreen
                ){
                    composable<Screens.AllBillRecordsScreen>{
                        ViewBillsRoot(navController = navController)
                    }
                    composable<Screens.AddBillScreen> {
                        AddBillsRoot(navController = navController)
                    }
                    composable<Screens.ViewTradersScreen> {
                        ViewTradersRoot(navController = navController)
                    }
                    composable<Screens.SettingsScreen> {
                        SettingsRoot(navController = navController)
                    }

                    composable<Screens.GoogleSignUpScreen>{
                        GoogleSignInRoot(navController = navController)
                    }

                    composable<Screens.UserPinSetUpScreen> {
                        val args = it.toRoute<Screens.UserPinSetUpScreen>()
                        UserPinSetUpRoot(
                            navController = navController,
                            isResetPin = args.isResetPin
                        )
                    }

                    composable<Screens.SplashScreen> {
                        SplashRoot(navController = navController)
                    }

                    composable<Screens.DetailedTradersScreen> {
                        val args = it.toRoute<Screens.DetailedTradersScreen>()

                        val viewModel: TradersViewModel = koinViewModel()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        DetailedTradersScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            navController = navController,
                            traderType = args.type
                        )
                    }
                }
            }
        }
    }
}