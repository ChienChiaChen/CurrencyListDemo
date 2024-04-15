package com.example.currencylistdemo.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.currencylistdemo.ui.crypto.CryptoListScreen
import com.example.currencylistdemo.MainViewModel
import com.example.currencylistdemo.ui.fiat.FiatListScreen
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
            when (page) {
                0 -> CryptoListScreen(viewModel)
                1 -> FiatListScreen(viewModel)
            }
        }
    }
}