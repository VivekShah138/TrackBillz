package com.example.trackbillz.add_bills.domain.usecases

import android.util.Log
import com.example.trackbillz.traders.domain.model.Traders

class ValidateTradeName {

    operator fun invoke(trader: String, tradersList: List<Traders>): ValidationResult{
        val exists = tradersList.any {trader.equals(it.name, ignoreCase = true) }
        if(trader.isBlank()){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please enter the trader name"
            )
        }
        else if(!exists){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Please enter the valid trader name"
            )
        }
        else{
            return ValidationResult(
                isSuccessful = true
            )
        }
    }

//    private fun isValidTrader(state: String,list: List<Traders>): Boolean{
//        list.any {
//            if(!state.equals(it.name, ignoreCase = true)){
//                return false
//            }
//        }
//        return true
//    }
}