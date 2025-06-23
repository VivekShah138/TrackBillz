package com.example.trackbillz.view_bills.presentation.component

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackbillz.ui.theme.TrackBillzTheme
import com.example.trackbillz.view_bills.presentation.util.FilterLeftPanel
import com.example.trackbillz.view_bills.presentation.util.FilterWrapper
import com.example.trackbillz.view_bills.presentation.util.ItemDuration
import com.example.trackbillz.view_bills.presentation.util.ItemSortType
import com.example.trackbillz.view_bills.presentation.util.ItemType
import com.example.trackbillz.view_bills.presentation.util.SortOrder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetModal(
    showSheet: Boolean,
    selectedSection: FilterLeftPanel,
    onSelectedSectionChange: (FilterLeftPanel) -> Unit,
    filterWrapper: FilterWrapper,
    onFilterChange: (FilterWrapper)-> Unit,
    showApplyButton: Boolean,
    onApplyButtonVisibilityChange: (Boolean) -> Unit,
    onApplyButtonClick:() -> Unit,
    onAllClearButtonClick:() -> Unit,
    onCancelButtonCLick: () -> Unit
){
    if(showSheet){
        ModalBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onDismissRequest = onCancelButtonCLick
        ) {
            FilterBottomSheet(
                selectedSection = selectedSection,
                onSelectedSectionChange = onSelectedSectionChange,
                filterWrapper = filterWrapper,
                onFilterChange = onFilterChange,
                showApplyButton = showApplyButton,
                onApplyButtonVisibilityChange = onApplyButtonVisibilityChange,
                onApplyButtonClick = onApplyButtonClick,
                onAllClearButtonClick = onAllClearButtonClick,
                onCancelButtonCLick = onCancelButtonCLick
            )
        }
    }
}

@Composable
fun FilterBottomSheet(
    selectedSection: FilterLeftPanel,
    onSelectedSectionChange: (FilterLeftPanel) -> Unit,
    filterWrapper: FilterWrapper,
    onFilterChange: (FilterWrapper)-> Unit,
    showApplyButton: Boolean,
    onApplyButtonVisibilityChange: (Boolean) -> Unit,
    onApplyButtonClick:() -> Unit,
    onAllClearButtonClick:() -> Unit,
    onCancelButtonCLick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Filter By",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cancel Filter",
                modifier = Modifier.clickable {
                    onCancelButtonCLick()
                }
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ){

            //Left Panel
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ){
                listOf(
                    FilterLeftPanel.SortBy,FilterLeftPanel.Type,FilterLeftPanel.Duration
                ).forEach {section ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (selectedSection == section)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                else Color.Transparent
                            )
                            .clickable {
                                onSelectedSectionChange(section)
                            }
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                    ) {
                        Text(
                            text = section.label,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = if (selectedSection == section)
                                    MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }

            //Right Panel
            Column(
                modifier = Modifier.weight(2f)
            ) {
                when(selectedSection){
                    FilterLeftPanel.SortBy -> {
                        SortBySection(
                            filterWrapper = filterWrapper,
                            onFilterChange = onFilterChange
                        )
                    }
                    FilterLeftPanel.Type -> {
                        TypeSection(
                            filterWrapper = filterWrapper,
                            onFilterChange = onFilterChange
                        )
                    }
                    FilterLeftPanel.Duration -> {
                        DurationSection(
                            filterWrapper = filterWrapper,
                            onFilterChange = onFilterChange,
                            onApplyButtonVisibilityChange = onApplyButtonVisibilityChange
                        )
                    }
                }
            }
        }
        HorizontalDivider()

        ButtonSection(
            showApplyButton = showApplyButton,
            onApplyButtonClick = onApplyButtonClick,
            onAllClearButtonClick = onAllClearButtonClick
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun FilterBottomSheetPreview(){
    TrackBillzTheme {
        FilterBottomSheet(
            FilterLeftPanel.Duration,
            filterWrapper = FilterWrapper(),
            showApplyButton = false,
            onSelectedSectionChange = {},
            onFilterChange = {},
            onApplyButtonClick = {},
            onAllClearButtonClick = {},
            onCancelButtonCLick = {},
            onApplyButtonVisibilityChange = {}
        )
    }
}


@Composable
fun SortBySection(
    filterWrapper: FilterWrapper,
    onFilterChange: (FilterWrapper)-> Unit
){
    Column{
        Text(
            text = "Sort Type",
            fontSize = 16.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        DefaultRadioButton(
            selected = filterWrapper.itemSortType is ItemSortType.Date,
            text = "Date",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemSortType = ItemSortType.Date(sortOrder = filterWrapper.itemSortType.sortOrder)
                    )
                )
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemSortType is ItemSortType.Title,
            text = "Title",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemSortType = ItemSortType.Title(sortOrder = filterWrapper.itemSortType.sortOrder)
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sort Order",
            fontSize = 16.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        DefaultRadioButton(
            selected = filterWrapper.itemSortType.sortOrder is SortOrder.Ascending,
            text = "Ascending",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemSortType = filterWrapper.itemSortType.copy(SortOrder.Ascending)
                    )
                )
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemSortType.sortOrder is SortOrder.Descending,
            text = "Descending",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemSortType = filterWrapper.itemSortType.copy(SortOrder.Descending)
                    )
                )
            }
        )
    }
}


@Composable
fun TypeSection(
    filterWrapper: FilterWrapper,
    onFilterChange: (FilterWrapper)-> Unit
){
    Column {
        DefaultRadioButton(
            selected = filterWrapper.itemType is ItemType.Both,
            text = "Both",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemType = ItemType.Both
                    )
                )
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemType is ItemType.Purchase,
            text = "Purchase",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemType = ItemType.Purchase
                    )
                )
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemType is ItemType.Sales,
            text = "Sales",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemType = ItemType.Sales
                    )
                )
            }
        )
    }
}

@Composable
fun DurationSection(
    filterWrapper: FilterWrapper,
    onFilterChange: (FilterWrapper)-> Unit,
    onApplyButtonVisibilityChange: (Boolean) -> Unit
){
    var dateError by remember { mutableStateOf<String?>(null) }
    var fromDate by remember { mutableStateOf<Calendar?>(null) }
    var toDate by remember { mutableStateOf<Calendar?>(null) }

    Column {
        DefaultRadioButton(
            selected = filterWrapper.itemDuration is ItemDuration.Today,
            text = "Today",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemDuration = ItemDuration.Today
                    )
                )
                onApplyButtonVisibilityChange(true)
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemDuration is ItemDuration.ThisMonth,
            text = "This Month",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemDuration = ItemDuration.ThisMonth
                    )
                )
                onApplyButtonVisibilityChange(true)
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemDuration is ItemDuration.LastMonth,
            text = "Last Month",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemDuration = ItemDuration.LastMonth
                    )
                )
                onApplyButtonVisibilityChange(true)
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemDuration is ItemDuration.Last3Months,
            text = "Last 3 Months",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemDuration = ItemDuration.Last3Months
                    )
                )
                onApplyButtonVisibilityChange(true)
            }
        )
        DefaultRadioButton(
            selected = filterWrapper.itemDuration is ItemDuration.CustomRange ,
            text = "Custom Range",
            onClick = {
                onFilterChange(
                    filterWrapper.copy(
                        itemDuration = ItemDuration.CustomRange(0L,0L)
                    )
                )
            }
        )
        if(filterWrapper.itemDuration is ItemDuration.CustomRange){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ){

                DatePickerField(
                    date = fromDate,
                    onDateSelected = { fromDate = it },
                    label = "From"
                )

                DatePickerField(
                    date = toDate,
                    onDateSelected = {
                        toDate = it

                        // Validate as soon as user picks the "To" date
                        if(fromDate == null){
                            dateError = "Please select From Date"
                        }
                        else if (fromDate != null && it.timeInMillis < fromDate!!.timeInMillis) {
                            dateError = "The From Date must come before the To date"
                        }else {
                            dateError = null

                            onFilterChange(
                                filterWrapper.copy(
                                    itemDuration = ItemDuration.CustomRange(
                                        from = fromDate!!.timeInMillis,
                                        to = toDate!!.timeInMillis
                                    )
                                )
                            )
                        }
                    },
                    label = "To"
                )

                if (dateError != null) {
                    Text(
                        text = dateError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp,bottom = 8.dp, end = 8.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
            val filterButtonVisibility = fromDate != null && toDate != null
                    && fromDate!!.timeInMillis <= toDate!!.timeInMillis
            onApplyButtonVisibilityChange(filterButtonVisibility)
        }
    }
}

@Composable
fun DatePickerField(
    date: Calendar?,
    onDateSelected: (Calendar) -> Unit,
    label:String
) {
    val context = LocalContext.current
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp,bottom = 8.dp, end = 8.dp)
    ){
        OutlinedTextField(
            value =  date?.let { formatter.format(it.time) } ?: "Select Date",
            onValueChange = {}, // no manual editing
            readOnly = true,
            label = { Text(label) },
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Date"
                )
            }
        )
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                val today = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }
                        onDateSelected(selectedDate)
                    },
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    datePicker.maxDate = Calendar.getInstance().timeInMillis
                }.show()
            }
        }
    }
}

@Composable
fun ButtonSection(
    showApplyButton: Boolean,
    onApplyButtonClick: () -> Unit,
    onAllClearButtonClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onAllClearButtonClick,
            modifier = Modifier.weight(1f)
        ) {
            Text("Clear All")
        }
        Button(
            onClick = onApplyButtonClick,
            modifier = Modifier.weight(1f),
            enabled = showApplyButton
        ) {
            Text("Apply")
        }
    }
}
