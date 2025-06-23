package com.example.trackbillz.splash_screen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackbillz.splash_screen.domain.SplashScreenUseCaseWrapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class SplashViewModel(
    private val splashScreenUseCaseWrapper: SplashScreenUseCaseWrapper
) : ViewModel() {

    private val _navigationChannel = Channel<NavigationEvent>()
    val navigationFlow = _navigationChannel.receiveAsFlow()

    init {
        isUserLoggedIn()
        loadInitialData()
    }

    private fun isUserLoggedIn(){
        viewModelScope.launch {
            val userId = splashScreenUseCaseWrapper.getUserId()
            Log.d("SplashViewModel","userId: $userId")
            if (!userId.isNullOrEmpty()){
                _navigationChannel.send(NavigationEvent.NavigateToUserPinSetup)
            }else{
                _navigationChannel.send(NavigationEvent.NavigateToGoogleSignUp)
            }
        }
    }

    private fun loadInitialData(){
        viewModelScope.launch {
            splashScreenUseCaseWrapper.insertAllPredefinedTraders()
        }
    }

    sealed class NavigationEvent {
        data object NavigateToUserPinSetup : NavigationEvent()
        data object NavigateToGoogleSignUp : NavigationEvent()
    }

}