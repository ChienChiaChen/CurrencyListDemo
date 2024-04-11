package com.example.currencylistdemo.di

import com.example.currencylistdemo.data.local.AppDatabase
import com.example.currencylistdemo.data.repository.CryptoRepositoryImpl
import com.example.currencylistdemo.data.repository.FiatRepositoryImpl
import com.example.currencylistdemo.domain.repository.CryptoRepository
import com.example.currencylistdemo.domain.repository.FiatRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {

    single { AppDatabase.build(androidApplication()) }

    single { get<AppDatabase>().cryptoDao() }
    single { get<AppDatabase>().fiatDao() }

    single { CryptoRepositoryImpl(get()) } bind CryptoRepository::class
    single { FiatRepositoryImpl(get()) } bind FiatRepository::class

}