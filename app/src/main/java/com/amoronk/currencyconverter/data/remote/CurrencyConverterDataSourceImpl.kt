package com.amoronk.currencyconverter.data.remote

import com.amoronk.currencyconverter.data.remote.api.CurrencyService
import com.amoronk.currencyconverter.data.remote.model.CurrencyConversionResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyRateResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencySymbolsResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyTimeSeriesResponse
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CurrencyConverterDataSourceImpl @Inject constructor(
    private val apiService: CurrencyService
) : CurrencyConverterDataSource {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override suspend fun getCurrencies(): CurrencySymbolsResponse {
        return apiService.getCurrencies()
    }

    override suspend fun convertCurrency(
        sourceCurrency: String,
        targetCurrency: String,
        amount: BigDecimal
    ): CurrencyConversionResponse {
        return apiService.convertCurrency(sourceCurrency, targetCurrency, amount)
    }

    override suspend fun getLatestRates(baseCurrency: String): CurrencyRateResponse {
        return apiService.getLatestRates(baseCurrency)
    }

    override suspend fun getHistoricalRate(
        date: String,
        baseCurrency: String,
        symbols: String
    ): CurrencyRateResponse {
        return apiService.getHistoricalRate(date, baseCurrency, symbols)
    }

    override suspend fun getTimeSeries(
        sourceCurrency: String,
        targetCurrency: String,
        startDate: Date,
        endDate: Date
    ): CurrencyTimeSeriesResponse {
        val formattedStartDate = dateFormat.format(startDate)
        val formattedEndDate = dateFormat.format(endDate)

        return apiService.getTimeSeries(
            startDate = formattedStartDate,
            endDate = formattedEndDate,
            base = sourceCurrency,
            symbols = targetCurrency
        )
    }
}
