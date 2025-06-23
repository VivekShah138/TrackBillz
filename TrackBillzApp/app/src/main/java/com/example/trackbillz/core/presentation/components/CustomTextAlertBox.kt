package com.example.trackbillz.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AlertTextField(
    val label: String,
    val value: String,
    val onValueChange: (String) -> Unit,
    val singleLine: Boolean = true,
    val keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    val isError: Boolean = false,
    val errorMessage: String? = null,
)


@Composable
fun CustomTextAlertBox(
    title: String,
    fields: List<AlertTextField>,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String = "Save",
    dismissButtonText: String = "Cancel",

) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = {
            Column {
                fields.forEach { field ->
                    OutlinedTextField(
                        value = field.value,
                        onValueChange = field.onValueChange,
                        label = { Text(field.label) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = field.keyboardOptions,
                        singleLine = field.singleLine,
                    )
                    if(field.isError){
                        Text(
                            text = field.errorMessage ?: "",
                            textAlign = TextAlign.End,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = fields.all { it.value.isNotBlank() }
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {

            OutlinedButton (onClick = onDismissRequest) {
                Text(dismissButtonText)
            }
        }
    )
}


@Composable
fun CustomDeleteAlertBox(
    title: String,
    displayText:String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String = "Delete",
    dismissButtonText: String = "Cancel",

    ) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = displayText)
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {

            OutlinedButton (onClick = onDismissRequest) {
                Text(dismissButtonText)
            }
        }
    )
}
