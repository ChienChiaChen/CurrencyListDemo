package com.example.currencylistdemo.ui.fiat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencylistdemo.ui.components.Operations
import com.example.currencylistdemo.MainViewModel


@Composable
fun FiatListScreen(viewModel: MainViewModel) {
    val state by viewModel.fiatState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadFiats()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .onKeyEvent {
                    if (it.key == Key.Back && state.isSearchActive) {
                        viewModel.onToggleSearch()
                        true
                    } else false
                }
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "All Fiats",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )

            }

            if (state.fiats.isEmpty()) {
                if (state.searchText.isNotEmpty())// In search state
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "no results",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .size(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                else { // Init state
                    Operations(
                        add = viewModel::addNewFiat,
                        addAll = viewModel::addAllFiats,
                        deleteAll = viewModel::deleteAllFiats
                    )
                }
            } else {
                if (state.searchText.isBlank()) { // hide it up when user is searching.
                    Operations(
                        add = viewModel::addNewFiat,
                        addAll = viewModel::addAllFiats,
                        deleteAll = viewModel::deleteAllFiats
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = state.fiats,
                        key = { it.id }
                    ) { fiat -> FiatItem(fiat) }
                }
            }
        }

    }
}

