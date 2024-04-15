package com.example.currencylistdemo.ui.fiat

import com.example.currencylistdemo.data.entity.Fiat


class FiatListSearch {

    fun execute(cryptos: List<Fiat>, query: String): List<Fiat> {
        if (query.isBlank()) {
            return cryptos
        }
        return cryptos.filter { crypto ->
            crypto.symbol.startsWith(prefix = query, ignoreCase = true)
        }
    }
}