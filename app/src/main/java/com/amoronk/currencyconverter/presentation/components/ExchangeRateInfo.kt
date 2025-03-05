package com.amoronk.currencyconverter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amoronk.currencyconverter.presentation.theme.PrimaryBlue

@Composable
fun ExchangeRateInfo(
    timestamp: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mid-market exchange rate at $timestamp UTC",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryBlue,
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.width(4.dp))

        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "i",
                color = PrimaryBlue,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExchangeRateInfoPreview() {
    ExchangeRateInfo(timestamp = "13:38")
}
