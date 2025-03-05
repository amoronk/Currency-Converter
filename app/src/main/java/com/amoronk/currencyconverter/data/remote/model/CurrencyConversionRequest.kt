package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CurrencyConversionRequest(
    @SerializedName("from")
    val sourceCurrency: String,
    @SerializedName("to")
    val targetCurrency: String,
    @SerializedName("amount")
    val amountToConvert: BigDecimal
)
