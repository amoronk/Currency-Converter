package com.amoronk.currencyconverter.data.remote

import com.amoronk.currencyconverter.data.remote.model.CurrencyConversionResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyRateResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencySymbolsResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyTimeSeriesResponse
import java.math.BigDecimal
import java.util.Date

interface CurrencyConverterDataSource {
    suspend fun getCurrencies(): CurrencySymbolsResponse

    suspend fun convertCurrency(
        sourceCurrency: String,
        targetCurrency: String,
        amount: BigDecimal
    ): CurrencyConversionResponse

    suspend fun getLatestRates(baseCurrency: String): CurrencyRateResponse

    suspend fun getHistoricalRate(
        date: String,
        baseCurrency: String,
        symbols: String
    ): CurrencyRateResponse

    suspend fun getTimeSeries(
        sourceCurrency: String,
        targetCurrency: String,
        startDate: Date,
        endDate: Date
    ): CurrencyTimeSeriesResponse
}
