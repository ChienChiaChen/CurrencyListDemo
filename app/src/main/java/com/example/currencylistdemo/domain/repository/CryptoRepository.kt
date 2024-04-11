package com.example.currencylistdemo.domain.repository

import com.example.currencylistdemo.data.entity.Crypto
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    suspend fun insertCrypto(crypto: Crypto)
    suspend fun getCryptos(): Flow<List<Crypto>>
    suspend fun deleteAllCryptos()
}