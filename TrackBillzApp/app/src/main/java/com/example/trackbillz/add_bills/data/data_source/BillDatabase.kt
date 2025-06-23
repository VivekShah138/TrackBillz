package com.example.trackbillz.add_bills.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BillEntity::class,DeletedBillsEntity::class],
    version = 3
)
abstract class BillDatabase:RoomDatabase() {
    abstract val billDao: BillDao
    abstract val deletedBillsDao: DeletedBillsDao

    companion object{
        const val DATABASE_NAME = "bills_db"
    }
}