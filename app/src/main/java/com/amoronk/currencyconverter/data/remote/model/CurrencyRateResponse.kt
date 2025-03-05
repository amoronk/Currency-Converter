package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrencyRateResponse(
    @SerializedName("success")
    val isSuccessful: Boolean,
    val timestamp: Long,
    @SerializedName("base")
    val baseCurrency: String,
    @SerializedName("date")
    val exchangeDate: String,
    @SerializedName("rates")
    val exchangeRates: Map<String, Double>,
    @SerializedName("historical")
    val historical: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null
)
