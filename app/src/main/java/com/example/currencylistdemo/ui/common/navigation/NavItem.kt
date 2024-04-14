package com.example.currencylistdemo.ui.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward

sealed class NavItem {
    object Crypto :
        Item(
            path = NavPath.CRYPTO.toString(),
            title = NavTitle.CRYPTO,
            icon = Icons.Default.ArrowBack
        )

    object Fiat :
        Item(
            path = NavPath.FIAT.toString(),
            title = NavTitle.FIAT,
            icon = Icons.Default.ArrowForward
        )

}