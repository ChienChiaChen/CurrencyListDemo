package com.example.currencylistdemo.di

import com.example.currencylistdemo.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            cryptoRepository = get(),
            fiatRepository = get(),
            savedStateHandle = get()
        )
    }
}