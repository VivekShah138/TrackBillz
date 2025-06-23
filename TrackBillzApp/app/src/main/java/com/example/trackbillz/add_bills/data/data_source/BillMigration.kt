package com.example.trackbillz.add_bills.data.data_source

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val BILL_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE BillEntity ADD COLUMN isSynced INTEGER NOT NULL DEFAULT 0")
    }
}