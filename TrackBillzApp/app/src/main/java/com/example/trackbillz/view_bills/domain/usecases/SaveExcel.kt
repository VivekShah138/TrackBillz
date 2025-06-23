package com.example.trackbillz.view_bills.domain.usecases

import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.view_bills.data.FileManager
import com.example.trackbillz.view_bills.data.SaveFileResult
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveExcel(
    private val fileManager: FileManager
) {

    suspend operator fun invoke(bills: List<Bill>, filterWrapper: FilterWrapper) : SaveFileResult = withContext(Dispatchers.IO){
        fileManager.exportBillsToExcel(bills = bills, filterWrapper = filterWrapper)
    }

}