package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrencyTimeSeriesResponse(
    @SerializedName("success")
    val isSuccessful: Boolean,
    @SerializedName("timeseries")
    val isTimeSeries: Boolean,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("base")
    val baseCurrency: String,
    @SerializedName("rates")
    val exchangeRates: Map<String, Map<String, Double>>,
    @SerializedName("error")
    val error: ErrorResponse? = null
)
