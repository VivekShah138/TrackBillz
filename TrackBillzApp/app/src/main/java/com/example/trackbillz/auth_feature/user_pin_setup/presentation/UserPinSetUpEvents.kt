package com.example.trackbillz.auth_feature.user_pin_setup.presentation

sealed interface UserPinSetUpEvents {
    data class OnEnterPin(val number: Int?, val index: Int): UserPinSetUpEvents
    data class OnFocusChange(val index: Int): UserPinSetUpEvents
    data object OnKeyBoardBack: UserPinSetUpEvents
    data class ChangeLoadingState(val loading: Boolean): UserPinSetUpEvents
    data class ChangePinSetupMode(val mode: PinSetupMode,val step: PinSetupStep): UserPinSetUpEvents
    data object LogoutUser: UserPinSetUpEvents
}