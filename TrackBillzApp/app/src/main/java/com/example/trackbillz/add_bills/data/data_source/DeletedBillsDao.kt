package com.example.trackbillz.add_bills.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DeletedBillsDao {
    @Upsert
    suspend fun insertDeletedBill(deletedBillsEntity: DeletedBillsEntity)

    @Query("DELETE FROM DeletedBillsEntity WHERE billId = :billId")
    suspend fun deleteDeletedBillById(billId: Int)

    @Query("SELECT * FROM DeletedBillsEntity")
    suspend fun getAllDeletedBills(): List<DeletedBillsEntity>
}