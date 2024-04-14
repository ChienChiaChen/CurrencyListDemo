package com.example.currencylistdemo.ui.crypto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencylistdemo.data.entity.Crypto
import com.example.currencylistdemo.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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
            savedStateHandle["cryptos"] = cryptoRepository.getCryptos().first()
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
        viewModelScope.launch {
            cryptoRepository.deleteAllCryptos()
        }
    }

    fun addCrypto() {
        viewModelScope.launch {
            // TODO: jason
        }
    }
}