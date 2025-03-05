package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName

data class CurrencySymbolsResponse(
    val success: Boolean,
    val symbols: Map<String, String>,
    @SerializedName("error")
    val error: ErrorResponse? = null
)
