package com.example.currencylistdemo.data.repository

import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.data.local.FiatDao
import com.example.currencylistdemo.domain.repository.FiatRepository

class FiatRepositoryImpl(
    private val fiatDao: FiatDao,
) : FiatRepository {
    override suspend fun insertFiat(fiat: Fiat) {
        fiatDao.insert(fiat)
    }

    override suspend fun insertAllMockFiats(cryptos: List<Fiat>) {
        fiatDao.insertAll(cryptos)
    }

    override suspend fun getFiats(): List<Fiat> = fiatDao.getAllFiats()


    override suspend fun deleteFiats() {
        fiatDao.clearAll()
    }
}