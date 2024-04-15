package com.example.currencylistdemo.ui.fiat

import com.example.currencylistdemo.data.entity.Fiat

data class FiatListState(
    val fiats: List<Fiat> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
