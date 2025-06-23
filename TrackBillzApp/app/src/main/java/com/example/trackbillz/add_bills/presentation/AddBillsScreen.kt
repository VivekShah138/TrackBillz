package com.example.trackbillz.add_bills.presentation

import BillTypeSegmentedButton
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackbillz.add_bills.presentation.components.BillTypeDropdown
import com.example.trackbillz.add_bills.presentation.components.CustomWithErrorTextField2
import com.example.trackbillz.add_bills.presentation.components.ItemsListDropdown2
import com.example.trackbillz.core.presentation.components.AppTopBar
import com.example.trackbillz.core.presentation.components.BottomNavigationBar
import com.example.trackbillz.ui.theme.TrackBillzTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.viewmodel.koinViewModel
import java.util.Calendar

@Composable
fun AddBillsRoot(
    viewModel: AddBillsViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val validationEvents = viewModel.validationEvents

    AddBillsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        validationEvents = validationEvents,
        navController = navController
    )
}

@Composable
fun AddBillsScreen(
    state: AddBillsStates,
    onEvent: (AddBillsEvents) -> Unit,
    validationEvents: Flow<AddBillsViewModel.ValidationEvent>,
    navController: NavController
) {

    val context = LocalContext.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = context) {
        validationEvents.collect{events ->

            when(events){
                is AddBillsViewModel.ValidationEvent.Error -> {
                    Toast.makeText(context, "Please check the error message ", Toast.LENGTH_SHORT).show()
                }
                is AddBillsViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, "Bill Added Successfully ", Toast.LENGTH_SHORT).show()
                    onEvent(AddBillsEvents.ResetState)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Bill",
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Text(
                text = "Bill Type",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            BillTypeDropdown(
                expanded = state.billTypeExpanded,
                onDismissRequest = {
                    onEvent(
                        AddBillsEvents.ChangeBillType(
                            billType = state.billType,
                            expanded = false
                        )
                    )
                },
                onExpandedChange = {
                    onEvent(
                        AddBillsEvents.ChangeBillType(
                            billType = state.billType,
                            expanded = true
                        )
                    )
                },
                selectedOption = state.billType,
                options = state.billTypeList,
                onItemSelect = {
                    onEvent(
                        AddBillsEvents.ChangeBillType(
                            billType = it,
                            expanded = false
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bill Date",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp)),
                tonalElevation = 2.dp,
                shadowElevation = 4.dp
            ) {
                OutlinedTextField(
                    value = state.formattedDate,
                    onValueChange =  {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date",
                            modifier = Modifier.clickable {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, day ->
                                        onEvent(AddBillsEvents.SelectDate(year = year,month = month,day = day))
                                    },
                                    state.selectedYear,
                                    state.selectedMonth,
                                    state.selectedDay
                                ).apply {
                                    datePicker.maxDate = Calendar.getInstance().timeInMillis
                                }.show()
                            }
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.onBackground
                    ),
                    placeholder = {
                        Text(
                            text = "Select a Date",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bill Number",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            CustomWithErrorTextField2(
                text = state.billNumber,
                label = "Enter the Number",
                onValueChange = {
                    onEvent(AddBillsEvents.ChangeBillNumber(it))
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                singleLine = true,
                inputType = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                ),
                isError = state.billNumberErrorMessage != null,
                errorMessage = state.billNumberErrorMessage ?: "",
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Trader Name",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            ItemsListDropdown2(
                selectedItem = state.searchTraderName,
                itemsList = state.tradersList,
                expanded = state.traderNameExpanded,
                loadList = {
                    onEvent(AddBillsEvents.LoadTraderList)
                },
                onSearchValueChange = {
                    onEvent(AddBillsEvents.ChangeSearchTraderName(it))
                },
                onItemSelect = {trader ->
                    onEvent(AddBillsEvents.ChangeSearchTraderName(trader.name))
                    onEvent(AddBillsEvents.SetTraderName(
                        name = trader.name,
                        expanded = false
                    ))
                    onEvent(AddBillsEvents.ChangeTraderGst(gst = trader.gstNumber))
                    keyboardManager?.hide()
                },
                onExpandedChange = {
                    onEvent(AddBillsEvents.SetTraderName(
                        name = state.traderName,
                        expanded = it
                    ))
                    if(it){
                        onEvent(AddBillsEvents.LoadTraderList)
                    }
                },
                displayText = {it.name},
                errorMessage = state.traderNameErrorMessage ?: "",
                isError = state.traderNameErrorMessage != null,
                label = "Select a Trader"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bill Total",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            CustomWithErrorTextField2(
                text = state.billTotal,
                label = "Enter the Bill Total",
                onValueChange = {
                    onEvent(AddBillsEvents.ChangeBillTotal(it))
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                singleLine = true,
                inputType = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = state.billTotalErrorMessage != null,
                errorMessage = state.billTotalErrorMessage ?: "",
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Grand Total",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = if (state.showDetailedBillTotal) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onEvent(AddBillsEvents.ShowDetailedBillTotal(!state.showDetailedBillTotal))
                    }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Surface (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp)),
                tonalElevation = 2.dp,
                shadowElevation = 4.dp
            ) {
                OutlinedTextField(
                    value = state.billGrandTotal,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.onBackground
                    ),
                    placeholder = {
                        Text(
                            text = "Enter the Grand Total",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            if(state.showDetailedBillTotal){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(modifier = Modifier.weight(2f)){
                        Text(
                            text = "CGST Amount",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface (
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(5.dp)),
                            tonalElevation = 2.dp,
                            shadowElevation = 4.dp
                        ) {
                            OutlinedTextField(
                                value = state.billCGSTAmount,
                                onValueChange = {},
                                readOnly = true,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = MaterialTheme.colorScheme.onBackground
                                ),
                                placeholder = {
                                    Text(
                                        text = "Enter CGST %",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 16.sp
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)){
                        Text(
                            text = "CGST %",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface (
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(5.dp)),
                            tonalElevation = 2.dp,
                            shadowElevation = 4.dp
                        ) {
                            OutlinedTextField(
                                value = state.billCGSTPercent,
                                onValueChange = {
                                    onEvent(AddBillsEvents.ChangeBillCGSTPercent(it))
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column(modifier = Modifier.weight(2f)){
                        Text(
                            text = "SGST Amount",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface (
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(5.dp)),
                            tonalElevation = 2.dp,
                            shadowElevation = 4.dp
                        ) {
                            OutlinedTextField(
                                value = state.billSGSTAmount,
                                onValueChange = {},
                                readOnly = true,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = MaterialTheme.colorScheme.onBackground
                                ),
                                placeholder = {
                                    Text(
                                        text = "Enter SGST %",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 16.sp
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)){
                        Text(
                            text = "SGST %",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface (
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(5.dp)),
                            tonalElevation = 2.dp,
                            shadowElevation = 4.dp
                        ) {
                            OutlinedTextField(
                                value = state.billSGSTPercent,
                                onValueChange = {
                                    onEvent(AddBillsEvents.ChangeBillSGSTPercent(it))
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    cursorColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    onEvent(AddBillsEvents.AddBill)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Add Bill")
            }

        }
    }
}

@Preview
@Composable
private fun Preview() {
    TrackBillzTheme  {
        AddBillsScreen(
            state = AddBillsStates(
                billNumberErrorMessage = "Please enter bill number",
                billTotalErrorMessage = "please enter correct bill total",
                traderNameErrorMessage = "please enter trader name"
            ),
            onEvent = {},
            validationEvents = emptyFlow(),
            navController = rememberNavController()
        )
    }
}