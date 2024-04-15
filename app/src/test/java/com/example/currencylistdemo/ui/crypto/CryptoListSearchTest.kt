package com.example.currencylistdemo.ui.crypto

import com.example.currencylistdemo.data.entity.Crypto
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class CryptoListSearchTest {
    private lateinit var cryptoListSearch: CryptoListSearch

    @Before
    fun setup() {
        cryptoListSearch = CryptoListSearch()
    }

    @Test
    fun `Coins name starts with the search term and find out successfully`() {
        var results = cryptoListSearch.execute(listOf(Crypto(name = "Foobar")), "foo")
        assertEquals(results.size, 1)

        results = cryptoListSearch.execute(
            listOf(
                Crypto(name = "Ethereum"),
                Crypto(name = "Ethereum Classic")
            ), "Ethereum"
        )
        assertEquals(results.size, 2)
    }

    @Test
    fun `Coins name starts with the search term and find out nothing`() {
        var results = cryptoListSearch.execute(listOf(Crypto(name = "Foobar")), "Barfoo")
        assertEquals(results.size, 0)

        results = cryptoListSearch.execute(
            listOf(
                Crypto(name = "Ethereum"),
                Crypto(name = "Ethereum Classic")
            ), "-"
        )
        assertEquals(results.size, 0)
    }

    @Test
    fun `Coins symbol starts with the search term and find out successfully`() {
        val results = cryptoListSearch.execute(
            listOf(
                Crypto(symbol = "ETH"),
                Crypto(symbol = "ETC"),
                Crypto(symbol = "ETN"),
            ), "ET"
        )
        assertEquals(results.size, 3)
    }

    @Test
    fun `Coins symbol starts with the search term and find out nothing`() {
        val results = cryptoListSearch.execute(
            listOf(
                Crypto(symbol = "ETH"),
                Crypto(symbol = "ETC"),
                Crypto(symbol = "ETN"),
            ), "BET"
        )
        assertEquals(results.size, 0)
    }


    @Test
    fun `Coins name contains with specific keyword and find out successfully`() {
        val results = cryptoListSearch.execute(
            listOf(
                Crypto(name = "Ethereum Classic"),
                Crypto(name = "Tronclassic")
            ), "Classic"
        )
        assertEquals(results.size, 1)
    }


}