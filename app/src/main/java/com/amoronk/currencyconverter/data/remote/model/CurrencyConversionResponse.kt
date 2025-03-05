package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CurrencyConversionResponse(
    @SerializedName("success")
    val isSuccessful: Boolean,
    @SerializedName("query")
    val conversionRequest: CurrencyConversionRequest? = null,
    @SerializedName("info")
    val conversionDetails: ConversionDetails? = null,
    @SerializedName("historical")
    val historical: Boolean? = null,
    @SerializedName("result")
    val convertedAmount: BigDecimal? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null
)
