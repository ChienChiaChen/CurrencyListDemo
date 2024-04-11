package com.example.currencylistdemo

import android.app.Application
import com.example.currencylistdemo.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CurrencyListApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CurrencyListApp)
            modules(repositoryModule)
        }
    }
}