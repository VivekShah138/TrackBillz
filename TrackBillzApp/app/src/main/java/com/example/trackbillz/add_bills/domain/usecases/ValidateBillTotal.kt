package com.example.trackbillz.add_bills.domain.usecases

class ValidateBillTotal {

    operator fun invoke(billTotal: String): ValidationResult{
        if(billTotal.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please enter the bill number"
            )
        }
        else if(!billTotal.matches(regex = Regex("^[0-9]+(\\.[0-9]+)?$"))){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please enter the valid amount"
            )
        }
        else{
            return ValidationResult(
                isSuccessful = true
            )
        }
    }
}