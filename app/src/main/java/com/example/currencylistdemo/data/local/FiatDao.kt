package com.example.currencylistdemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencylistdemo.data.entity.Fiat
import kotlinx.coroutines.flow.Flow

@Dao
interface FiatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Fiat): Long

    @Query("SELECT * FROM Fiat")
    suspend fun getAllFiats(): List<Fiat>

    @Query("DELETE FROM Fiat")
    suspend fun clearAll(): Int
}