package com.amoronk.currencyconverter.data.local.model

import androidx.room.Entity

@Entity(
    tableName = "exchange_rates",
    primaryKeys = ["sourceCurrency", "targetCurrency"]
)
data class ExchangeRateEntity(
    val sourceCurrency: String,
    val targetCurrency: String,
    val rate: Double,
    val lastUpdated: Long
)
