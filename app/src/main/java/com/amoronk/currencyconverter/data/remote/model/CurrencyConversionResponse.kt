package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CurrencyConversionResponse(
    @SerializedName("success")
    val isSuccessful: Boolean,
    @SerializedName("query")
    val conversionRequest: CurrencyConversionRequest,
    @SerializedName("info")
    val conversionDetails: ConversionDetails,
    @SerializedName("historical")
    val historical: Boolean? = null,
    @SerializedName("result")
    val convertedAmount: BigDecimal,
    @SerializedName("error")
    val error: ErrorResponse? = null
)
