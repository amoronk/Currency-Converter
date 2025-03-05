package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName

data class ConversionDetails(
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("rate")
    val exchangeRate: Double
)
