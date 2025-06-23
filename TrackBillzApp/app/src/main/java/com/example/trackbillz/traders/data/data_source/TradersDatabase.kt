package com.example.trackbillz.traders.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TradersEntity::class],
    version = 1
)
abstract class TradersDatabase: RoomDatabase() {
    abstract val tradersDao: TradersDao

    companion object{
        const val DATABASE_NAME = "traders_db"
    }
}