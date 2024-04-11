package com.example.currencylistdemo.utils

import kotlinx.datetime.*

object DateTimeUtil {

    fun now(): Long {
        return Clock.System.now().epochSeconds
    }
}