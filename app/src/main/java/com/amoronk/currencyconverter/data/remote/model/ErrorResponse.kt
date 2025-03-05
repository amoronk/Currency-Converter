package com.amoronk.currencyconverter.data.remote.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("info")
    val info: String
)
