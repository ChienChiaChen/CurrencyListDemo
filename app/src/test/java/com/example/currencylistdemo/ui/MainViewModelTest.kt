package com.example.currencylistdemo.ui

import androidx.lifecycle.SavedStateHandle
import com.example.currencylistdemo.MainDispatcherRule
import com.example.currencylistdemo.MainViewModel
import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.domain.repository.CryptoRepository
import com.example.currencylistdemo.domain.repository.FiatRepository
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

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val cryptoRepository: CryptoRepository = mockk(relaxed = true)
    private val fiatRepository: FiatRepository = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @get:Rule
    var coroutineRule = MainDispatcherRule()

    @Before
    fun setup() {
        unmockkAll()
        unmockkObject()
        viewModel = MainViewModel(
            cryptoRepository = cryptoRepository,
            fiatRepository = fiatRepository,
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
    fun `load fiats`() {
        viewModel.loadFiats()
        coVerify {
            fiatRepository.getFiats()
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
    fun `delete all fiats`() {
        viewModel.deleteAllFiats()

        coVerify {
            fiatRepository.deleteFiats()
            fiatRepository.getFiats()
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
    fun `add new fiat`() {
        viewModel.addNewFiat()

        coVerify {
            fiatRepository.insertFiat(any())
            fiatRepository.getFiats()
        }
    }

    @Test
    fun `add all mock cryptos`() {
        viewModel.addAllCryptos()

        coVerify {
            cryptoRepository.insertAllMockCryptos(
                listOf(
                    Crypto(id = "BTC", name = "BitCoin", symbol = "BTC"),
                    Crypto(id = "ETH", name = "Ethereum", symbol = "ETH"),
                    Crypto(id = "XRP", name = "XRP", symbol = "XRP"),
                )
            )
            cryptoRepository.getCryptos()
        }
    }

    @Test
    fun `add all mock fiats`() {
        viewModel.addAllFiats()

        coVerify {
            fiatRepository.insertAllMockFiats(
                listOf(
                    Fiat(id = "SGD", name = "Singapore Dollar", code = "SGD", symbol = "$"),
                    Fiat(id = "EUR", name = "Euro", code = "EUR", symbol = "€"),
                    Fiat(id = "GBP", name = "British Pound", code = "GBP", symbol = "£"),
                    Fiat(id = "HKD", name = "Hong Kong Dollar", code = "HKD", symbol = "$"),
                    Fiat(id = "JPY", name = "Japanese Yen", code = "JPY", symbol = "¥"),
                )
            )
            fiatRepository.getFiats()
        }
    }
}