package com.example.currencylistdemo.ui

import androidx.lifecycle.SavedStateHandle
import com.example.currencylistdemo.MainDispatcherRule
import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.domain.repository.FiatRepository
import com.example.currencylistdemo.ui.fiat.FiatListViewViewModel
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

class FiatListViewViewModelTest {

    private lateinit var viewModel: FiatListViewViewModel
    private val fiatRepository: FiatRepository = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @get:Rule
    var coroutineRule = MainDispatcherRule()

    @Before
    fun setup() {
        unmockkAll()
        unmockkObject()
        viewModel = FiatListViewViewModel(
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
    fun `delete all fiats`() {
        viewModel.deleteAllFiats()

        coVerify {
            fiatRepository.deleteFiats()
            fiatRepository.getFiats()
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