package com.example.trackbillz.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.trackbillz.core.presentation.components.AlertTextField
import com.example.trackbillz.core.presentation.components.AppTopBar
import com.example.trackbillz.core.presentation.components.BottomNavigationBar
import com.example.trackbillz.core.presentation.components.CustomSwitch
import com.example.trackbillz.core.presentation.components.CustomTextAlertBox
import com.example.trackbillz.core.presentation.utils.Screens
import com.example.trackbillz.ui.theme.TrackBillzTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}

@Composable
fun SettingsScreen(
    state: SettingsStates,
    onEvent: (SettingsEvents) -> Unit,
    navController: NavController
) {

    Scaffold(
        topBar = {
            AppTopBar(title = "Settings")
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) {paddingValues ->

        if(state.isInitialBillsDialogVisible){
            CustomTextAlertBox(
                title = "Set Initial Bill Number",
                fields = listOf(
                    AlertTextField(
                        label = "Bill No.",
                        value = state.initialBillNo,
                        onValueChange = {
                            onEvent(SettingsEvents.ChangeInitialBillNo(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        isError = state.billNumberError != null,
                        errorMessage = state.billNumberError
                    )
                ),
                onDismissRequest = {
                    onEvent(SettingsEvents.ChangeInitialBillsDialogVisible(false))
                },
                onConfirm = {
                    onEvent(SettingsEvents.SaveBillNumber)
                }
            )
        }

        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = state.userImageUri,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = rememberVectorPainter(Icons.Default.Person),
                        error = rememberVectorPainter(Icons.Default.Person)
                    )
                }
                
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Name",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            Text(
                                text = state.userName ?: "",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            Text(
                                text = state.userEmail ?: "",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }

                    CustomSwitch(
                        text = "Automate Bill Number",
                        checked = state.autoFillBillNo,
                        onCheckChange = {
                            onEvent(SettingsEvents.ChangeAutomateBills(it))
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screens.UserPinSetUpScreen(isResetPin = true))
                            }
                    ){
                        Icon(
                            imageVector = Icons.Default.LockReset,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Change Pin",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = null
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEvent(SettingsEvents.Logout)
                                navController.navigate(Screens.GoogleSignUpScreen){
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                    ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Logout",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                            contentDescription = null
                        )
                    }
                }
            }
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
        SettingsScreen(
            state = SettingsStates(
                userName = "Vivek Shah",
                userEmail = "shahvive138@gmail.com"
            ),
            onEvent = {},
            navController = rememberNavController()
        )
    }
}