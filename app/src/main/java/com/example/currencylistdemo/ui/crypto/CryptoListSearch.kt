package com.example.currencylistdemo.ui.crypto

import com.example.currencylistdemo.data.entity.Crypto

class CryptoListSearch {

    fun execute(cryptos: List<Crypto>, query: String): List<Crypto> {
        if (query.isBlank()) {
            return cryptos
        }
        return cryptos.filter { crypto ->
            crypto.symbol.startsWith(prefix = query, ignoreCase = true)
        }
    }
}