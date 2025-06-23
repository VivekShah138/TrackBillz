package com.example.trackbillz.add_bills.di

import androidx.room.Room
import com.example.trackbillz.add_bills.data.data_source.BILL_MIGRATION_1_2
import com.example.trackbillz.add_bills.data.data_source.BillDao
import com.example.trackbillz.add_bills.data.data_source.BillDatabase
import com.example.trackbillz.add_bills.data.data_source.DeletedBillsDao
import com.example.trackbillz.add_bills.data.repository.AddBillLocalRepositoryImpl
import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository
import com.example.trackbillz.add_bills.domain.usecases.AddBillsLocalUseCaseWrapper
import com.example.trackbillz.add_bills.domain.usecases.DeleteBill
import com.example.trackbillz.add_bills.domain.usecases.DoesBillExist
import com.example.trackbillz.add_bills.domain.usecases.GetAllBills
import com.example.trackbillz.add_bills.domain.usecases.InsertBill
import com.example.trackbillz.add_bills.domain.usecases.InsertBillReturningId
import com.example.trackbillz.add_bills.domain.usecases.ValidateBillNumber
import com.example.trackbillz.add_bills.domain.usecases.ValidateBillTotal
import com.example.trackbillz.add_bills.domain.usecases.ValidateTradeName
import com.example.trackbillz.add_bills.presentation.AddBillsViewModel
import com.example.trackbillz.core.domain.repository.local.DataStoreRepository
import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillDate
import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.local.GetPreviousBillNumberState
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillDate
import com.example.trackbillz.core.domain.usecases.local.SetPreviousBillNo
import com.example.trackbillz.core.domain.usecases.remote.GetBillsFromCloud
import com.example.trackbillz.core.domain.usecases.remote.InsertBillToCloud
import com.example.trackbillz.traders.domain.repository.TradersLocalRepository
import com.example.trackbillz.traders.domain.usecases.GetAllTradersByType
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val addBillsModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            BillDatabase::class.java,
            BillDatabase.DATABASE_NAME
        )
            .addMigrations(BILL_MIGRATION_1_2)
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single<BillDao> {
        get<BillDatabase>().billDao
    }

    single<DeletedBillsDao> {
        get<BillDatabase>().deletedBillsDao
    }

    single<AddBillLocalRepository> {
        AddBillLocalRepositoryImpl(
            billDao = get<BillDao>(),
            deletedBillsDao = get<DeletedBillsDao>()
        )
    }

    single<AddBillsLocalUseCaseWrapper>{
        val tradersLocalRepository = get<TradersLocalRepository>()
        val addBillLocalRepository = get<AddBillLocalRepository>()
        val dataStoreRepository = get<DataStoreRepository>()
        AddBillsLocalUseCaseWrapper(
            getAllBills = GetAllBills(addBillLocalRepository = addBillLocalRepository),
            getAllTradersByType = GetAllTradersByType(tradersLocalRepository = tradersLocalRepository),
            deleteBill = DeleteBill(addBillLocalRepository = addBillLocalRepository),
            insertBill = InsertBill(addBillLocalRepository = addBillLocalRepository),
            validateBillTotal = ValidateBillTotal(),
            validateBillNumber = ValidateBillNumber(),
            validateTradeName = ValidateTradeName(),
            getPreviousBillNo = GetPreviousBillNo(dataStoreRepository = dataStoreRepository),
            getPreviousBillNumberState = GetPreviousBillNumberState(dataStoreRepository = dataStoreRepository),
            setPreviousBillNo = SetPreviousBillNo(dataStoreRepository = dataStoreRepository),
            setPreviousBillDate = SetPreviousBillDate(dataStoreRepository = dataStoreRepository),
            getPreviousBillDate = GetPreviousBillDate(dataStoreRepository = dataStoreRepository),
            insertBillReturningId = InsertBillReturningId(get()),
            insertBillToCloud = InsertBillToCloud(get()),
            doesBillExist = DoesBillExist(get()),
            getBillsFromCloud = GetBillsFromCloud(get())
        )
    }

    viewModel {
        AddBillsViewModel(get())
    }

}