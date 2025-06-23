package com.example.trackbillz.traders.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackbillz.core.presentation.components.AlertTextField
import com.example.trackbillz.core.presentation.components.AppTopBar
import com.example.trackbillz.core.presentation.components.CustomDeleteAlertBox
import com.example.trackbillz.core.presentation.components.CustomTextAlertBox
import com.example.trackbillz.traders.domain.model.Traders
import com.example.trackbillz.traders.presentation.TraderEvents
import com.example.trackbillz.traders.presentation.TradersStates
import com.example.trackbillz.view_bills.presentation.component.SearchBarCustom

@Composable
fun DetailedTradersScreen(
    traderType: String,
    state: TradersStates,
    onEvent: (TraderEvents) -> Unit,
    navController: NavController
){
    val listToDisplay = if(traderType == "sales"){
        state.salesTradersList
    }else{
        state.purchaseTradersList
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if(traderType == "sales"){
                    "Sales Traders"
                }else{
                    "Purchase Traders"
                },
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBarCustom(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                searchQuery = state.searchQuery,
                onSearchQueryChange = {
                    onEvent(
                        TraderEvents.ChangeSearchQuery(query = it)
                    )
                },
                label = "Search your trader",
                onCancelIconClick = {
                    onEvent(
                        TraderEvents.ChangeSearchQuery(query = "")
                    )
                },
                onBackIconClick = {
                    onEvent(
                        TraderEvents.ChangeSearchQuery(query = "")
                    )
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                val filteredList = if(state.searchQuery.isNotEmpty()){
                    listToDisplay.filter {list ->
                        list.name.contains(state.searchQuery, ignoreCase = true) || list.gstNumber.contains(state.searchQuery, ignoreCase = true)
                    }.sortedBy { it.name }
                }else{
                    listToDisplay
                }


                items(filteredList){
                   SingleCategoryDisplay(
                       onClickItem = {
                           onEvent(TraderEvents.ChangeSelectedTrader(trader = it))
                           onEvent(TraderEvents.ChangeTraderAddAlertBoxState(true))
                       },
                       onClickDelete = {
                           onEvent(TraderEvents.ChangeSelectedTrader(trader = it))
                           onEvent(TraderEvents.ChangeTraderDeleteAlertBoxState(state = true))
                       },
                       traderGst = it.gstNumber,
                       traderName = it.name,
                       index = 0
                   )
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
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DetailedTradersScreenPreview(){
    DetailedTradersScreen(
        traderType = "sales",
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
        navController = rememberNavController(),
        onEvent = {}
    )
}