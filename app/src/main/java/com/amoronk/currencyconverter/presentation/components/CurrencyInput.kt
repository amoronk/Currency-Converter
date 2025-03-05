package com.amoronk.currencyconverter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amoronk.currencyconverter.presentation.theme.TextPrimary
import com.amoronk.currencyconverter.presentation.theme.TextSecondary

@Composable
fun CurrencyInput(
    value: String,
    onValueChange: (String) -> Unit,
    currencyCode: String,
    isReadOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                    onValueChange(it)
                }
            },
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = if (isReadOnly) TextSecondary else TextPrimary
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            readOnly = isReadOnly,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = currencyCode,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary.copy(alpha = 0.5f)
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AmountInputPreview() {
    CurrencyInput(
        value = "1",
        onValueChange = {},
        currencyCode = "EUR"
    )
}
