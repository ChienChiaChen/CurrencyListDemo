package com.example.currencylistdemo

import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.entity.Fiat

val mockCryptoList = listOf(
    Crypto(id = "BTC", name = "BitCoin", symbol = "BTC"),
    Crypto(id = "ETH", name = "Ethereum", symbol = "ETH"),
    Crypto(id = "XRP", name = "XRP", symbol = "XRP"),
)

val mockFiatList = listOf(
    Fiat(id = "SGD", name="Singapore Dollar", symbol = "", code = "SDG"),
    Fiat(id = "JPY", name="Japanese Yen", symbol = "", code = "JPY"),
    Fiat(id = "HKD", name="HongKongDollar", symbol = "", code = "HKD"),
)