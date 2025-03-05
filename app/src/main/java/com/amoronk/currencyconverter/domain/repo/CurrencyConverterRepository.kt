package com.amoronk.currencyconverter.domain.repo

import com.amoronk.currencyconverter.data.remote.model.CurrencyRateResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyTimeSeriesResponse
import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.domain.model.CurrencyConversion
import com.amoronk.currencyconverter.domain.model.ExchangeRate
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.Date

interface CurrencyConverterRepository {
    fun getAllCurrencies(): Flow<Resource<List<Currency>>>
    suspend fun getCurrencyByCode(code: String): Resource<Currency?>
    suspend fun refreshCurrencies(): Resource<Unit>
    fun getAllExchangeRates(): Flow<Resource<List<ExchangeRate>>>
    suspend fun getExchangeRatesForBaseCurrency(baseCurrency: String): Resource<List<ExchangeRate>>
    suspend fun getExchangeRate(
        baseCurrency: String,
        targetCurrency: String
    ): Resource<ExchangeRate?>

    suspend fun refreshExchangeRates(baseCurrency: String): Resource<Unit>
    suspend fun convertCurrency(
        sourceCurrency: String,
        targetCurrency: String,
        amount: BigDecimal
    ): Resource<CurrencyConversion>

    suspend fun getHistoricalRate(
        date: String,
        baseCurrency: String,
        symbols: String
    ): Resource<CurrencyRateResponse>

    suspend fun getTimeSeriesData(
        sourceCurrency: String,
        targetCurrency: String,
        startDate: Date,
        endDate: Date
    ): Resource<CurrencyTimeSeriesResponse>
}
