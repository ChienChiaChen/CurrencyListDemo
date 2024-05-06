package com.example.currencylistdemo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.data.entity.Fiat
import com.example.currencylistdemo.domain.repository.CryptoRepository
import com.example.currencylistdemo.domain.repository.FiatRepository
import com.example.currencylistdemo.ui.crypto.CryptoListSearch
import com.example.currencylistdemo.ui.crypto.CryptoListState
import com.example.currencylistdemo.ui.fiat.FiatListSearch
import com.example.currencylistdemo.ui.fiat.FiatListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val searchCrypto: CryptoListSearch,
    private val searchFiat: FiatListSearch,
    private val cryptoRepository: CryptoRepository,
    private val fiatRepository: FiatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val cryptos = savedStateHandle.getStateFlow("cryptos", emptyList<Crypto>())
    private val fiats = savedStateHandle.getStateFlow("fiats", emptyList<Fiat>())
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val cryptoState =
        combine(cryptos, searchText, isSearchActive) { cryptos, searchText, isSearchActive ->
            CryptoListState(
                cryptos = searchCrypto.execute(cryptos, searchText),
                searchText = searchText,
                isSearchActive = isSearchActive
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CryptoListState())

    val fiatState =
        combine(fiats, searchText, isSearchActive) { fiats, searchText, isSearchActive ->
            FiatListState(
                fiats = searchFiat.execute(fiats, searchText),
                searchText = searchText,
                isSearchActive = isSearchActive
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FiatListState())


    fun loadCryptos() {
        viewModelScope.launch {
            savedStateHandle["cryptos"] = cryptoRepository.getCryptos()
        }
    }

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

    fun deleteAllCryptos() {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoRepository.deleteAllCryptos()
            loadCryptos()
        }
    }

    fun deleteAllFiats() {
        viewModelScope.launch(Dispatchers.IO) {
            fiatRepository.deleteFiats()
            loadFiats()
        }
    }

    fun addNewCrypto() {
        viewModelScope.launch(Dispatchers.IO) {
            val newCoin = generateRandomString()
            cryptoRepository.insertCrypto(Crypto(id = newCoin, name = newCoin, symbol = newCoin))
            loadCryptos()
        }
    }

    fun addNewFiat() {
        viewModelScope.launch(Dispatchers.IO) {
            val newCoin = generateRandomString()
            fiatRepository.insertFiat(Fiat(id = newCoin, name = newCoin, symbol = "$"))
            loadFiats()
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