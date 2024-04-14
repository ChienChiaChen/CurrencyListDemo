package com.example.currencylistdemo.ui.crypto

import com.example.currencylistdemo.data.entity.Crypto

data class CryptoListState(
    val cryptos: List<Crypto> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
