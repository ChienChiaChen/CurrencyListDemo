package com.example.currencylistdemo.domain.repository

import com.example.currencylistdemo.data.entity.Fiat

interface FiatRepository {
    suspend fun insertFiat(fiat: Fiat)
    suspend fun getFiats(): List<Fiat>
    suspend fun deleteFiats()
    suspend fun insertAllMockFiats(cryptos: List<Fiat>)
}