package com.example.trackbillz.splash_screen.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackbillz.R
import com.example.trackbillz.core.presentation.utils.Screens
import com.example.trackbillz.ui.theme.TrackBillzTheme
import com.example.trackbillz.ui.theme.iconGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashRoot(
    viewModel: SplashViewModel = koinViewModel(),
    navController: NavController
) {
    val navigationEvent = viewModel.navigationFlow

    SplashScreen(
        navigationEvent = navigationEvent,
        navController = navController
    )
}

@Composable
fun SplashScreen(
    navigationEvent: Flow<SplashViewModel.NavigationEvent>,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        navigationEvent.collect{event ->
//            delay(1000)
            when(event){
                SplashViewModel.NavigationEvent.NavigateToGoogleSignUp -> {
                    navController.navigate(Screens.GoogleSignUpScreen){
                        popUpTo(Screens.SplashScreen){
                            inclusive = true
                        }
                    }
                }
                SplashViewModel.NavigationEvent.NavigateToUserPinSetup -> {
                    navController.navigate(Screens.UserPinSetUpScreen(isResetPin = false)){
                        popUpTo(Screens.SplashScreen){
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center)
        )
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun Preview() {
    TrackBillzTheme(darkTheme = false) {
        SplashScreen(
            navigationEvent = emptyFlow(),
            navController = rememberNavController()
        )
    }
}