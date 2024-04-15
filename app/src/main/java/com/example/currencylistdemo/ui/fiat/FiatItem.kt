package com.example.currencylistdemo.ui.fiat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencylistdemo.data.entity.Fiat


@Composable
fun FiatItem(fiat: Fiat) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .background(color = Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = fiat.name.first().toString(), color = Color.Black)
        }
        Text(modifier = Modifier.padding(start = 10.dp), text = fiat.name, fontSize = 20.sp, fontWeight = FontWeight.Light)
        Spacer(Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(end = 10.dp),
            text = fiat.symbol,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "detail"
        )

    }
    Divider(color = Color.White)
}
