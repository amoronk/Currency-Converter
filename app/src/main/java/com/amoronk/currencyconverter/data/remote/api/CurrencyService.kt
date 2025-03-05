package com.amoronk.currencyconverter.data.remote.api

import com.amoronk.currencyconverter.data.remote.model.CurrencyConversionResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyRateResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencySymbolsResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyTimeSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

interface CurrencyService {
    @GET("symbols")
    suspend fun getCurrencies(): CurrencySymbolsResponse

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: String
    ): CurrencyRateResponse

    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: BigDecimal
    ): CurrencyConversionResponse

    @GET("/{date}")
    suspend fun getHistoricalRate(
        @Query("date") date: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): CurrencyRateResponse

    @GET("timeseries")
    suspend fun getTimeSeries(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): CurrencyTimeSeriesResponse
}
