package com.example.currencylistdemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencylistdemo.data.entity.Fiat

@Dao
interface FiatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Fiat): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fiats: List<Fiat>)

    @Query("SELECT * FROM Fiat ORDER BY created DESC")
    suspend fun getAllFiats(): List<Fiat>

    @Query("DELETE FROM Fiat")
    suspend fun clearAll(): Int
}