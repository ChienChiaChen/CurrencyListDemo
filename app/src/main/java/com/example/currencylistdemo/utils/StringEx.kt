package com.example.currencylistdemo.utils

fun String.findWord(word: String) = "\\b$word\\b".toRegex().containsMatchIn(this)
