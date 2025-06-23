package com.example.trackbillz.view_bills.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackbillz.ui.theme.TrackBillzTheme

@Composable
fun BillEntryCard(
    index: Int,
    traderName: String,
    billNumber: String,
    grandTotal: Double,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
    isSelected: Boolean,
    selectionMode: Boolean
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( start = if(selectionMode) 0.dp else 16.dp, end = 16.dp)
            .combinedClickable(
                onLongClick = {
                    onLongClick()
                },
                onClick = {
                    onClick()
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        if(selectionMode){
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onClick() },
            )
        }

        Column(
            modifier = Modifier.weight(4f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = traderName,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Bill No.-$billNumber",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(0.dp).offset(y = (-8).dp)
            )
        }
//        Text(
//            buildAnnotatedString {
//                append(traderName)
//                append("\n")
//                withStyle(
//                    SpanStyle(
//                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
//                        fontSize = 14.sp
//                    )
//                ) {
//                    append("Bill No.-$billNumber")
//                }
//            },
//            style = MaterialTheme.typography.bodySmall.copy(
//                fontSize = 16.sp,
//                lineHeight = 16.sp,
//                fontWeight = FontWeight.Medium,
//                color = MaterialTheme.colorScheme.onBackground
//            ),
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//        )

        Text(
            text = "₹$grandTotal",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp
            ),
            modifier = Modifier.weight(2f),
        )

    }
}

@Preview(
    showBackground = true,
//    showSystemUi = true
)
@Composable
fun BillEntryCardPreview(){
    TrackBillzTheme {
        BillEntryCard(
            index = 1,
            billNumber = "12345",
            traderName = "Brahmand Retail Mart",
            grandTotal =  20000.96,
            onLongClick = {},
            onClick = {},
            isSelected = true,
            selectionMode = true
        )
    }
}