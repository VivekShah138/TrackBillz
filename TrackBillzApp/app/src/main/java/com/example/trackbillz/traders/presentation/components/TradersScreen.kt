package com.example.trackbillz.traders.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.trackbillz.traders.domain.model.Traders
import com.example.trackbillz.ui.theme.TrackBillzTheme
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackbillz.core.presentation.components.AlertTextField
import com.example.trackbillz.core.presentation.components.AppTopBar
import com.example.trackbillz.core.presentation.components.BottomNavigationBar
import com.example.trackbillz.core.presentation.components.CustomDeleteAlertBox
import com.example.trackbillz.core.presentation.components.CustomTextAlertBox
import com.example.trackbillz.core.presentation.utils.Screens
import com.example.trackbillz.traders.presentation.TraderEvents
import com.example.trackbillz.traders.presentation.TradersStates
import com.example.trackbillz.traders.presentation.TradersViewModel
@Composable
fun ViewTradersRoot(
    viewModel: TradersViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ViewTradersScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}

@Composable
fun ViewTradersScreen(
    state: TradersStates,
    onEvent: (TraderEvents) -> Unit,
    navController: NavController
) {


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Traders"
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        )
        {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {

                    val visibleSalesTraders = state.salesTradersList.take(5)
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sales",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add ",
                                modifier = Modifier.clickable {
                                    onEvent(TraderEvents.ChangeSelectedTrader(trader = Traders()))
                                    onEvent(TraderEvents.ChangeTraderType(type = "sales"))
                                    onEvent(TraderEvents.ChangeTraderAddAlertBoxState(state = true))
                                }
                            )

                        }
                        visibleSalesTraders.forEachIndexed { index, trader ->
                            Column {
                                SingleCategoryDisplay(
                                    onClickDelete = {
                                        onEvent(TraderEvents.ChangeSelectedTrader(trader = trader))
                                        onEvent(TraderEvents.ChangeTraderDeleteAlertBoxState(state = true))
                                    },
                                    onClickItem = {
                                        onEvent(TraderEvents.ChangeSelectedTrader(trader = trader))
                                        onEvent(TraderEvents.ChangeTraderAddAlertBoxState(true))
                                    },
                                    traderName = trader.name,
                                    traderGst = trader.gstNumber,
                                    index = index + 1
                                )
                                if (index < visibleSalesTraders.lastIndex || state.salesTradersList.size > 5) {
                                    HorizontalDivider(color = MaterialTheme.colorScheme.inverseSurface)
                                }
                                if(index >= visibleSalesTraders.lastIndex && state.salesTradersList.size > 5){
                                    Text(
                                        text ="See all",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            textAlign = TextAlign.Start
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                            .clickable {
                                                navController.navigate(Screens.DetailedTradersScreen(type = "sales"))
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item{
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    val visiblePurchaseTraders = state.purchaseTradersList.take(5)
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Purchase",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add ",
                                modifier = Modifier.clickable {
                                    onEvent(TraderEvents.ChangeSelectedTrader(trader = Traders()))
                                    onEvent(TraderEvents.ChangeTraderType(type = "purchase"))
                                    onEvent(TraderEvents.ChangeTraderAddAlertBoxState(state = true))
                                }.size(24.dp)
                            )
                        }
                        visiblePurchaseTraders.forEachIndexed { index, trader ->
                            Column {
                                SingleCategoryDisplay(
                                    onClickDelete = {
                                        onEvent(TraderEvents.ChangeSelectedTrader(trader = trader))
                                        onEvent(TraderEvents.ChangeTraderDeleteAlertBoxState(state = true))
                                    },
                                    onClickItem = {
                                        onEvent(TraderEvents.ChangeSelectedTrader(trader = trader))
                                        onEvent(TraderEvents.ChangeTraderAddAlertBoxState(true))
                                    },
                                    traderName = trader.name,
                                    traderGst = trader.gstNumber,
                                    index = index + 1
                                )
                                if (index < visiblePurchaseTraders.lastIndex || state.purchaseTradersList.size > 5) {
                                    HorizontalDivider(color = MaterialTheme.colorScheme.inverseSurface)
                                }
                                if(index >= visiblePurchaseTraders.lastIndex && state.purchaseTradersList.size > 5){
                                    Text(
                                        text ="See all",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            textAlign = TextAlign.Start
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                            .clickable {

                                            }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (state.traderAddAlertBoxState) {
            CustomTextAlertBox(
                title = "Trader Details",
                fields = listOf(
                    AlertTextField(
                        label = "Trader Name",
                        value = state.traderName,
                        onValueChange = {
                            onEvent(TraderEvents.ChangeTraderName(name = it))
                        }
                    ),
                    AlertTextField(
                        label = "Trader Gst Number",
                        value = state.traderGST,
                        onValueChange = {
                            onEvent(TraderEvents.ChangeTraderGST(gst = it))
                        }
                    )
                ),
                onDismissRequest = {
                    onEvent(TraderEvents.ChangeTraderAddAlertBoxState(state = false))
                },
                onConfirm = {
                    onEvent(TraderEvents.AddTrader)
                    onEvent(TraderEvents.ChangeTraderAddAlertBoxState(state = false))
                }
            )
        }

        if (state.traderDeleteAlertBoxState) {
            CustomDeleteAlertBox(
                title = "Delete Trader",
                displayText = "Are you sure you want to delete this trader?",
                onDismissRequest = {
                    onEvent(TraderEvents.ChangeTraderDeleteAlertBoxState(state = false))
                },
                onConfirm = {
                    onEvent(TraderEvents.DeleteTrader)
                    onEvent(TraderEvents.ChangeTraderDeleteAlertBoxState(state = false))
                }
            )
        }


    }
}

@Preview
@Composable
private fun Preview() {
    TrackBillzTheme {
        ViewTradersScreen(
            state = TradersStates(
                salesTradersList = listOf(
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    )
                ),
                purchaseTradersList =  listOf(
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    ),
                    Traders(
                        name = "VivekVivekVivekVivek",
                        gstNumber = "123445667898765432q123456",
                        type = "sales"
                    )
                ),
            ),
            onEvent = {},
            navController = rememberNavController()
        )
    }
}