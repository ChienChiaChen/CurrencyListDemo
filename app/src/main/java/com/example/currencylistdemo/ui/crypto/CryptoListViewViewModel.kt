package com.example.currencylistdemo.ui.crypto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.domain.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CryptoListViewViewModel(
    private val cryptoRepository: CryptoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchCrypto = CryptoListSearch()

    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val cryptos = savedStateHandle.getStateFlow("cryptos", emptyList<Crypto>())
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state =
        combine(cryptos, searchText, isSearchActive) { cryptos, searchText, isSearchActive ->
            CryptoListState(
                cryptos = searchCrypto.execute(cryptos, searchText),
                searchText = searchText,
                isSearchActive = isSearchActive
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CryptoListState())


    fun loadCryptos() {
        viewModelScope.launch {
            savedStateHandle["cryptos"] = cryptoRepository.getCryptos()
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

    fun deleteAllCryptos() {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoRepository.deleteAllCryptos()
            loadCryptos()
        }
    }

    fun addNewCrypto() {
        viewModelScope.launch(Dispatchers.IO) {
            val newCoin = generateRandomString()
            cryptoRepository.insertCrypto(Crypto(id = newCoin, name = newCoin, symbol = newCoin))
            loadCryptos()
        }
    }

    fun addAllCryptos() {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoRepository.insertAllMockCryptos(
                listOf(
                    Crypto(id = "BTC", name = "BitCoin", symbol = "BTC"),
                    Crypto(id = "ETH", name = "Ethereum", symbol = "ETH"),
                    Crypto(id = "XRP", name = "XRP", symbol = "XRP"),
                )
            )
            loadCryptos()
        }
    }

    private fun generateRandomString(): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..5)
            .map { allowedChars.random() }
            .joinToString("")
    }
}