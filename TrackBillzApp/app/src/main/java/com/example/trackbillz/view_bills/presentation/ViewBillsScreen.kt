package com.example.trackbillz.view_bills.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.core.presentation.components.AppTopBar
import com.example.trackbillz.core.presentation.components.BottomNavigationBar
import com.example.trackbillz.core.presentation.components.CustomDeleteAlertBox
import com.example.trackbillz.core.presentation.utils.MenuItems
import com.example.trackbillz.traders.presentation.TraderEvents
import com.example.trackbillz.ui.theme.TrackBillzTheme
import com.example.trackbillz.view_bills.presentation.component.BillEntryCard
import com.example.trackbillz.view_bills.presentation.component.ButtonSection
import com.example.trackbillz.view_bills.presentation.component.FilterBottomSheetModal
import com.example.trackbillz.view_bills.presentation.component.ItemDisplayList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ViewBillsRoot(
    viewModel: ViewBillsViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val saveFileEvent = viewModel.saveFileEvent

    ViewBillsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController,
        saveFileEvent = saveFileEvent
    )
}

@Composable
fun ViewBillsScreen(
    state: ViewBillsStates,
    onEvent: (ViewBillsEvents) -> Unit,
    navController: NavController,
    saveFileEvent: Flow<ViewBillsViewModel.SaveFileEvent>
) {

    BackHandler (enabled = state.selectionMode) {
        onEvent(ViewBillsEvents.ExitSelectionMode)
    }

    val context = LocalContext.current

    LaunchedEffect(context) {
        saveFileEvent.collect{result ->
            when(result){
                is ViewBillsViewModel.SaveFileEvent.Result -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
           AppTopBar(
               title = if (state.selectionMode) state.selectedItems.size.toString() else "Records",
               titleTextStyle = if(state.selectionMode) {
                   MaterialTheme.typography.bodySmall.copy(
                       fontSize = 20.sp,
                       color = MaterialTheme.colorScheme.onBackground,
                       fontWeight = FontWeight.Normal
                   )
               } else {
                   MaterialTheme.typography.titleLarge.copy(
                       fontSize = 30.sp,
                       color = MaterialTheme.colorScheme.onBackground,
                       fontWeight = FontWeight.Bold
                   )
               },
               showMenu = state.selectionMode,
               menuItems = listOf(
                   MenuItems(
                       text = "Delete",
                       onClick = {
                           onEvent(ViewBillsEvents.ChangeDeleteAlertBoxState(state = true))
                       }
                   ),
                   MenuItems(
                       text = if(state.selectedItems.size == state.list.size) "Deselect All" else "Select All",
                       onClick = {
                           if (state.selectedItems.size == state.list.size){
                               onEvent(ViewBillsEvents.DeselectAllItems)
                           }else{
                               onEvent(ViewBillsEvents.SelectAllItems)
                           }
                       }
                   )
               ),
               showBackButton = state.selectionMode,
               onBackClick = {
                   onEvent(ViewBillsEvents.ExitSelectionMode)
               }
           )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) {paddingValue ->

        FilterBottomSheetModal(
            showSheet = state.showFilter,
            selectedSection = state.selectedSection,
            filterWrapper = state.filter,
            showApplyButton = state.showApplyButton,
            onFilterChange = {
                onEvent(
                    ViewBillsEvents.OnFilterChange(filter = it)
                )
            },
            onSelectedSectionChange = {
                onEvent(
                    ViewBillsEvents.OnSelectedSectionChange(section = it)
                )
            },
            onAllClearButtonClick = {
                onEvent(ViewBillsEvents.OnAllClearButtonClick)
            },
            onCancelButtonCLick = {
                onEvent(ViewBillsEvents.OnCancelButtonClick)
            },
            onApplyButtonClick = {
                onEvent(ViewBillsEvents.OnApplyButtonClick)
                onEvent(ViewBillsEvents.OnClickShowFilter(visibility = false))

            },
            onApplyButtonVisibilityChange = {
                onEvent(ViewBillsEvents.OnApplyButtonVisibilityChange(visibility = it))
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {

            ItemDisplayList(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                onClickItem = {bill ->
                    if(state.selectionMode){
                        onEvent(ViewBillsEvents.ToggleItemSelection(itemId = bill.billId!!))
                    }
                },
                onLongClickItem = {bill ->
                    if(!state.selectionMode){
                        onEvent(ViewBillsEvents.EnterSelectionMode)
                        onEvent(ViewBillsEvents.ToggleItemSelection(itemId = bill.billId!!))
                    }
                }
            )

            ButtonSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 8.dp),
                outlineButtonClicked = {
                    onEvent(ViewBillsEvents.OnClickShowFilter(visibility = true))
                },
                saveButtonClicked = {
                    onEvent(ViewBillsEvents.SaveToExcel)
                },
                saveButtonEnable = state.list.isNotEmpty() && !state.selectionMode,
                outlineButtonEnable = !state.selectionMode,
                isLoading = state.isLoading
            )

            if (state.showDeleteAlertBox) {
                CustomDeleteAlertBox(
                    title = if(state.selectedItems.size == 1) "Delete this bill" else "Delete ${state.selectedItems.size} bill",
                    displayText = if(state.selectedItems.size == 1) "Are you sure you want to delete this bill?" else "Are you sure you want to delete this bills?",
                    onDismissRequest = {
                        onEvent(ViewBillsEvents.ExitSelectionMode)
                        onEvent(ViewBillsEvents.ChangeDeleteAlertBoxState(state = false))
                    },
                    onConfirm = {
                        onEvent(ViewBillsEvents.DeleteSelectedItems)
                        onEvent(ViewBillsEvents.ChangeDeleteAlertBoxState(state = false))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TrackBillzTheme {
        ViewBillsScreen(
            state = ViewBillsStates(
                list = listOf(
                    Bill(
                        traderName = "Brahmand Retail Mart Brahmand Retail Mart",
                        billNumber = "1234567",
                        billGrandTotal = 12345.98,
                        billId = 1
                    ),
                    Bill(
                        traderName = "Brahmand Retail Mart Brahmand Retail Mart",
                        billNumber = "1234567",
                        billGrandTotal = 1345.98,
                        billId = 2
                    ),
                    Bill(
                        traderName = "Brahmand Retail Mart Brahmand Retail Mart",
                        billNumber = "1234567",
                        billGrandTotal = 145.98,
                        billId = 3
                    ),
                    Bill(
                        traderName = "Brahmand Retail Mart Brahmand Retail Mart",
                        billNumber = "1234567",
                        billGrandTotal = 45.98,
                        billId = 4
                    ),
                    Bill(
                        traderName = "Brahmand Retail Mart Brahmand Retail Mart",
                        billNumber = "1234567",
                        billGrandTotal = 45.98,
                        billId = 5
                    ),
                    Bill(
                        traderName = "Brahmand Retail Mart Brahmand Retail Mart",
                        billNumber = "1234567",
                        billGrandTotal = 45.98,
                        billId = 6
                    )
                ),
                selectionMode = false,
                selectedItems = setOf(1,2,6),
                isLoading = true
            ),
            onEvent = {},
            navController = rememberNavController(),
            saveFileEvent = emptyFlow(),
        )
    }
}