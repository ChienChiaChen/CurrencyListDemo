package com.example.currencylistdemo.domain.repository

import com.example.currencylistdemo.data.entity.Fiat
import kotlinx.coroutines.flow.Flow

interface FiatRepository {
    suspend fun insertFiat(fiat: Fiat)
    suspend fun getFiats(): Flow<List<Fiat>>
    suspend fun deleteFiats()
}