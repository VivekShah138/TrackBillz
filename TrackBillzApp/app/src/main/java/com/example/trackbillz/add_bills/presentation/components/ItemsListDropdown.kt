package com.example.trackbillz.add_bills.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackbillz.traders.domain.model.Traders
import java.lang.Error

@Composable
fun ItemsListDropdown(
    itemsList: List<Traders>,
    loadList: () -> Unit,
    selectedItem: String,
    onSearchValueChange: (String) -> Unit,
    onItemSelect: (Traders) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    displayText: (Traders) -> String,
    isError: Boolean,
    errorMessage: String
){
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onExpandedChange(false)
                }
            )
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = selectedItem,
                onValueChange = {
                    onSearchValueChange(it)
                    onExpandedChange(true)
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onExpandedChange(!expanded)
                            loadList()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "arrow",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
        if(isError){
            Text(
                text = errorMessage,
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp)
            ) {
                if (itemsList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.heightIn(max = 150.dp),
                ) {
                    val filteredCategories = if (selectedItem.isNotEmpty()) {
                        itemsList.filter {
                            val displayValue = displayText(it)
                            displayValue.lowercase().contains(selectedItem.lowercase()) ||
                                    it.name.lowercase().contains("others")
                        }.sortedBy {
                            val displayValue = displayText(it)
                            displayValue
                        }
                    } else {
                        itemsList.sortedBy {
                            val displayValue = displayText(it)
                            displayValue
                        }
                    }

                    if (filteredCategories.isNotEmpty()) {
                        items(filteredCategories) { item ->
                            val displayValue = displayText(item)
                            TraderItems(
                                title = displayValue,
                                onSelect = {
                                    onItemSelect(item)
                                }
                            )
                        }
                    } else {
                        item {
                            Text(
                                text = "No results found",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemsListDropdown2(
    itemsList: List<Traders>,
    loadList: () -> Unit,
    selectedItem: String,
    label: String,
    onSearchValueChange: (String) -> Unit,
    onItemSelect: (Traders) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    displayText: (Traders) -> String,
    isError: Boolean,
    errorMessage: String
){
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onExpandedChange(false)
                }
            )
            .imePadding()
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Surface (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp)),
                tonalElevation = 2.dp,
                shadowElevation = 4.dp
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = selectedItem,
                    onValueChange = {
                        onSearchValueChange(it)
                        onExpandedChange(true)
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onExpandedChange(!expanded)
                                loadList()
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = if(!expanded) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp,
                                contentDescription = "arrow",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
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
                            text = label,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }
                )
            }
        }
        if(isError){
            Text(
                text = errorMessage,
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        AnimatedVisibility(visible = expanded) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
//                tonalElevation = 2.dp,
                shape = RoundedCornerShape(5.dp),
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.background
            ) {
                if (itemsList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.heightIn(max = 150.dp),
                ) {
                    val filteredCategories = if (selectedItem.isNotEmpty()) {
                        itemsList.filter {
                            val displayValue = displayText(it)
                            displayValue.lowercase().contains(selectedItem.lowercase()) ||
                                    it.name.lowercase().contains("others")
                        }.sortedBy {
                            val displayValue = displayText(it)
                            displayValue
                        }
                    } else {
                        itemsList.sortedBy {
                            val displayValue = displayText(it)
                            displayValue
                        }
                    }

                    if (filteredCategories.isNotEmpty()) {
                        items(filteredCategories) { item ->
                            val displayValue = displayText(item)
                            TraderItems(
                                title = displayValue,
                                onSelect = {
                                    onItemSelect(item)
                                }
                            )
                        }
                    } else {
                        item {
                            Text(
                                text = "No results found",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TraderItems(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}


