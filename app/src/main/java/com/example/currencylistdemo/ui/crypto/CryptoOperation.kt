package com.example.currencylistdemo.ui.crypto

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CryptoOperation(
    add: () -> Unit,
    addAll: () -> Unit,
    deleteAll: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { add.invoke() }
        ) {
            Text(text = "Insert")
        }

        Button(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { addAll.invoke() }
        ) {
            Text(text = "Add all")
        }

        Button(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { deleteAll.invoke() }
        ) {
            Text(text = "Delete all")
        }
    }
}