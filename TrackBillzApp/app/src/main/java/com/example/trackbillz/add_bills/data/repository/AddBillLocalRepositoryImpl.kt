package com.example.trackbillz.add_bills.data.repository

import android.util.Log
import com.example.trackbillz.add_bills.data.data_source.BillDao
import com.example.trackbillz.add_bills.data.data_source.BillEntity
import com.example.trackbillz.add_bills.data.data_source.DeletedBillsDao
import com.example.trackbillz.add_bills.data.data_source.DeletedBillsEntity
import com.example.trackbillz.add_bills.data.utils.toDomain
import com.example.trackbillz.add_bills.data.utils.toEntity
import com.example.trackbillz.add_bills.data.utils.toModel
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.model.DeletedBill
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper
import com.example.trackbillz.view_bills.presentation.util.ItemDuration
import com.example.trackbillz.view_bills.presentation.util.ItemSortType
import com.example.trackbillz.view_bills.presentation.util.ItemType
import com.example.trackbillz.view_bills.presentation.util.SortOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddBillLocalRepositoryImpl(
    private val billDao: BillDao,
    private val deletedBillsDao: DeletedBillsDao
): AddBillLocalRepository {
    override suspend fun insertBill(bill: Bill)  = withContext(Dispatchers.IO){
        billDao.insertBill(bill.toEntity())
    }

    override suspend fun insertBillReturningId(bill: Bill): Long  = withContext(Dispatchers.IO){
        billDao.insertBillReturningId(billEntity = bill.toEntity())
    }

    override suspend fun deleteBill(bill: Bill) = withContext(Dispatchers.IO){
        billDao.deleteBill(bill.toEntity())
    }

    override suspend fun deleteBillById(billId: Int) = withContext(Dispatchers.IO) {
        billDao.deleteBillById(billId = billId)
    }

    override fun getAllBills(): Flow<List<Bill>> {
        return billDao.getAllBills().map { billList->
            billList.map { it.toModel() }
        }
    }

    override fun getAllFilteredBills(filterWrapper: FilterWrapper): Flow<List<Bill>> {
        return billDao.getAllBills().map { bills ->

            var filteredBills = bills
            Log.d("GetAllFilteredRecords","filtered Bills initial: $filteredBills")

            val type = filterWrapper.itemType
            val sortType = filterWrapper.itemSortType
            val duration = filterWrapper.itemDuration

            filteredBills = when(type){
                is ItemType.Both -> filteredBills
                is ItemType.Sales -> filteredBills.filter{it.billType.equals("sales", ignoreCase = true)}
                is ItemType.Purchase -> filteredBills.filter{it.billType.equals("purchase", ignoreCase = true)}
            }

            Log.d("GetAllFilteredRecords","filtered Bills type: $filteredBills")

            filteredBills = when(sortType.sortOrder){
                is SortOrder.Ascending -> {
                    when(sortType){
                        is ItemSortType.Date -> filteredBills.sortedBy{it.date}
                        is ItemSortType.Title -> filteredBills.sortedBy { it.traderName.lowercase() }
                    }
                }
                is SortOrder.Descending -> {
                    when(sortType){
                        is ItemSortType.Date -> filteredBills.sortedByDescending{it.date}
                        is ItemSortType.Title -> filteredBills.sortedByDescending { it.traderName.lowercase() }
                    }
                }
            }

            Log.d("GetAllFilteredRecords","filtered Bills sortyBy: $filteredBills")

            filteredBills = when(duration){
                is ItemDuration.Today -> {
                    val startOfToday = getStartOfTodayInMillis()
                    val endOfToday = Calendar.getInstance().timeInMillis
                    filteredBills.filter { it.date in startOfToday .. endOfToday }
                }
                is ItemDuration.ThisMonth -> {
                    val startOfThisMonth = getStartOfMonthInMillis()
                    val endOfThisMonth = Calendar.getInstance().timeInMillis
                    filteredBills.filter { it.date in startOfThisMonth .. endOfThisMonth }
                }
                is ItemDuration.LastMonth -> {
                    val startOfLastMonth = getStartOfLastMonthInMillis()
                    val endOfLastMonth = getEndOfLastMonthInMillis()
                    filteredBills.filter { it.date in startOfLastMonth .. endOfLastMonth }
                }
                is ItemDuration.Last3Months -> {
                    val startOfLast3Month = getStartOfLast3MonthsInMillis()
                    val endOfLast3Month = getEndOfLastMonthInMillis()
                    filteredBills.filter { it.date in startOfLast3Month .. endOfLast3Month }
                }
                is ItemDuration.CustomRange -> {
                    val startOfRange = duration.from
                    val endOfRange = getEndOfDayInMillis(duration.to)
                    filteredBills.filter { it.date in startOfRange .. endOfRange }
                }
            }
            Log.d("GetAllFilteredRecords","filtered Bills duration: $filteredBills")
            filteredBills.map { it.toModel() }
        }
    }

    override suspend fun doesBillExist(billId: Int): Boolean = withContext(Dispatchers.IO) {
        billDao.doesBillExist(billId = billId)
    }

    override suspend fun insertDeletedBill(deletedBill: DeletedBill) = withContext(Dispatchers.IO){
        deletedBillsDao.insertDeletedBill(deletedBill.toEntity())
    }

    override suspend fun deleteDeletedBillById(billId: Int) = withContext(Dispatchers.IO){
        deletedBillsDao.deleteDeletedBillById(billId = billId)
    }

    override suspend fun getAllDeletedBills(): List<DeletedBill> = withContext(Dispatchers.IO) {
        deletedBillsDao.getAllDeletedBills().map { it.toDomain() }
    }


    private fun getStartOfTodayInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfTodayInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    private fun getStartOfMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        Log.d("ViewTransactionsViewModel","This Month First Day ${Calendar.DAY_OF_MONTH}")
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dateThisMonth = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.timeInMillis)
        Log.d("ViewTransactionsViewModel","This Month long${calendar.timeInMillis}")
        Log.d("ViewTransactionsViewModel","This Month date format $dateThisMonth")
        return calendar.timeInMillis
    }

    private fun getStartOfLastMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfLastMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    private fun getStartOfLast3MonthsInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -3)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }


    private fun getEndOfDayInMillis(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }
}