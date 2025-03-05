package com.amoronk.currencyconverter.presentation.state

import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.presentation.components.RatePoint
import com.amoronk.currencyconverter.presentation.components.TimeFrame


data class CurrencyConverterState(
    val currencies: List<Currency> = emptyList(),
    val isCurrenciesLoading: Boolean = false,
    val sourceCurrency: Currency? = null,
    val targetCurrency: Currency? = null,
    val fromAmount: String = "",
    val toAmount: String = "",
    val isConverting: Boolean = false,
    val historicalRates: List<RatePoint> = emptyList(),
    val selectedTimeFrame: TimeFrame = TimeFrame.DAYS_30,
    val isHistoricalRatesLoading: Boolean = false,
    val errorMessage: String? = null,
    val historyErrorMessage: String? = null,
    val exchangeRateTimestamp: String = ""
)
