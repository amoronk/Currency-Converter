package com.amoronk.currencyconverter.domain.model

import java.util.Date

data class ExchangeRate(
    val sourceCurrency: String,
    val targetCurrency: String,
    val rate: Double,
    val date: Date? = null
)
