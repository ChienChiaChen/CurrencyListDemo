package com.example.currencylistdemo.domain.repository

import com.example.currencylistdemo.data.entity.Crypto

interface CryptoRepository {

    suspend fun insertCrypto(crypto: Crypto)
    suspend fun getCryptos(): List<Crypto>
    suspend fun deleteAllCryptos()
    suspend fun insertAllMockCryptos(cryptos: List<Crypto>)
}