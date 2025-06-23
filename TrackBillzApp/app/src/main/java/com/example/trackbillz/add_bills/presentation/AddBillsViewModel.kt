package com.example.trackbillz.add_bills.presentation

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.add_bills.domain.usecases.AddBillsLocalUseCaseWrapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddBillsViewModel(
    private val addBillsLocalUseCaseWrapper: AddBillsLocalUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(
        AddBillsStates(
            billCGSTPercent = "6.0",
            billSGSTPercent = "6.0"
        )
    )
    val state: StateFlow<AddBillsStates> = _state.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {

        formatDate()
        setPreviousBillDetails()

    }

    fun onEvent(events: AddBillsEvents) {
        when (events) {
            is AddBillsEvents.ChangeBillCGSTPercent -> {
                if (events.cgstPercent.isEmpty() || events.cgstPercent.matches(Regex("^\\d*\\.?\\d*\$"))) {
                    _state.value = _state.value.copy(
                        billCGSTPercent = events.cgstPercent
                    )
                    setCGSTAmount()
                    setSGSTAmount()
                    setGrandTotal()
                }

            }

            is AddBillsEvents.ChangeBillSGSTPercent -> {
                if (events.sgstPercent.isEmpty() || events.sgstPercent.matches(Regex("^\\d*\\.?\\d*\$"))) {
                    _state.value = _state.value.copy(
                        billSGSTPercent = events.sgstPercent
                    )
                    setCGSTAmount()
                    setSGSTAmount()
                    setGrandTotal()
                }
            }

            is AddBillsEvents.ChangeBillNumber -> {
                _state.value = _state.value.copy(
                    billNumber = events.billNumber,
                    billNumberErrorMessage = null
                )
            }

            is AddBillsEvents.ChangeBillTotal -> {
                _state.value = _state.value.copy(
                    billTotal = events.billTotal,
                    billTotalErrorMessage = null
                )
                setCGSTAmount()
                setSGSTAmount()
                setGrandTotal()
            }

            is AddBillsEvents.ChangeBillType -> {
                _state.value = _state.value.copy(
                    billType = events.billType,
                    billTypeExpanded = events.expanded
                )
            }

            is AddBillsEvents.ChangeSearchTraderName -> {
                _state.value = _state.value.copy(
                    searchTraderName = events.name,
                    traderNameErrorMessage = null
                )
            }

            is AddBillsEvents.ChangeTraderGst -> {
                _state.value = _state.value.copy(
                    traderGst = events.gst
                )
            }

            is AddBillsEvents.SelectDate -> {
                _state.value = _state.value.copy(
                    selectedDay = events.day,
                    selectedMonth = events.month,
                    selectedYear = events.year
                )
                formatDate()
            }

            is AddBillsEvents.SetTraderName -> {
                _state.value = _state.value.copy(
                    traderName = events.name,
                    traderNameExpanded = events.expanded
                )
            }

            is AddBillsEvents.LoadTraderList -> {
                viewModelScope.launch {
                    addBillsLocalUseCaseWrapper.getAllTradersByType(type = state.value.billType.lowercase()).collect{list ->
                        _state.value = _state.value.copy(
                            tradersList = list
                        )
                    }
                }
            }

            is AddBillsEvents.AddBill -> {
                viewModelScope.launch {
                    addBill()
                }
            }

            is AddBillsEvents.ResetState -> {
                resetStates()
            }

            is AddBillsEvents.ShowDetailedBillTotal -> {
                _state.value = _state.value.copy(
                    showDetailedBillTotal = events.expanded
                )
            }
        }
    }

    private fun setCGSTAmount(){
        val total = _state.value.billTotal.toDoubleOrNull() ?: 0.0
        val cgstPercent = _state.value.billCGSTPercent.toDoubleOrNull() ?: 0.0
        val cgst = BigDecimal(total * (cgstPercent / 100))
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()

        _state.value = _state.value.copy(
            billCGSTAmount = cgst.toString()
        )
    }

    private fun setSGSTAmount(){
        val total = _state.value.billTotal.toDoubleOrNull() ?: 0.0
        val sgstPercent = _state.value.billSGSTPercent.toDoubleOrNull() ?: 0.0
        val sgst = BigDecimal(total * (sgstPercent / 100))
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()

        _state.value = _state.value.copy(
            billSGSTAmount = sgst.toString()
        )
    }

    private fun setGrandTotal(){
        val total = _state.value.billTotal.toDoubleOrNull() ?: 0.0
        val cgstAmount = _state.value.billCGSTAmount.toDoubleOrNull() ?: 0.0
        val sgstAmount = _state.value.billSGSTAmount.toDoubleOrNull() ?: 0.0

        val grandTotal = BigDecimal(total + cgstAmount + sgstAmount)
            .setScale(0, RoundingMode.HALF_UP)
            .toDouble()

        _state.value = _state.value.copy(
            billGrandTotal = grandTotal.toString()
        )
    }

    private fun formatDate(){
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, _state.value.selectedYear)
            set(Calendar.MONTH, _state.value.selectedMonth)
            set(Calendar.DAY_OF_MONTH, _state.value.selectedDay)
        }

        val dateFormat = SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)

        _state.value = _state.value.copy(
            formattedDate = formattedDate
        )
    }

    private suspend fun addBill(){
        val billNumberResult = addBillsLocalUseCaseWrapper.validateBillNumber(billNumber = _state.value.billNumber)
        val billTotalResult = addBillsLocalUseCaseWrapper.validateBillTotal(billTotal = _state.value.billTotal)
        val traderNameResult = addBillsLocalUseCaseWrapper.validateTradeName(
            trader = _state.value.searchTraderName,
            tradersList = _state.value.tradersList
        )

        val hasError = listOf(billTotalResult,billNumberResult,traderNameResult)

        if(hasError.any { !it.isSuccessful }){
            _state.value = _state.value.copy(
                billNumberErrorMessage = billNumberResult.errorMessage,
                billTotalErrorMessage =  billTotalResult.errorMessage,
                traderNameErrorMessage = traderNameResult.errorMessage
            )
            validationEventChannel.send(ValidationEvent.Error("Validation failed! Check errors."))
            return
        }

        val billNumber = _state.value.billNumber
        val billType = _state.value.billType
        val traderName = _state.value.traderName
        val traderGstNumber = _state.value.traderGst
        val billTotal = _state.value.billTotal
        val billGrandTotal = _state.value.billGrandTotal
        val traderCSGTPercent = _state.value.billCGSTPercent
        val traderSGSTPercent = _state.value.billSGSTPercent
        val billCGSTAmount = _state.value.billCGSTAmount
        val billSGSTAmount = _state.value.billSGSTAmount


        val bill = Bill(
            billNumber = billNumber,
            billType = billType.lowercase(),
            traderName = traderName,
            traderGst = traderGstNumber,
            date = dateConverter(),
            billTotalAmount = billTotal.toDoubleOrNull() ?: 0.0,
            billGrandTotal = billGrandTotal.toDoubleOrNull() ?: 0.0,
            billCGSTAmount = billCGSTAmount.toDoubleOrNull() ?: 0.0,
            billSGSTAmount = billSGSTAmount.toDoubleOrNull() ?: 0.0,
            traderCGSTPercent = traderCSGTPercent.toDoubleOrNull() ?: 0.0,
            traderSGSTPercent = traderSGSTPercent.toDoubleOrNull() ?: 0.0
        )

        try{
            val billId = addBillsLocalUseCaseWrapper.insertBillReturningId(bill = bill)
            Log.d("AddBillsViewModel","Id:- $billId")
            addBillsLocalUseCaseWrapper.insertBillToCloud(bill = bill.copy(billId = billId.toInt()))
            addBillsLocalUseCaseWrapper.insertBill(bill = bill.copy(billId = billId.toInt(),isSynced = true))
        }catch (e: Exception){
            Log.d("AddBillsViewModel","error:- ${e.message}")
            validationEventChannel.send(ValidationEvent.Error(e.localizedMessage))
            return
        }



        val billNumberInt = _state.value.billNumber.toIntOrNull() ?: 0
        addBillsLocalUseCaseWrapper.setPreviousBillNo(billNo = (billNumberInt+1).toString())
        addBillsLocalUseCaseWrapper.setPreviousBillDate(date = dateConverter())
        validationEventChannel.send(ValidationEvent.Success)
    }

    private fun dateConverter(): Long{
        val selectedDay = _state.value.selectedDay
        val selectedMonth = _state.value.selectedMonth
        val selectedYear = _state.value.selectedYear

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, selectedYear)
        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if(
            selectedDay == Calendar.getInstance().get(Calendar.DATE)
            && selectedMonth == Calendar.getInstance().get(Calendar.MONTH)
            && selectedYear == Calendar.getInstance().get(Calendar.YEAR)
        ){
            return Calendar.getInstance().timeInMillis
        }else{
            return calendar.timeInMillis
        }
    }

    private fun resetStates(){
        _state.value = AddBillsStates(
            billCGSTPercent = "6.0",
            billSGSTPercent = "6.0"
        )
        setPreviousBillDetails()
        formatDate()
    }

    private fun setPreviousBillDetails(){
        viewModelScope.launch {
            val billNumber = addBillsLocalUseCaseWrapper.getPreviousBillNo() ?: ""
            if(addBillsLocalUseCaseWrapper.getPreviousBillNumberState()){
                _state.value = _state.value.copy(
                    billNumber = billNumber
                )
            }

            val previousBillDate = addBillsLocalUseCaseWrapper.getPreviousBillDate()
            val calendar = Calendar.getInstance().apply { timeInMillis = previousBillDate ?: timeInMillis }
            _state.value = _state.value.copy(
                selectedYear = calendar.get(Calendar.YEAR),
                selectedMonth = calendar.get(Calendar.MONTH),
                selectedDay = calendar.get(Calendar.DAY_OF_MONTH),
            )
            formatDate()
        }
    }

    sealed class ValidationEvent{
        data object Success: ValidationEvent()
        data class Error(val errorMessage: String?): ValidationEvent()
    }

}