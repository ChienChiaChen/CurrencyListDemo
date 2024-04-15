package com.example.currencylistdemo.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.currencylistdemo.MainViewModel
import com.example.currencylistdemo.MainListScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationScreens(
    pagerState: PagerState,
    viewModel : MainViewModel
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState
        ) { page ->
            val isCryptoPage = page == 0
            MainListScreen(viewModel, isCryptoPage)
        }
    }
}