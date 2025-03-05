package com.amoronk.currencyconverter.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amoronk.currencyconverter.domain.model.Currency

@Composable
fun CurrencySelectorLayout(
    fromCurrency: Currency?,
    toCurrency: Currency?,
    currencies: List<Currency>,
    onFromCurrencySelected: (Currency) -> Unit,
    onToCurrencySelected: (Currency) -> Unit,
    onSwap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyDropdown(
            selectedCurrency = fromCurrency,
            currencies = currencies,
            onCurrencySelected = onFromCurrencySelected,
            modifier = Modifier.weight(1f)
        )

        SwapButton(onClick = onSwap)

        CurrencyDropdown(
            selectedCurrency = toCurrency,
            currencies = currencies,
            onCurrencySelected = onToCurrencySelected,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencySelectorLayoutPreview() {
    val sampleCurrencies = listOf(
        Currency(code = "EUR", name = "Euro", symbol = "€"),
        Currency(code = "PLN", name = "Polish Złoty", symbol = "zł"),
        Currency(code = "USD", name = "US Dollar", symbol = "$")
    )

    CurrencySelectorLayout(
        fromCurrency = sampleCurrencies[0],
        toCurrency = sampleCurrencies[1],
        currencies = sampleCurrencies,
        onFromCurrencySelected = {},
        onToCurrencySelected = {},
        onSwap = {}
    )
}
