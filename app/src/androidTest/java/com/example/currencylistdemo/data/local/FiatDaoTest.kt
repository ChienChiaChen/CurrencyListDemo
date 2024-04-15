package com.example.currencylistdemo.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.currencylistdemo.data.entity.Fiat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class FiatDaoTest {
    private lateinit var fiatDao: FiatDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        fiatDao = appDatabase.fiatDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllFiats() = runBlocking {
        val fiat1 = Fiat(id = "BTC", name = "BitCoin", symbol = "BTC")
        val fiat2 = Fiat(id = "ETH", name = "Ethereum", symbol = "ETH")
        fiatDao.insert(fiat1)
        fiatDao.insert(fiat2)
        val fiatsFromDb = fiatDao.getAllFiats()
        assertEquals(fiatsFromDb[0], fiat1)
        assertEquals(fiatsFromDb[1], fiat2)
    }

    @Test
    @Throws(Exception::class)
    fun insertAllFiats() = runBlocking {
        val fiats = listOf(
            Fiat(id = "SGD", name = "Singapore Dollar", code = "SGD", symbol = "$"),
            Fiat(id = "EUR", name = "Euro", code = "EUR", symbol = "€"),
            Fiat(id = "GBP", name = "British Pound", code = "GBP", symbol = "£"),
            Fiat(id = "HKD", name = "Hong Kong Dollar", code = "HKD", symbol = "$"),
            Fiat(id = "JPY", name = "Japanese Yen", code = "JPY", symbol = "¥"),
        )
        fiatDao.insertAll(fiats)
        val fiatsFromDb = fiatDao.getAllFiats()
        assertEquals(fiatsFromDb.size, fiats.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        fiatDao.insert(Fiat(id = "SGD", name = "Singapore Dollar", code = "SGD", symbol = "$"))
        fiatDao.insert(Fiat(id = "EUR", name = "Euro", code = "EUR", symbol = "€"))
        fiatDao.clearAll()
        val allFiat = fiatDao.getAllFiats()
        Assert.assertTrue(allFiat.isEmpty())
    }
}