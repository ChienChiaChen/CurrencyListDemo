package com.example.currencylistdemo.di

import com.example.currencylistdemo.ui.crypto.CryptoListViewViewModel
import com.example.currencylistdemo.ui.fiat.FiatListViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CryptoListViewViewModel(cryptoRepository = get(), savedStateHandle = get())
    }

    viewModel {
        FiatListViewViewModel(fiatRepository = get(), savedStateHandle = get())
    }
}