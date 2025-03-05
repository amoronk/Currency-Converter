package com.amoronk.currencyconverter.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val symbol: String? = null,
    val flagRes: Int? = null
)
