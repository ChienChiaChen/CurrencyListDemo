package com.example.currencylistdemo.ui.crypto

import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.utils.findWord

class CryptoListSearch {

    fun execute(cryptos: List<Crypto>, query: String): List<Crypto> {
        if (query.isBlank()) {
            return cryptos
        }
        return cryptos.filter { crypto ->
            crypto.symbol.startsWith(prefix = query.trim(), ignoreCase = true) ||
                    crypto.name.startsWith(prefix = query.trim(), ignoreCase = true) ||
                    crypto.name.findWord(query)
        }
    }
}