package com.example.trackbillz.auth_feature.user_pin_setup.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackbillz.auth_feature.user_pin_setup.domain.usecases.UserPinSetUpUseCaseWrapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class UserPinSetUpViewModel(
    private val userPinSetUpUseCaseWrapper: UserPinSetUpUseCaseWrapper
) : ViewModel() {

    private val _state = MutableStateFlow(UserPinSetUpStates())
    val state: StateFlow<UserPinSetUpStates> = _state.asStateFlow()

    private val _navigationEvent = Channel<Unit>()
    val navigationEvent = _navigationEvent.receiveAsFlow()


    init{
        checkPinSetup()
        loadInitialData()
    }
    
    fun onEvent(events: UserPinSetUpEvents) {
        when(events){
            is UserPinSetUpEvents.OnEnterPin -> {
                when(state.value.pinSetupMode){
                    PinSetupMode.ResetPin -> enterNumberForReset(number = events.number, index = events.index)
                    PinSetupMode.SetInitialPin -> enterNumberForSetup(number = events.number, index = events.index)
                    PinSetupMode.ValidateExistingPin -> enterNumberForValidation(number = events.number, index = events.index)
                }

            }
            is UserPinSetUpEvents.OnFocusChange -> {
                _state.update { it.copy(
                    focusIndex = events.index
                ) }
            }
            is UserPinSetUpEvents.OnKeyBoardBack -> {
                val previousFocusIndex = getPreviousFocusIndex(currentIndex = _state.value.focusIndex)
                _state.update { it.copy(
                    pin = it.pin.mapIndexed { index, number ->
                        if(index == previousFocusIndex){
                            null
                        }else{
                            number
                        }
                    },
                    focusIndex = previousFocusIndex
                ) }
            }
            is UserPinSetUpEvents.ChangeLoadingState -> {
                _state.value = _state.value.copy(
                    isLoading = events.loading
                )
            }

            is UserPinSetUpEvents.ChangePinSetupMode -> {
                _state.value = _state.value.copy(
                    pinSetupMode = events.mode,
                    pinSetupStep = events.step
                )
            }

            is UserPinSetUpEvents.LogoutUser -> {
                viewModelScope.launch {
                    userPinSetUpUseCaseWrapper.signOutUser()
                }
            }
        }
    }

    private fun getPreviousFocusIndex(currentIndex: Int?): Int?{
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getFirstEmptyFieldAfterFocusedIndex(
        currentPin: List<Int?>,
        currentFocusedIndex: Int
    ): Int{
        currentPin.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex){
                return@forEachIndexed
            }
            if(number == null){
                return index
            }
        }
        return currentFocusedIndex
    }

    private fun getNextFocusedTextFieldIndex(
        currentPin: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if(currentFocusedIndex == null){
            return null
        }

        if(currentFocusedIndex == 3){
            return currentFocusedIndex
        }

        return getFirstEmptyFieldAfterFocusedIndex(
            currentPin = currentPin,
            currentFocusedIndex =  currentFocusedIndex
        )
    }

    private fun enterNumberForValidation(
        number: Int?,
        index: Int,
    ){

        val newPin = _state.value.pin.mapIndexed { currentIndex, currentNumber ->
            _state.update { it.copy(error = null) }
            if(index == currentIndex){
                number
            }else{
                currentNumber
            }
        }

        val wasNumberRemoved = number == null

        _state.update { it.copy(
            pin = newPin,
            focusIndex = if(wasNumberRemoved || it.pin.getOrNull(index) != null){
                it.focusIndex
            }else{
                getNextFocusedTextFieldIndex(
                    currentPin = it.pin,
                    currentFocusedIndex = it.focusIndex
                )
            }
        ) }

        if (newPin.none { it == null }) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                if(_state.value.isPinSet){
                    val isValidPin = validatePin(newPin.joinToString(""))
                    if(isValidPin){
                        _navigationEvent.send(Unit)
                    }else{
                        _state.update { it.copy(
                            isLoading = false,
                            error = "Please enter valid PIN"
                        ) }
                    }
                }else{
                    userPinSetUpUseCaseWrapper.setUserPin(pin = newPin.joinToString(""))
                    _state.update { it.copy(isPinSet = true) }
                }
            }
        }
    }

    private fun enterNumberForSetup(
        number: Int?,
        index: Int
    ){
        when(_state.value.pinSetupStep){
            PinSetupStep.EnterPin -> handleEnterPin(number = number,index = index)
            PinSetupStep.ConfirmPin -> handleConfirmPin(number = number, index = index)
            else -> Unit
        }
    }

    private fun handleEnterPin(
        number: Int?,
        index: Int
    ){
        val newPin = _state.value.pin.mapIndexed { currentIndex, currentNumber ->
            if(index == currentIndex){
                number
            }else{
                currentNumber
            }
        }

        val wasNumberRemoved = number == null

        _state.update { it.copy(
            pin = newPin,
            focusIndex = if(wasNumberRemoved || it.pin.getOrNull(index) != null){
                it.focusIndex
            }else{
                getNextFocusedTextFieldIndex(
                    currentPin = it.pin,
                    currentFocusedIndex = it.focusIndex
                )
            }
        ) }

        if (newPin.none { it == null }) {
            _state.update { it.copy(
                pin = newPin,
                pinSetupStep = PinSetupStep.ConfirmPin,
                focusIndex = 0,
                error = null
            ) }
        }

    }

    private fun handleConfirmPin(
        number: Int?,
        index: Int
    ){
        val newPin = _state.value.confirmPin.mapIndexed { currentIndex, currentNumber ->
            if(index == currentIndex){
                number
            }else{
                currentNumber
            }
        }

        val wasNumberRemoved = number == null

        _state.update { it.copy(
            confirmPin = newPin,
            focusIndex = if(wasNumberRemoved || it.confirmPin.getOrNull(index) != null){
                it.focusIndex
            }else{
                getNextFocusedTextFieldIndex(
                    currentPin = it.confirmPin,
                    currentFocusedIndex = it.focusIndex
                )
            }
        ) }

        if (newPin.none { it == null }) {

            val pin = _state.value.pin.joinToString("")
            val confirmPin = newPin.joinToString("")

            if(pin == confirmPin){
                _state.update { it.copy(
                    confirmPin = newPin,
                    pinSetupStep = PinSetupStep.Completed,
                    isLoading = true
                ) }
                viewModelScope.launch {
                    userPinSetUpUseCaseWrapper.setUserPin(pin = confirmPin)
                    _navigationEvent.send(Unit)
                }
            }else{
                _state.update { it.copy(
                    pin = (1..4).map { null },
                    confirmPin = (1..4).map { null },
                    pinSetupStep = PinSetupStep.EnterPin,
                    focusIndex = 0,
                    error = "Passwords don't match. Start again"
                ) }
            }
        }
    }

    private fun enterNumberForReset(
        number: Int?,
        index: Int
    ){
        when(state.value.pinSetupStep){
            PinSetupStep.EnterPin -> handleEnterPin(number = number,index = index)
            PinSetupStep.ConfirmPin -> handleConfirmPin(number = number, index = index)
            PinSetupStep.Completed -> Unit
            PinSetupStep.EnterOldPin -> handleOldPin(number = number,index = index)
        }
    }

    private fun handleOldPin(
        number: Int?,
        index: Int
    ){
        val newPin = _state.value.oldPin.mapIndexed { currentIndex, currentNumber ->
            _state.update { it.copy(error = null) }
            if(index == currentIndex){
                number
            }else{
                currentNumber
            }
        }

        val wasNumberRemoved = number == null

        _state.update { it.copy(
            oldPin = newPin,
            focusIndex = if(wasNumberRemoved || it.pin.getOrNull(index) != null){
                it.focusIndex
            }else{
                getNextFocusedTextFieldIndex(
                    currentPin = it.oldPin,
                    currentFocusedIndex = it.focusIndex
                )
            }
        ) }

        if (newPin.none { it == null }) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                val isValidPin = validatePin(newPin.joinToString(""))
                if(isValidPin){
                    _state.update { it.copy(
                        isLoading = false,
                        focusIndex = 0,
                        pinSetupStep = PinSetupStep.EnterPin
                    ) }
                }else{
                    _state.update { it.copy(
                        isLoading = false,
                        error = "Please enter valid PIN",
                        pinSetupStep = PinSetupStep.EnterOldPin
                    ) }
                }
            }
        }
    }
    private fun checkPinSetup(){
        viewModelScope.launch {
            val pin = userPinSetUpUseCaseWrapper.getUserPin()
            if(pin != null){
                _state.value = _state.value.copy(
                    isPinSet = true,
                )
            }
            else{
                _state.value = _state.value.copy(
                    pinSetupMode = PinSetupMode.SetInitialPin
                )
            }
        }
    }

    private suspend fun validatePin(enteredPin: String): Boolean {
        val pin = userPinSetUpUseCaseWrapper.getUserPin()
        return pin == enteredPin
    }

    private fun loadInitialData(){
        viewModelScope.launch {
            userPinSetUpUseCaseWrapper.syncRemoteBillsToLocal()
        }
    }

}