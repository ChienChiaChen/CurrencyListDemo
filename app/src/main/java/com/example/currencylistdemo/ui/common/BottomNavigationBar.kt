package com.example.currencylistdemo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencylistdemo.ui.common.navigation.Item
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomNavigationBar(
    pagerState: PagerState,
    items: List<Item>
) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .background(Color.Black)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            AddItem(
                item = item,
                pagerState = pagerState
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddItem(
    item: Item,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val selected = pagerState.currentPage == item.page
    val background = if (selected) Color.Magenta else Color.Transparent

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(item.page)
                    }
                }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = Color.White
            )
            Text(
                text = item.title,
                color = Color.White
            )
        }
    }
}