package com.example.currencylistdemo.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencylistdemo.utils.DateTimeUtil
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("Crypto")
data class Crypto(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "symbol")
    val symbol: String = "",

    @ColumnInfo(name = "created")
    val created: Long = DateTimeUtil.now(),
) : Parcelable
