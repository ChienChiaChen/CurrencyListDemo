package com.example.currencylistdemo.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencylistdemo.ui.common.navigation.NavItem
import com.example.currencylistdemo.ui.fiat.FiatListScreen
import com.example.currencylistdemo.ui.crypto.CryptoListScreen

@Composable
fun NavigationScreens(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Crypto.path) {
        composable(NavItem.Crypto.path) { CryptoListScreen() }
        composable(NavItem.Fiat.path) { FiatListScreen() }
    }
}