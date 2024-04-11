package com.example.currencylistdemo.data.repository

import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.data.local.FiatDao
import com.example.currencylistdemo.domain.repository.FiatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FiatRepositoryImpl(
    private val fiatDao: FiatDao,
) : FiatRepository {
    override suspend fun insertFiat(fiat: Fiat) {
        fiatDao.insert(fiat)
    }

    override suspend fun getFiats(): Flow<List<Fiat>> =
        flow {
            emit(fiatDao.getAllFiats())
        }
    override suspend fun deleteFiats() {
        fiatDao.clearAll()
    }
}