package com.example.currencylistdemo.ui.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward

sealed class NavItem {
    object Crypto :
        Item(
            title = NavTitle.CRYPTO,
            icon = Icons.Default.ArrowBack,
            page = 0
        )

    object Fiat :
        Item(
            title = NavTitle.FIAT,
            icon = Icons.Default.ArrowForward,
            page = 1
        )

}