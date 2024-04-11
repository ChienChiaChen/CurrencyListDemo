package com.example.currencylistdemo.data.repository

import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.local.CryptoDao
import com.example.currencylistdemo.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CryptoRepositoryImpl(
    private val cryptoDao: CryptoDao
) : CryptoRepository {
    override suspend fun insertCrypto(crypto: Crypto) {
        cryptoDao.insert(crypto)
    }

    override suspend fun getCryptos(): Flow<List<Crypto>> = flow {
        emit(cryptoDao.getAllCurrencies())
    }

    override suspend fun deleteAllCryptos() {
        cryptoDao.clearAll()
    }
}