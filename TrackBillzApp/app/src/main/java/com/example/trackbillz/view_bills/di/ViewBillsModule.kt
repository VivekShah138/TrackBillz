package com.example.trackbillz.view_bills.di

import com.example.trackbillz.add_bills.domain.repository.AddBillLocalRepository
import com.example.trackbillz.add_bills.domain.usecases.DeleteBillById
import com.example.trackbillz.add_bills.domain.usecases.DeleteDeletedBillById
import com.example.trackbillz.add_bills.domain.usecases.GetAllBills
import com.example.trackbillz.add_bills.domain.usecases.GetAllDeletedBills
import com.example.trackbillz.add_bills.domain.usecases.InsertDeletedBill
import com.example.trackbillz.core.domain.usecases.remote.DeleteBillFromCloud
import com.example.trackbillz.core.domain.usecases.remote.DeleteMultipleBillFromCloud
import com.example.trackbillz.core.domain.usecases.remote.InsertMultipleBillsToCloud
import com.example.trackbillz.view_bills.data.FileManager
import com.example.trackbillz.view_bills.domain.usecases.GetAllFilteredRecords
import com.example.trackbillz.view_bills.domain.usecases.SaveExcel
import com.example.trackbillz.view_bills.domain.usecases.ViewBillsUseCaseWrapper
import com.example.trackbillz.view_bills.presentation.ViewBillsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewBillsModule = module {

    single<FileManager> {
        FileManager(androidContext())
    }

    single<ViewBillsUseCaseWrapper>{
        val addBillLocalRepository = get<AddBillLocalRepository>()
        val fileManager = get<FileManager>()
        ViewBillsUseCaseWrapper(
            getAllBills = GetAllBills(addBillLocalRepository = addBillLocalRepository),
            getAllFilteredRecords = GetAllFilteredRecords(addBillLocalRepository = addBillLocalRepository),
            deleteBillById = DeleteBillById(addBillLocalRepository = addBillLocalRepository),
            saveExcel = SaveExcel(fileManager = fileManager),
            insertDeletedBill = InsertDeletedBill(addBillLocalRepository = addBillLocalRepository),
            deleteDeletedBillById = DeleteDeletedBillById(get()),
            deleteBillFromCloud = DeleteBillFromCloud(get()),
            getAllDeletedBills = GetAllDeletedBills(get()),
            insertMultipleBillsToCloud =  InsertMultipleBillsToCloud(get()),
            deleteMultipleBillFromCloud = DeleteMultipleBillFromCloud(get()),
        )
    }

    viewModel {
        ViewBillsViewModel(get())
    }



}