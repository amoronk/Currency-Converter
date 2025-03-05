package com.amoronk.currencyconverter.presentation

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedTime(): String {
    return SimpleDateFormat("HH:mm", Locale.US).format(Date(this * 1000L))
}

fun BigDecimal.format(decimalPlaces: Int = 6): String {
    return this.setScale(decimalPlaces, RoundingMode.HALF_EVEN)
        .stripTrailingZeros()
        .toPlainString()
}
