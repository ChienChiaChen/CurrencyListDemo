package com.example.currencylistdemo.data.repository

import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.local.CryptoDao
import com.example.currencylistdemo.domain.repository.CryptoRepository

class CryptoRepositoryImpl(
    private val cryptoDao: CryptoDao
) : CryptoRepository {

    override suspend fun insertAllMockCryptos(cryptos: List<Crypto>) {
        cryptoDao.insertAll(cryptos)
    }

    override suspend fun insertCrypto(crypto: Crypto) {
        cryptoDao.insert(crypto)
    }

    override suspend fun getCryptos(): List<Crypto> = cryptoDao.getAllCurrencies()

    override suspend fun deleteAllCryptos() {
        cryptoDao.clearAll()
    }
}
