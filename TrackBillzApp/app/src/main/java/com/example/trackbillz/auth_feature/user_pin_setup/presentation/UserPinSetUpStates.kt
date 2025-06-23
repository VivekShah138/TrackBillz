package com.example.trackbillz.auth_feature.user_pin_setup.presentation
data class UserPinSetUpStates(
    val pin: List<Int?> = (1..4).map { null },
    val confirmPin: List<Int?> = (1..4).map { null },
    val oldPin: List<Int?> = (1..4).map { null },
    val pinSetupStep: PinSetupStep = PinSetupStep.EnterPin,
    val focusIndex: Int? = null,
    val isValid: Boolean? = null,
    val isPinSet: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val displayMessage: String = "",
    val pinSetupMode: PinSetupMode = PinSetupMode.ValidateExistingPin
)