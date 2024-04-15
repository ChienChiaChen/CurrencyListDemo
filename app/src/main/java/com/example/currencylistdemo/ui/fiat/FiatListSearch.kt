package com.example.currencylistdemo.ui.fiat

import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.utils.findWord


class FiatListSearch {

    fun execute(fiats: List<Fiat>, query: String): List<Fiat> {
        if (query.isBlank()) {
            return fiats
        }
        return fiats.filter { fiat ->
            fiat.symbol.startsWith(prefix = query.trim(), ignoreCase = true) ||
                    fiat.name.startsWith(prefix = query.trim(), ignoreCase = true) ||
                    fiat.name.findWord(query)
        }
    }
}