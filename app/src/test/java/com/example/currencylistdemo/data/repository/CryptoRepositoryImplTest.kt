package com.example.currencylistdemo.data.repository

import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.local.CryptoDao
import com.example.currencylistdemo.domain.repository.CryptoRepository
import com.example.currencylistdemo.mockCryptoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.unmockkObject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test


class CryptoRepositoryImplTest {

    private val cryptoDao = mockk<CryptoDao>(relaxed = true)
    private lateinit var cryptoRepository: CryptoRepository

    @Before
    fun setup() {
        unmockkAll()
        unmockkObject()

        cryptoRepository = CryptoRepositoryImpl(cryptoDao)
    }

    @Test
    fun `fetch crypto data`() = runBlocking {
        coEvery { cryptoDao.getAllCurrencies() } returns mockCryptoList
        val repoList = cryptoRepository.getCryptos()
        assert(mockCryptoList.size == repoList.size)

    }

    @Test
    fun `insert crypto data`() = runBlocking {
        val crypto = Crypto("BTH", "BitCoin cash", "BCH")
        cryptoRepository.insertCrypto(crypto)
        coEvery { cryptoDao.insert(any()) } returns 1
        coVerify(exactly = 1) {
            cryptoDao.insert(crypto)
        }
    }


    @Test
    fun `delete all crypto data`() = runBlocking {
        cryptoRepository.deleteAllCryptos()
        coEvery { cryptoDao.clearAll() } returns 1
        coVerify(exactly = 1) {
            cryptoDao.clearAll()
        }
    }


    @After
    fun shutdown() {
        unmockkAll()
        unmockkObject()
    }

}