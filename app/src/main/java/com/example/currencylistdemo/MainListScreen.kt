package com.example.currencylistdemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.ui.components.Operations
import com.example.currencylistdemo.ui.crypto.CryptoItem
import com.example.currencylistdemo.ui.fiat.FiatItem

@Composable
fun MainListScreen(viewModel: MainViewModel, isCryptoPage: Boolean) {
    val cryptoState by viewModel.cryptoState.collectAsState()
    val fiatState by viewModel.fiatState.collectAsState()

    LaunchedEffect(key1 = true) {
        if (isCryptoPage) viewModel.loadCryptos() else viewModel.loadFiats()
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
                    text = if (isCryptoPage) "All Crypto" else "All Fiats",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            val isEmptyList =
                if (isCryptoPage) cryptoState.cryptos.isEmpty() else fiatState.fiats.isEmpty()

            if (isEmptyList) {
                if (cryptoState.searchText.isNotEmpty())// In search state
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "no results",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .size(200.dp)
                            .align(CenterHorizontally)
                    )
                else { // Init state
                    operationViews(isCryptoPage, viewModel)
                }
            } else {
                if (cryptoState.searchText.isBlank()) { // hide it up when user is searching.
                    operationViews(isCryptoPage, viewModel)
                }
                if (isCryptoPage) {
                    CryptoItems(cryptoState.cryptos)
                } else {
                    FiatItems(fiatState.fiats)
                }

            }
        }

    }
}

@Composable
private fun operationViews(
    isCryptoPage: Boolean,
    viewModel: MainViewModel
) {
    Operations(
        add = {
            if (isCryptoPage) viewModel.addNewCrypto() else viewModel.addNewFiat()
        },
        addAll = {
            if (isCryptoPage) viewModel.addAllCryptos() else viewModel.addAllFiats()
        },
        deleteAll = {
            if (isCryptoPage) viewModel.deleteAllCryptos() else viewModel.deleteAllFiats()
        }
    )
}

@Composable
fun ColumnScope.CryptoItems(cryptos: List<Crypto>) {
    LazyColumn(
        modifier = Modifier
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = cryptos,
            key = { it.id }
        ) { crypto -> CryptoItem(crypto) }
    }
}

@Composable
fun ColumnScope.FiatItems(fiats: List<Fiat>) {
    LazyColumn(
        modifier = Modifier
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = fiats,
            key = { it.id }
        ) { fiat -> FiatItem(fiat) }
    }
}