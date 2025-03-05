package com.amoronk.currencyconverter.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.presentation.theme.GrayLight
import com.amoronk.currencyconverter.presentation.theme.PrimaryBlue
import com.amoronk.currencyconverter.presentation.theme.PrimaryBlueDark
import com.amoronk.currencyconverter.presentation.theme.PrimaryBlueDeep
import com.amoronk.currencyconverter.presentation.theme.SecondaryGreen
import com.amoronk.currencyconverter.presentation.theme.SecondaryLightBlue
import com.amoronk.currencyconverter.presentation.theme.SecondaryMediumBlue

@Composable
fun CurrencyDropdown(
    selectedCurrency: Currency?,
    currencies: List<Currency>,
    onCurrencySelected: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = GrayLight,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(horizontal = 10.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            selectedCurrency?.flagRes?.let { flagRes ->
                androidx.compose.foundation.Image(
                    painter = painterResource(id = flagRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
            } ?: run {
                val colors = listOf(
                    PrimaryBlue,
                    PrimaryBlueDark,
                    PrimaryBlueDeep,
                    SecondaryGreen,
                    SecondaryLightBlue,
                    SecondaryMediumBlue
                )
                val randomColor = colors.random()
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(randomColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = selectedCurrency?.code?.first()?.toString() ?: "?",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = selectedCurrency?.code ?: "Select",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select currency",
                tint = Color.Gray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(240.dp)
                .background(Color.White)
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            currency.flagRes?.let { flagRes ->
                                androidx.compose.foundation.Image(
                                    painter = painterResource(id = flagRes),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                )
                            } ?: run {
                                val colors = listOf(
                                    PrimaryBlue,
                                    PrimaryBlueDark,
                                    PrimaryBlueDeep,
                                    SecondaryGreen,
                                    SecondaryLightBlue,
                                    SecondaryMediumBlue
                                )
                                val randomColor = colors.random()

                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(randomColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currency.code.first().toString(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "${currency.code} - ${currency.name}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }
                    },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyDropdownPreview() {
    val sampleCurrencies = listOf(
        Currency(code = "USD", name = "US Dollar", symbol = "$"),
        Currency(code = "EUR", name = "Euro", symbol = "€"),
        Currency(code = "GBP", name = "British Pound", symbol = "£")
    )

    CurrencyDropdown(
        selectedCurrency = sampleCurrencies[0],
        currencies = sampleCurrencies,
        onCurrencySelected = {}
    )
}
