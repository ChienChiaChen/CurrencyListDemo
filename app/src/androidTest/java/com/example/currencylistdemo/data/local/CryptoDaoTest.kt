package com.example.currencylistdemo.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.currencylistdemo.data.entity.Crypto
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CryptoDaoTest {
    private lateinit var cryptoDao: CryptoDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        cryptoDao = appDatabase.cryptoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllCryptos() = runBlocking {
        val crypto1 = Crypto(id = "BTC", name = "BitCoin", symbol = "BTC")
        val crypto2 = Crypto(id = "ETH", name = "Ethereum", symbol = "ETH")
        cryptoDao.insert(crypto1)
        cryptoDao.insert(crypto2)
        val cryptosFromDb = cryptoDao.getAllCurrencies()
        assertEquals(cryptosFromDb[0], crypto1)
        assertEquals(cryptosFromDb[1], crypto2)
    }

    @Test
    @Throws(Exception::class)
    fun insertAllCryptos() = runBlocking {
        val cryptos = listOf(
            Crypto(id = "BTC", name = "BitCoin", symbol = "BTC"),
            Crypto(id = "ETH", name = "Ethereum", symbol = "ETH"),
            Crypto(id = "XRP", name = "XRP", symbol = "XRP"),
        )
        cryptoDao.insertAll(cryptos)
        val cryptosFromDb = cryptoDao.getAllCurrencies()
        assertEquals(cryptosFromDb.size, cryptos.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        cryptoDao.insert(Crypto(id = "BTC", name = "BitCoin", symbol = "BTC"))
        cryptoDao.insert(Crypto(id = "ETH", name = "Ethereum", symbol = "ETH"))
        cryptoDao.clearAll()
        val allCrypto = cryptoDao.getAllCurrencies()
        Assert.assertTrue(allCrypto.isEmpty())
    }
}