package com.example.currencylistdemo.ui.fiat

import com.example.currencylistdemo.data.entity.Fiat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class FiatListSearchTest {
    private lateinit var fiatListSearch: FiatListSearch

    @Before
    fun setup() {
        fiatListSearch = FiatListSearch()
    }

    @Test
    fun `Coins name starts with the search term and find out successfully`() {
        val results = fiatListSearch.execute(listOf(Fiat(name = "Singapore Dollar")), "singapore")
        assertEquals(results.size, 1)
    }

    @Test
    fun `Coins name starts with the search term and find out nothing`() {
        val results = fiatListSearch.execute(listOf(Fiat(name = "British Pound")), "Pound British")
        assertEquals(results.size, 0)
    }

    @Test
    fun `Coins symbol starts with the search term and find out successfully`() {
        val results = fiatListSearch.execute(
            listOf(
                Fiat(symbol = "$"),
                Fiat(symbol = "£"),
                Fiat(symbol = "¥¥"),
            ), "¥"
        )
        assertEquals(results.size, 1)
    }

    @Test
    fun `Coins symbol starts with the search term and find out nothing`() {
        val results = fiatListSearch.execute(
            listOf(
                Fiat(symbol = "$$"),
                Fiat(symbol = "££"),
                Fiat(symbol = "¥¥"),
            ), "¥$"
        )
        assertEquals(results.size, 0)
    }


    @Test
    fun `Coins name contains with specific keyword and find out successfully`() {
        val results = fiatListSearch.execute(
            listOf(
                Fiat(name = "ABC Classic"),
                Fiat(name = "ABCclassic")
            ), "Classic"
        )
        assertEquals(results.size, 1)
    }


}