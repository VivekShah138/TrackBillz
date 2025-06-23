package com.example.trackbillz.auth_feature.google_sign.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackbillz.add_bills.presentation.AddBillsViewModel.ValidationEvent
import com.example.trackbillz.auth_feature.google_sign.domain.GoogleSignInUseCaseWrapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class GoogleSignInViewModel(
    private val googleSignInUseCaseWrapper: GoogleSignInUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(GoogleSignUpStates())
    val state: StateFlow<GoogleSignUpStates> = _state.asStateFlow()

    private val loginEventChannel = Channel<ValidationEvent>()
    val loginEvents = loginEventChannel.receiveAsFlow()



    fun onEvent(events: GoogleSignInEvents) {
        when (events) {
            is GoogleSignInEvents.GoogleSignInClick -> {
                viewModelScope.launch {
                    when(events.result){
                        is GoogleSignInResult.Success ->{
                            _state.value = state.value.copy(
                                isLoading = false
                            )
                            googleSignInUseCaseWrapper.setUserGoogleInfo(events.result.userInfo)
                            loginEventChannel.send(ValidationEvent.Success)
                        }
                        is GoogleSignInResult.Cancelled ->{
                            _state.value = state.value.copy(
                                isLoading = false
                            )
                            loginEventChannel.send(ValidationEvent.Error(errorMessage = "Google SignIn Cancelled"))
                        }
                        is GoogleSignInResult.Failure ->{
                            _state.value = state.value.copy(
                                isLoading = false
                            )
                            loginEventChannel.send(ValidationEvent.Error(errorMessage = "Opps!Something went wrong.${events.result.errorMessage}"))
                        }
                        is GoogleSignInResult.NotAuthorized -> {
                            _state.value = state.value.copy(
                                isLoading = false
                            )
                            loginEventChannel.send(ValidationEvent.Error(errorMessage = "Please use admin account.\n${events.result.email} is not authorised"))
                        }
                    }
                }
            }

            is GoogleSignInEvents.SetLoadingTrue -> {
                _state.value = _state.value.copy(
                    isLoading = true
                )
            }
        }
    }
}