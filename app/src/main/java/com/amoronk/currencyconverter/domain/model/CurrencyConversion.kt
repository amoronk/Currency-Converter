package com.amoronk.currencyconverter.domain.model

import java.math.BigDecimal

data class CurrencyConversion(
    val isSuccessful: Boolean,
    val sourceCurrency: String? = null,
    val targetCurrency: String? = null,
    val sourceAmount: BigDecimal? = null,
    val convertedAmount: BigDecimal? = null,
    val exchangeRate: Double? = null,
    val timestamp: Long? = null,
    val isHistorical: Boolean? = false,
    val error: String? = null
)
