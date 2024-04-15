package com.example.currencylistdemo.data.repository

import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.data.local.FiatDao
import com.example.currencylistdemo.domain.repository.FiatRepository
import com.example.currencylistdemo.mockFiatList
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

class FiatRepositoryImplTest {

    private val fiatDao = mockk<FiatDao>(relaxed = true)
    private lateinit var fiatRepository: FiatRepository

    @Before
    fun setup() {
        unmockkAll()
        unmockkObject()

        fiatRepository = FiatRepositoryImpl(fiatDao)
    }

    @Test
    fun `fetch fiat data`() = runBlocking {
        coEvery { fiatDao.getAllFiats() } returns mockFiatList
        val repoList = fiatRepository.getFiats()
        assert(mockFiatList.size == repoList.size)
    }

    @Test
    fun `insert fiat data`() = runBlocking {
        val crypto = Fiat(id = "SGD", name = "Singapore Dollar", symbol = "", code = "SDG")
        fiatRepository.insertFiat(crypto)
        coEvery { fiatDao.insert(any()) } returns 1
        coVerify(exactly = 1) {
            fiatDao.insert(crypto)
        }
    }


    @Test
    fun `delete all fiat data`() = runBlocking {
        fiatRepository.deleteFiats()
        coEvery { fiatDao.clearAll() } returns 1
        coVerify(exactly = 1) {
            fiatDao.clearAll()
        }
    }


    @After
    fun shutdown() {
        unmockkAll()
        unmockkObject()
    }


}