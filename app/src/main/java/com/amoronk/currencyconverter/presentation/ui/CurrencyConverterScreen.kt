package com.amoronk.currencyconverter.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.presentation.components.ConvertButton
import com.amoronk.currencyconverter.presentation.components.CurrencyInput
import com.amoronk.currencyconverter.presentation.components.CurrencySelectorLayout
import com.amoronk.currencyconverter.presentation.components.ExchangeRateInfo
import com.amoronk.currencyconverter.presentation.components.RateGraph
import com.amoronk.currencyconverter.presentation.components.RatePoint
import com.amoronk.currencyconverter.presentation.components.TimeFrame
import com.amoronk.currencyconverter.presentation.state.CurrencyConverterState
import com.amoronk.currencyconverter.presentation.theme.CurrencyConverterTheme
import com.amoronk.currencyconverter.presentation.theme.PrimaryBlue
import com.amoronk.currencyconverter.presentation.theme.SecondaryGreen
import com.amoronk.currencyconverter.presentation.vm.CurrencyConverterViewModel
import java.util.Date
import kotlin.random.Random

@Composable
fun CurrencyConverterScreen(
    viewModel: CurrencyConverterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        val error = state.errorMessage
        if (error != null) {
            snackbarHostState.showSnackbar(message = error)
        }
    }

    CurrencyConverterScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onFromAmountChanged = { viewModel.setFromAmount(it) },
        onFromCurrencySelected = { viewModel.setFromCurrency(it) },
        onToCurrencySelected = { viewModel.setToCurrency(it) },
        onSwapCurrencies = { viewModel.swapCurrencies() },
        onConvertClicked = { viewModel.convert() },
        onTimeFrameSelected = { viewModel.setTimeFrame(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreenContent(
    state: CurrencyConverterState,
    snackbarHostState: SnackbarHostState,
    onFromAmountChanged: (String) -> Unit,
    onFromCurrencySelected: (Currency) -> Unit,
    onToCurrencySelected: (Currency) -> Unit,
    onSwapCurrencies: () -> Unit,
    onConvertClicked: () -> Unit,
    onTimeFrameSelected: (TimeFrame) -> Unit,
    onMenuClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val fromCurrencyCode = state.sourceCurrency?.code ?: "EUR"
    val toCurrencyCode = state.targetCurrency?.code ?: "PLN"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onMenuClicked) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = SecondaryGreen
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = onSignUpClicked
                    ) {
                        Text(
                            text = "Sign up",
                            color = SecondaryGreen,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Row {
                Column {
                    Text(
                        text = "Currency",
                        style = MaterialTheme.typography.headlineLarge,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                    Text(
                        text = "Calculator",
                        style = MaterialTheme.typography.headlineLarge,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                }
                Column(modifier = Modifier.align(Alignment.Bottom)) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(SecondaryGreen, shape = RoundedCornerShape(50))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }

            Spacer(modifier = Modifier.height(48.dp))

            CurrencyInput(
                value = state.fromAmount,
                onValueChange = onFromAmountChanged,
                currencyCode = fromCurrencyCode,
                isReadOnly = false,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CurrencyInput(
                value = state.toAmount,
                onValueChange = {  },
                currencyCode = toCurrencyCode,
                isReadOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            CurrencySelectorLayout(
                fromCurrency = state.sourceCurrency,
                toCurrency = state.targetCurrency,
                currencies = state.currencies,
                onFromCurrencySelected = onFromCurrencySelected,
                onToCurrencySelected = onToCurrencySelected,
                onSwap = onSwapCurrencies
            )

            Spacer(modifier = Modifier.height(32.dp))

            ConvertButton(
                onClick = onConvertClicked,
                isLoading = state.isConverting,
                enabled = state.fromAmount.isNotEmpty() && state.sourceCurrency != null && state.targetCurrency != null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ExchangeRateInfo(
                    timestamp = state.exchangeRateTimestamp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (state.sourceCurrency != null && state.targetCurrency != null) {
                RateGraph(
                    rates = state.historicalRates,
                    fromCurrency = fromCurrencyCode,
                    toCurrency = toCurrencyCode,
                    isLoading = state.isHistoricalRatesLoading,
                    error = state.historyErrorMessage,
                    onTimeFrameSelected = onTimeFrameSelected,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterScreenContentPreview() {
    CurrencyConverterTheme {
        val previewState = CurrencyConverterState(
            fromAmount = "1",
            toAmount = "4.264820",
            sourceCurrency = Currency(code = "EUR", name = "Euro", symbol = "€"),
            targetCurrency = Currency(code = "PLN", name = "Polish Złoty", symbol = "zł"),
            exchangeRateTimestamp = "13:38",
            historicalRates = List(30) { index ->
                val date = Date(System.currentTimeMillis() - (29 - index) * 24 * 60 * 60 * 1000L)
                val rate = 4.2 + (Random.nextFloat() * 0.4)
                RatePoint(date, rate)
            }
        )

        CurrencyConverterScreenContent(
            state = previewState,
            snackbarHostState = SnackbarHostState(),
            onFromAmountChanged = {},
            onFromCurrencySelected = {},
            onToCurrencySelected = {},
            onSwapCurrencies = {},
            onConvertClicked = {},
            onTimeFrameSelected = {}
        )
    }
}
