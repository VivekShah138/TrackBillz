package com.example.trackbillz.add_bills.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomWithErrorTextField(
    modifier: Modifier = Modifier,
    text : String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onValueChange : (String) -> Unit,
    textStyle: TextStyle,
    singleLine : Boolean,
    inputType : KeyboardOptions,
    isError : Boolean,
    errorMessage: String,
){
    Box(modifier = modifier){
        Column{

            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
//                textStyle = textStyle.copy(color = textColor),
                singleLine = singleLine,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = inputType,
                trailingIcon = {
                    if(isError){
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error Icon",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            if(isError){
                Text(
                    text = errorMessage,
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun CustomWithErrorTextField2(
    text : String,
    onValueChange : (String) -> Unit,
    textStyle: TextStyle,
    singleLine : Boolean,
    inputType : KeyboardOptions,
    isError : Boolean,
    errorMessage: String,
    label : String
){
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(5.dp)),
            tonalElevation = 2.dp,
            shadowElevation = 4.dp
        ) {
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                singleLine = singleLine,
                keyboardOptions = inputType,
                textStyle = textStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(5.dp))
                    .padding(horizontal = 12.dp, vertical = 20.dp)
                    .onFocusChanged { isFocused = it.isFocused },
                decorationBox = { innerTextField ->
                    Box {
                        if (!isFocused && text.isEmpty()) {
                            Text(
                                text = label,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}


@Preview(
    showBackground = true,
//    showSystemUi = true
)
@Composable
fun CustomWithErrorTextFieldPreview2(

){
    CustomWithErrorTextField2(
        text = "Vivek Shah",
        onValueChange = {},
        singleLine = true,
        inputType = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        isError = true,
        errorMessage = "Errro wrror wroor woor",
        label = "Enter the Trader Name",
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
    )
}