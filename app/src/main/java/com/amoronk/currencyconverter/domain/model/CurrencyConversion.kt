package com.amoronk.currencyconverter.domain.model

import java.math.BigDecimal

data class CurrencyConversion(
    val isSuccessful: Boolean,
    val sourceCurrency: String,
    val targetCurrency: String,
    val sourceAmount: BigDecimal,
    val convertedAmount: BigDecimal,
    val exchangeRate: Double,
    val timestamp: Long,
    val isHistorical: Boolean,
    val error: String? = null
)
