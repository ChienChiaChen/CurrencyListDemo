package com.example.currencylistdemo.ui.common

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencylistdemo.MainViewModel
import com.example.currencylistdemo.ui.common.navigation.NavItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val navItems = listOf(NavItem.Crypto, NavItem.Fiat)
    val pagerState = PagerState(pageCount = navItems.size)
    val state by viewModel.cryptoState.collectAsState()

    Scaffold(
        topBar = {
            SearchBar(
                query = state.searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = viewModel::onSearchTextChange,
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Search"
                    )
                }, placeholder = {
                    Text(text = "Search...")
                }
            ) {}
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigationBar(pagerState = pagerState, items = navItems)
            }
        }
    ) { paddingValues ->

        BackHandler(state.searchText.isNotEmpty()) {
            viewModel.onSearchTextChange("")
        }

        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavigationScreens(pagerState = pagerState, viewModel)
        }
    }
}