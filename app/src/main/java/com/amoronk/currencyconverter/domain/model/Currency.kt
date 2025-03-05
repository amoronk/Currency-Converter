package com.amoronk.currencyconverter.domain.model

data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    val flagRes: Int? = null
)
