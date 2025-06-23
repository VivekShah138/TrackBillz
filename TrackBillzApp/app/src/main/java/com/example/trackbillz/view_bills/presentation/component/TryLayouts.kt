package com.example.trackbillz.view_bills.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackbillz.add_bills.domain.model.Bill
import com.example.trackbillz.view_bills.presentation.ViewBillsEvents
import com.example.trackbillz.view_bills.presentation.ViewBillsStates

@Composable
fun TryLayouts(
) {
    Scaffold(

    ) {paddingValues ->

        val focusRequester = remember { FocusRequester() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

        }
    }
}

@Composable
fun ItemDisplayList(
    modifier: Modifier = Modifier,
    state: ViewBillsStates,
    onLongClickItem: (Bill) -> Unit,
    onClickItem: (Bill) -> Unit
){
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(state.list) { index, item ->
            val isSelected = state.selectedItems.contains(item.billId)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent
                    )
                    .padding(horizontal = 8.dp).padding(top = 8.dp)
            ) {
                BillEntryCard(
                    index = index+1,
                    traderName = item.traderName,
                    billNumber = item.billNumber,
                    grandTotal = item.billGrandTotal,
                    onLongClick = {
                        onLongClickItem(item)
                    },
                    onClick = {
                        onClickItem(item)
                    },
                    isSelected = isSelected,
                    selectionMode = state.selectionMode
                )
            }
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier,
    outlineButtonClicked: () -> Unit,
    saveButtonClicked: () -> Unit,
    saveButtonEnable: Boolean,
    outlineButtonEnable: Boolean,
    isLoading: Boolean = false
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        OutlinedButton(
            onClick = outlineButtonClicked,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            enabled = outlineButtonEnable
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Filter"
            )
        }
        Button(
            onClick = saveButtonClicked,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            enabled = saveButtonEnable
        ) {
            if(isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }else{
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Save Records"
                )
            }
        }
    }
}

@Composable
fun SearchBarCustom(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit,
    onCancelIconClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    label: String
){
   Row(
       modifier = modifier
   ) {

       val focusRequester = remember { FocusRequester() }
       val focusManager = LocalFocusManager.current
       var isFocused by remember { mutableStateOf(false) }

       Surface (
           modifier = Modifier
               .fillMaxWidth()
               .clip(shape = RoundedCornerShape(5.dp)),
           tonalElevation = 2.dp,
           shadowElevation = 4.dp
       ) {
           OutlinedTextField(
               modifier = Modifier
                   .fillMaxWidth()
                   .focusRequester(focusRequester)
                   .onFocusChanged { isFocused = it.isFocused },
               value = searchQuery,
               onValueChange = {
                   onSearchQueryChange(it)
               },
               trailingIcon = {
                   if(isFocused){
                       Icon(
                           imageVector = Icons.Default.Cancel,
                           contentDescription = null,
                           modifier = Modifier.clickable {
                               onCancelIconClick()
                           }
                       )
                   }
               },
               leadingIcon = {
                   if(isFocused){
                       Icon(
                           imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                           contentDescription = null,
                           modifier = Modifier.clickable {
                               onBackIconClick()
                               focusManager.clearFocus(force = true)
                           }
                       )
                   }
                   else{
                       Icon(
                           imageVector = Icons.Default.Search,
                           contentDescription = null,
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
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun TryLayoutsPreview(){
    TryLayouts(

    )
}