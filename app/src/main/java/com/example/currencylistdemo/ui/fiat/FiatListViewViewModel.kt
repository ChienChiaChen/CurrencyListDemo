package com.example.currencylistdemo.ui.fiat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.domain.repository.FiatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FiatListViewViewModel(
    private val fiatRepository: FiatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchFiat = FiatListSearch()

    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val fiats = savedStateHandle.getStateFlow("fiats", emptyList<Fiat>())
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state =
        combine(fiats, searchText, isSearchActive) { fiats, searchText, isSearchActive ->
            FiatListState(
                fiats = searchFiat.execute(fiats, searchText),
                searchText = searchText,
                isSearchActive = isSearchActive
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FiatListState())


    fun loadFiats() {
        viewModelScope.launch {
            savedStateHandle["fiats"] = fiatRepository.getFiats()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteAllFiats() {
        viewModelScope.launch(Dispatchers.IO) {
            fiatRepository.deleteFiats()
            loadFiats()
        }
    }

    fun addNewFiat() {
        viewModelScope.launch(Dispatchers.IO) {
            val newCoin = generateRandomString()
            fiatRepository.insertFiat(Fiat(id = newCoin, name = newCoin, symbol = "$"))
            loadFiats()
        }
    }

    fun addAllFiats() {
        viewModelScope.launch(Dispatchers.IO) {
            fiatRepository.insertAllMockFiats(
                listOf(
                    Fiat(id = "SGD", name = "Singapore Dollar", code = "SGD", symbol = "$"),
                    Fiat(id = "EUR", name = "Euro", code = "EUR", symbol = "€"),
                    Fiat(id = "GBP", name = "British Pound", code = "GBP", symbol = "£"),
                    Fiat(id = "HKD", name = "Hong Kong Dollar", code = "HKD", symbol = "$"),
                    Fiat(id = "JPY", name = "Japanese Yen", code = "JPY", symbol = "¥"),
                )
            )
            loadFiats()
        }
    }

    private fun generateRandomString(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..5)
            .map { allowedChars.random() }
            .joinToString("")
    }
}