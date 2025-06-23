package com.example.trackbillz.settings.presentation

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackbillz.settings.domain.use_cases.SettingsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class SettingsViewModel(
    private val settingsUseCaseWrapper: SettingsUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsStates())
    val state: StateFlow<SettingsStates> = _state.asStateFlow()

    init {
        setBillNumberState()
        setUserInfo()
    }

    fun onEvent(events: SettingsEvents) {
        when (events) {
            is SettingsEvents.ChangeAutomateBills -> {
                _state.value = _state.value.copy(
                    autoFillBillNo = events.state
                )
                if(_state.value.autoFillBillNo){
                    _state.value = _state.value.copy(
                        isInitialBillsDialogVisible = true
                    )
                }
                viewModelScope.launch {
                    settingsUseCaseWrapper.setPreviousBillNumberState(state = _state.value.autoFillBillNo)
                }
            }

            is SettingsEvents.ChangeInitialBillsDialogVisible -> {
                _state.value = _state.value.copy(
                    isInitialBillsDialogVisible = events.state
                )
            }

            is SettingsEvents.ChangeInitialBillNo -> {
                _state.value = _state.value.copy(
                    initialBillNo = events.text,
                    billNumberError = null
                )
            }

            is SettingsEvents.SaveBillNumber -> {
                val validBillNumber = _state.value.initialBillNo.isDigitsOnly()
                val nonEmptyBillNumber = _state.value.initialBillNo.isNotEmpty()


                if(validBillNumber && nonEmptyBillNumber){
                    viewModelScope.launch {
                        saveBillNoToDataStore()
                    }
                    _state.value = _state.value.copy(
                        isInitialBillsDialogVisible = false
                    )
                }
                else{
                    _state.value = _state.value.copy(
                        billNumberError = "Please Enter Valid Bill Number"
                    )
                }
            }

            is SettingsEvents.Logout -> {
                viewModelScope.launch {
                    settingsUseCaseWrapper.signOutUser()
                }
            }
        }
    }

    private suspend fun saveBillNoToDataStore (){
        settingsUseCaseWrapper.setPreviousBillNo(
            billNo = _state.value.initialBillNo
        )
    }

    private fun setBillNumberState (){
        viewModelScope.launch {
            val state = settingsUseCaseWrapper.getPreviousBillNumberState()
            val billNo = settingsUseCaseWrapper.getPreviousBillNo() ?: ""
            _state.value = _state.value.copy(
                initialBillNo = billNo,
                autoFillBillNo = state
            )
        }
    }

    private fun setUserInfo(){
        viewModelScope.launch {
            val userInfo = settingsUseCaseWrapper.getUserInfo()
            Log.d("SettingsViewModel","UserInfo: ${settingsUseCaseWrapper.getUserInfo()}")
            _state.value = _state.value.copy(
                userImageUri = userInfo.profileURI,
                userEmail = userInfo.email,
                userName = userInfo.name
            )
        }
    }
}