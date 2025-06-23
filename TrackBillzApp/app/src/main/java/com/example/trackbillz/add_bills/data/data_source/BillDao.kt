package com.example.trackbillz.add_bills.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Upsert
    suspend fun insertBill(billEntity: BillEntity)

    @Insert
    suspend fun insertBillReturningId(billEntity: BillEntity): Long

    @Delete
    suspend fun deleteBill(billEntity: BillEntity)

    @Query("DELETE FROM billentity WHERE billId = :billId")
    suspend fun deleteBillById(billId: Int)

    @Query("SELECT * FROM billentity")
    fun getAllBills(): Flow<List<BillEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM BillEntity WHERE billId = :billId LIMIT 1)")
    suspend fun doesBillExist(billId: Int): Boolean
}