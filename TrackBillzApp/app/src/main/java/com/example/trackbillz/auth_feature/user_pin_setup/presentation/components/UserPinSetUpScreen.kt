package com.example.trackbillz.auth_feature.user_pin_setup.presentation.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackbillz.auth_feature.user_pin_setup.presentation.PinSetupMode
import com.example.trackbillz.auth_feature.user_pin_setup.presentation.PinSetupStep
import com.example.trackbillz.auth_feature.user_pin_setup.presentation.UserPinSetUpEvents
import com.example.trackbillz.auth_feature.user_pin_setup.presentation.UserPinSetUpStates
import com.example.trackbillz.auth_feature.user_pin_setup.presentation.UserPinSetUpViewModel
import com.example.trackbillz.core.presentation.components.AppTopBar
import com.example.trackbillz.core.presentation.utils.Screens
import com.example.trackbillz.ui.theme.TrackBillzTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserPinSetUpRoot(
    viewModel: UserPinSetUpViewModel = koinViewModel(),
    navController: NavController,
    isResetPin: Boolean
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigationEvent = viewModel.navigationEvent
    val focusRequester = remember {
        (1..4).map { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusIndex) {

        state.focusIndex?.let {index ->
            focusRequester.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.pin,keyboardManager,state.pinSetupStep) {
        val allNumbersEntered = state.pin.none { it == null }
        if(allNumbersEntered){
            focusRequester.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    UserPinSetUpScreen(
        state = state,
        onEvent = {event ->
            when(event){
                is UserPinSetUpEvents.OnEnterPin -> {
                    if(event.number != null){
                        focusRequester[event.index].freeFocus()
                    }
                }
                else -> Unit
            }
            viewModel.onEvent(event)
        },
        focusRequester = focusRequester,
        navController = navController,
        navigationEvent = navigationEvent,
        isResetPin = isResetPin
    )
}

@Composable
fun UserPinSetUpScreen(
    state: UserPinSetUpStates,
    onEvent: (UserPinSetUpEvents) -> Unit,
    focusRequester: List<FocusRequester>,
    navController: NavController,
    navigationEvent: Flow<Unit>,
    isResetPin: Boolean
) {

    LaunchedEffect(Unit) {
        navigationEvent.collect {
            delay(2500)
            onEvent(UserPinSetUpEvents.ChangeLoadingState(false))
            navController.navigate(Screens.AllBillRecordsScreen){
                popUpTo(Screens.UserPinSetUpScreen(isResetPin = false)){
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(isResetPin) {
        if(isResetPin){
            onEvent(UserPinSetUpEvents.ChangePinSetupMode(mode = PinSetupMode.ResetPin, step = PinSetupStep.EnterOldPin))
        }
    }
    
    BackHandler {
        onEvent(UserPinSetUpEvents.LogoutUser)
        navController.navigate(Screens.GoogleSignUpScreen)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        if(state.pinSetupMode == PinSetupMode.ResetPin){
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .offset(y = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when(state.pinSetupMode){
                    PinSetupMode.ResetPin -> {
                        when(state.pinSetupStep){
                            PinSetupStep.EnterPin -> "Set your new 4-digit PIN"
                            PinSetupStep.ConfirmPin -> "Re-enter your 4-digit PIN"
                            PinSetupStep.Completed -> "Pin Setup Completed"
                            PinSetupStep.EnterOldPin -> "Enter your old PIN"
                        }
                    }
                    PinSetupMode.SetInitialPin -> {
                        when(state.pinSetupStep){
                            PinSetupStep.EnterPin -> "Set your new 4-digit PIN"
                            PinSetupStep.ConfirmPin -> "Re-enter your 4-digit PIN"
                            else -> "Pin Setup Completed"
                        }
                    }
                    PinSetupMode.ValidateExistingPin -> "Enter your 4-digit PIN"
                },
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                )
            )
            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }



            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ){
                val currentPin = when (state.pinSetupStep) {
                    PinSetupStep.ConfirmPin -> state.confirmPin
                    PinSetupStep.EnterOldPin -> state.oldPin
                    else -> state.pin
                }

                currentPin.forEachIndexed { index, number ->
                    PinInputField(
                        number = number,
                        focusRequester = focusRequester[index],
                        onFocusChanged = { isFocused ->
                            if(isFocused){
                                onEvent(UserPinSetUpEvents.OnFocusChange(index))
                            }
                        },
                        onNumberChanged = {newNumber ->
                            onEvent(UserPinSetUpEvents.OnEnterPin(number = newNumber, index = index))
                        },
                        onBackPressed = {
                            onEvent(UserPinSetUpEvents.OnKeyBoardBack)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .align(Alignment.Center)
            )
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun Preview() {
    TrackBillzTheme {
        UserPinSetUpScreen(
            state = UserPinSetUpStates(
                pinSetupStep = PinSetupStep.EnterOldPin,
                pinSetupMode = PinSetupMode.ResetPin,
                error = "Please Enter Correct Pin",
                isLoading = true
            ),
            onEvent = {},
            focusRequester = remember { (1..4).map { FocusRequester() } },
            navController = rememberNavController(),
            navigationEvent = emptyFlow(),
            isResetPin = false,
        )
    }
}