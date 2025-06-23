package com.example.trackbillz.add_bills.domain.usecases

class ValidateBillNumber {

    operator fun invoke(billNumber: String): ValidationResult{
        if(billNumber.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please enter the bill number"
            )
        }
        else{
            return ValidationResult(
                isSuccessful = true
            )
        }
    }
}