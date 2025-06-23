package com.example.trackbillz.auth_feature.user_pin_setup.presentation

enum class PinSetupStep {
    EnterPin,
    ConfirmPin,
    Completed,
    EnterOldPin
}

enum class PinSetupMode{
    ResetPin,
    SetInitialPin,
    ValidateExistingPin
}