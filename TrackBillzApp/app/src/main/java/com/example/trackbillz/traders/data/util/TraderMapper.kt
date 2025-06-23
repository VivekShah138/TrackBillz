package com.example.trackbillz.traders.data.util

import com.example.trackbillz.traders.data.data_source.TradersEntity
import com.example.trackbillz.traders.domain.model.Traders

fun Traders.toEntity(): TradersEntity{
    return TradersEntity(
        name = this.name,
        gstNumber = this.gstNumber,
        type = this.type
    )
}

fun TradersEntity.toModel(): Traders{
    return Traders(
        name = this.name,
        gstNumber = this.gstNumber,
        type = this.type
    )
}