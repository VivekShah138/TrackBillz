package com.example.trackbillz.add_bills.domain.usecases

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)
