package com.amoronk.currencyconverter.presentation.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amoronk.currencyconverter.presentation.theme.SecondaryGreen

@Composable
fun ConvertButton(
    onClick: () -> Unit,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryGreen,
            contentColor = Color.White,
            disabledContainerColor = SecondaryGreen.copy(alpha = 0.6f),
            disabledContentColor = Color.White.copy(alpha = 0.6f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Text(
                    text = "Convert",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ConvertButtonPreview() {
    ConvertButton(
        onClick = {},
        isLoading = false
    )
}
