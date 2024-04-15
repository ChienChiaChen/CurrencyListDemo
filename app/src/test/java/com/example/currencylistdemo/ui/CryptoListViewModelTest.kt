package com.example.currencylistdemo.ui

import androidx.lifecycle.SavedStateHandle
import com.example.currencylistdemo.MainDispatcherRule
import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.domain.repository.CryptoRepository
import com.example.currencylistdemo.ui.crypto.CryptoListViewViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.unmockkObject
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CryptoListViewModelTest {

    private lateinit var viewModel: CryptoListViewViewModel
    private val cryptoRepository: CryptoRepository = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @get:Rule
    var coroutineRule = MainDispatcherRule()

    @Before
    fun setup() {
        unmockkAll()
        unmockkObject()
        viewModel = CryptoListViewViewModel(
            cryptoRepository = cryptoRepository,
            savedStateHandle = savedStateHandle
        )
    }

    @After
    fun shutdown() {
        unmockkAll()
        unmockkObject()
    }

    @Test
    fun `search text`() {
        viewModel.onSearchTextChange("ber")
        verify {
            savedStateHandle["searchText"] = "ber"
        }
    }

    @Test
    fun `load cryptos`() {
        viewModel.loadCryptos()
        coVerify {
            cryptoRepository.getCryptos()
        }
    }

    @Test
    fun `off toggle`() {
        coEvery {
            savedStateHandle.getStateFlow("isSearchActive", false).value
        } returns false

        viewModel.onToggleSearch()
        coVerify {
            savedStateHandle["searchText"] = ""
        }
    }

    @Test
    fun `on toggle`() {
        coEvery {
            savedStateHandle.getStateFlow("isSearchActive", false).value
        } returns true

        viewModel.onToggleSearch()
        coVerify(exactly = 0) {
            savedStateHandle["searchText"] = ""
        }
    }

    @Test
    fun `delete all cryptos`() {
        viewModel.deleteAllCryptos()

        coVerify {
            cryptoRepository.deleteAllCryptos()
            cryptoRepository.getCryptos()
        }
    }

    @Test
    fun `add new crypto`() {
        viewModel.addNewCrypto()

        coVerify {
            cryptoRepository.insertCrypto(any())
            cryptoRepository.getCryptos()
        }
    }

    @Test
    fun `add all mock cryptos`() {
        viewModel.addAllCryptos()

        coVerify {
            cryptoRepository.insertAllMockCryptos(listOf(
                Crypto(id = "BTC", name = "BitCoin", symbol = "BTC"),
                Crypto(id = "ETH", name = "Ethereum", symbol = "ETH"),
                Crypto(id = "XRP", name = "XRP", symbol = "XRP"),
            ))
            cryptoRepository.getCryptos()
        }
    }
}