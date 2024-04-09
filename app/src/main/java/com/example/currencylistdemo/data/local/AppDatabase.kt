package com.example.currencylistdemo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.entity.Fiat

@Database(
    entities = [Crypto::class, Fiat::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
    abstract fun fiatDao(): FiatDao

    companion object {
        private const val DATABASE_NAME: String = "currency_db"
        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
    }
}