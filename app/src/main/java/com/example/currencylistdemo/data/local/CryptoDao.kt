package com.example.currencylistdemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencylistdemo.data.entity.Crypto

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Crypto): Long

    @Query("SELECT * FROM Crypto ORDER BY created DESC")
    suspend fun getAllCurrencies(): List<Crypto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMockCryptos(cryptos: List<Crypto>)

    @Query("DELETE FROM Crypto")
    suspend fun clearAll(): Int
}