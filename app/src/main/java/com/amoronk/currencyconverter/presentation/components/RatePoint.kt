package com.amoronk.currencyconverter.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amoronk.currencyconverter.presentation.theme.PrimaryBlue
import com.amoronk.currencyconverter.presentation.theme.SecondaryGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class RatePoint(val date: Date, val rate: Double)

enum class TimeFrame { DAYS_30, DAYS_90 }

@Composable
fun RateGraph(
    rates: List<RatePoint>,
    fromCurrency: String,
    toCurrency: String,
    isLoading: Boolean = false,
    error: String? = null,
    onTimeFrameSelected: (TimeFrame) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTimeFrame by remember { mutableStateOf(TimeFrame.DAYS_30) }
    
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TabRow(
                selectedTabIndex = when (selectedTimeFrame) {
                    TimeFrame.DAYS_30 -> 0
                    TimeFrame.DAYS_90 -> 1
                },
                containerColor = PrimaryBlue,
                contentColor = Color.White,
                divider = {},
                indicator = {}
            ) {
                Tab(
                    selected = selectedTimeFrame == TimeFrame.DAYS_30,
                    onClick = { 
                        selectedTimeFrame = TimeFrame.DAYS_30
                        onTimeFrameSelected(TimeFrame.DAYS_30)
                    },
                    text = {
                        Text(
                            text = "Past 30 days",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selectedTimeFrame == TimeFrame.DAYS_30) FontWeight.Bold else FontWeight.Normal,
                            color = Color.White
                        )
                    }
                )
                Tab(
                    selected = selectedTimeFrame == TimeFrame.DAYS_90,
                    onClick = { 
                        selectedTimeFrame = TimeFrame.DAYS_90
                        onTimeFrameSelected(TimeFrame.DAYS_90) 
                    },
                    text = {
                        Text(
                            text = "Past 90 days",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (selectedTimeFrame == TimeFrame.DAYS_90) FontWeight.Bold else FontWeight.Normal,
                            color = Color.White
                        )
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = error,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                rates.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No historical data available",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> {
                    val filteredRates = when (selectedTimeFrame) {
                        TimeFrame.DAYS_30 -> rates.takeLast(30)
                        TimeFrame.DAYS_90 -> rates
                    }
                    
                    if (filteredRates.isNotEmpty()) {
                        val latestRate = filteredRates.last()
                        val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
                        val formattedDate = dateFormatter.format(latestRate.date)
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formattedDate,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Box(
                                modifier = Modifier
                                    .size(60.dp, 30.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SecondaryGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "1 $fromCurrency = ${String.format("%.4f", latestRate.rate)} $toCurrency",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        if (filteredRates.size > 1) {
                            val min = filteredRates.minOf { it.rate }
                            val max = filteredRates.maxOf { it.rate }
                            
                            Canvas(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            ) {
                                val path = Path()
                                val width = size.width
                                val height = size.height
                                val verticalPadding = height * 0.1f
                                
                                filteredRates.forEachIndexed { index, rate ->
                                    val x = index * width / (filteredRates.size - 1)
                                    val normalizedY = if (max > min) {
                                        (rate.rate - min) / (max - min)
                                    } else {
                                        0.5
                                    }
                                    val y = height - (normalizedY * (height - 2 * verticalPadding) + verticalPadding).toFloat()
                                    
                                    if (index == 0) {
                                        path.moveTo(x, y)
                                    } else {
                                        path.lineTo(x, y)
                                    }
                                    
                                    drawCircle(
                                        color = Color.White,
                                        radius = 3f,
                                        center = Offset(x, y)
                                    )
                                }
                                
                                drawPath(
                                    path = path,
                                    color = Color.White,
                                    style = Stroke(width = 2f)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (filteredRates.size >= 3) {
                        val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = dateFormatter.format(filteredRates.first().date),
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall
                            )
                            
                            Text(
                                text = dateFormatter.format(filteredRates[filteredRates.size / 2].date),
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall
                            )
                            
                            Text(
                                text = dateFormatter.format(filteredRates.last().date),
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Get rate alerts straight to your email inbox",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RateGraphPreview() {
    val ratePoints = List(30) { index ->
        val date = Date(System.currentTimeMillis() - (29 - index) * 24 * 60 * 60 * 1000L)
        val rate = 4.2 + (Math.random() * 0.4)
        RatePoint(date, rate)
    }
    
    RateGraph(
        rates = ratePoints,
        fromCurrency = "EUR",
        toCurrency = "PLN",
        modifier = Modifier.fillMaxWidth()
    )
}
