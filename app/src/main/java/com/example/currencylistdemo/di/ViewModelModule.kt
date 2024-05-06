package com.example.currencylistdemo.di

import com.example.currencylistdemo.MainViewModel
import com.example.currencylistdemo.ui.crypto.CryptoListSearch
import com.example.currencylistdemo.ui.fiat.FiatListSearch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { CryptoListSearch() }
    single { FiatListSearch() }

    viewModel {
        MainViewModel(
            searchCrypto = get(),
            searchFiat = get(),
            cryptoRepository = get(),
            fiatRepository = get(),
            savedStateHandle = get()
        )
    }
}