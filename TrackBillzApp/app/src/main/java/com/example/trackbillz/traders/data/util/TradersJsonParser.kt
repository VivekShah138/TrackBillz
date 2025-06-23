package com.example.trackbillz.traders.data.util

import com.example.trackbillz.traders.domain.model.Traders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TradersJsonParser {

    fun parseJsonToTraders(jsonString: String): List<Traders>{
        val listType =  object : TypeToken<List<Traders>>(){}.type
        return Gson().fromJson(jsonString,listType)
    }

}