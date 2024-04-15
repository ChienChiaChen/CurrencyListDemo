package com.example.currencylistdemo.ui.crypto

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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencylistdemo.MainViewModel
import com.example.currencylistdemo.ui.components.Operations

@Composable
fun CryptoListScreen(viewModel: MainViewModel) {
    val state by viewModel.cryptoState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadCryptos()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "All Crypto",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            if (state.cryptos.isEmpty()) {
                if (state.searchText.isNotEmpty())// In search state
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "no results",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .size(200.dp)
                            .align(CenterHorizontally)
                    )
                else { // Init state
                    Operations(
                        add = viewModel::addNewCrypto,
                        addAll = viewModel::addAllCryptos,
                        deleteAll = viewModel::deleteAllCryptos
                    )
                }
            } else {
                if (state.searchText.isBlank()) { // hide it up when user is searching.
                    Operations(
                        add = viewModel::addNewCrypto,
                        addAll = viewModel::addAllCryptos,
                        deleteAll = viewModel::deleteAllCryptos
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = state.cryptos,
                        key = { it.id }
                    ) { crypto -> CryptoItem(crypto) }
                }
            }
        }

    }
}

