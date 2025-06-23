package com.example.trackbillz.auth_feature.google_sign.presentation

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackbillz.R
import com.example.trackbillz.add_bills.presentation.AddBillsViewModel
import com.example.trackbillz.core.presentation.utils.Screens
import com.example.trackbillz.ui.theme.TrackBillzTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GoogleSignInRoot(
    viewModel: GoogleSignInViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val loginEvents = viewModel.loginEvents

    GoogleSignInScreen(
        state = state,
        onEvent = viewModel::onEvent,
        loginEvents = loginEvents,
        navController = navController
    )
}

@Composable
fun GoogleSignInScreen(
    state: GoogleSignUpStates,
    onEvent: (GoogleSignInEvents) -> Unit,
    loginEvents: Flow<AddBillsViewModel.ValidationEvent>,
    navController: NavController
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }



    LaunchedEffect(key1 = context) {
        loginEvents.collect{event->
            when(event){
                is AddBillsViewModel.ValidationEvent.Error -> {
                    Toast.makeText(context,
                        event.errorMessage,
                        Toast.LENGTH_SHORT).show()
                }
                is AddBillsViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context,
                        "Login Successful ",
                        Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.UserPinSetUpScreen(isResetPin = false))
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bill_invoice_ui), // your drawable icon here
                contentDescription = "App Icon",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome to\nTrackBillz",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Gray.copy(alpha = 0.5f),
                        blurRadius = 4f,
                        offset = Offset(2f, 2f)
                    )
                ),
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        onEvent(GoogleSignInEvents.SetLoadingTrue)
                        val result = accountManager.signInWithGoogle()
                        onEvent(GoogleSignInEvents.GoogleSignInClick(result = result))
                    }
                },
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(10.dp)
                    ),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = Color.Transparent,
                    disabledContentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = Color.Transparent
                )
            ){
                Icon(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google Icon",
                    tint = Color.Unspecified

                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Sign in with Google",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Gray.copy(alpha = 0.5f),
                            blurRadius = 4f,
                            offset = Offset(2f, 2f)
                        )
                    )
                )
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
        GoogleSignInScreen(
            state = GoogleSignUpStates(),
            onEvent = {},
            loginEvents = emptyFlow(),
            navController = rememberNavController()
        )
    }
}