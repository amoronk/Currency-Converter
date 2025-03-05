package com.amoronk.currencyconverter.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import com.amoronk.currencyconverter.domain.usecase.GetCurrenciesUseCase
import com.amoronk.currencyconverter.domain.usecase.GetTimeSeriesDataUseCase
import com.amoronk.currencyconverter.domain.usecase.RefreshCurrenciesUseCase
import com.amoronk.currencyconverter.domain.usecase.RefreshExchangeRatesUseCase
import com.amoronk.currencyconverter.presentation.components.RatePoint
import com.amoronk.currencyconverter.presentation.components.TimeFrame
import com.amoronk.currencyconverter.presentation.format
import com.amoronk.currencyconverter.presentation.state.CurrencyConverterState
import com.amoronk.currencyconverter.presentation.toFormattedTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getTimeSeriesDataUseCase: GetTimeSeriesDataUseCase,
    private val refreshExchangeRatesUseCase: RefreshExchangeRatesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyConverterState())
    val state: StateFlow<CurrencyConverterState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CurrencyConverterState()
    )

    init {
        loadCurrencies()
        refreshData()
    }

    private fun loadCurrencies() {
        getCurrenciesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val currencies = result.data ?: emptyList()

                    val sourceCurrency =
                        _state.value.sourceCurrency ?: currencies.find { it.code == "EUR" }
                    val targetCurrency =
                        _state.value.targetCurrency ?: currencies.find { it.code == "PLN" }

                    _state.update { currentState ->
                        currentState.copy(
                            currencies = currencies,
                            isCurrenciesLoading = false,
                            errorMessage = null,
                            sourceCurrency = sourceCurrency,
                            targetCurrency = targetCurrency,
                            exchangeRateTimestamp = (System.currentTimeMillis() / 1000).toFormattedTime()
                        )
                    }

                    if (sourceCurrency != null && targetCurrency != null) {
                        loadHistoricalRates(
                            sourceCurrency.code,
                            targetCurrency.code,
                            _state.value.selectedTimeFrame
                        )
                        convert()
                    }
                }

                is Resource.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isCurrenciesLoading = false,
                            errorMessage = result.message
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isCurrenciesLoading = true
                        )
                    }
                }
            }
        }
            .catch { e ->
                _state.update { currentState ->
                    currentState.copy(
                        isCurrenciesLoading = false,
                        errorMessage = e.message
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun refreshData() {
        viewModelScope.launch {
            val currencyResult = refreshCurrenciesUseCase()

            var refreshError: String? = null
            if (currencyResult is Resource.Error) {
                refreshError = currencyResult.message
            }

            _state.value.sourceCurrency?.let { fromCurrency ->
                val ratesResult = refreshExchangeRatesUseCase(fromCurrency.code)
                if (ratesResult is Resource.Error && refreshError != null) {
                    refreshError = ratesResult.message
                }
            }

            if (refreshError != null) {
                _state.update { currentState ->
                    currentState.copy(
                        errorMessage = refreshError
                    )
                }
            }
        }
    }


    fun setFromCurrency(currency: Currency) {
        _state.update { currentState ->
            currentState.copy(
                sourceCurrency = currency
            )
        }

        _state.value.targetCurrency?.let { toCurrency ->
            loadHistoricalRates(currency.code, toCurrency.code, _state.value.selectedTimeFrame)
        }

        convert()
    }

    fun setToCurrency(currency: Currency) {
        _state.update { currentState ->
            currentState.copy(
                targetCurrency = currency
            )
        }

        _state.value.sourceCurrency?.let { fromCurrency ->
            loadHistoricalRates(fromCurrency.code, currency.code, _state.value.selectedTimeFrame)
        }

        convert()
    }

    fun setFromAmount(amount: String) {
        _state.update { currentState ->
            currentState.copy(
                fromAmount = amount
            )
        }

        convert()
    }

    fun swapCurrencies() {
        val fromCurrency = _state.value.sourceCurrency
        val toCurrency = _state.value.targetCurrency

        if (fromCurrency != null && toCurrency != null) {
            _state.update { currentState ->
                currentState.copy(
                    sourceCurrency = toCurrency,
                    targetCurrency = fromCurrency,
                    fromAmount = currentState.toAmount.takeIf { it.isNotEmpty() }
                        ?: currentState.fromAmount,
                    toAmount = ""
                )
            }

            loadHistoricalRates(toCurrency.code, fromCurrency.code, _state.value.selectedTimeFrame)

            convert()
        }
    }

    fun convert() {
        val fromCurrency = _state.value.sourceCurrency
        val toCurrency = _state.value.targetCurrency
        val fromAmount = _state.value.fromAmount.toBigDecimalOrNull()

        if (fromCurrency != null && toCurrency != null && fromAmount != null) {
            _state.update { currentState ->
                currentState.copy(
                    isConverting = true,
                    errorMessage = null
                )
            }

            viewModelScope.launch {
                val result = convertCurrencyUseCase(
                    sourceCurrency = fromCurrency.code,
                    targetCurrency = toCurrency.code,
                    amount = fromAmount
                )

                when (result) {
                    is Resource.Success -> {
                        result.data?.let { response ->
                            if (response.isSuccessful) {
                                _state.update { currentState ->
                                    currentState.copy(
                                        toAmount = response.convertedAmount?.format().orEmpty(),
                                        isConverting = false,
                                        errorMessage = null,
                                        exchangeRateTimestamp = response.timestamp?.toFormattedTime().orEmpty()
                                    )
                                }
                            } else {
                                _state.update { currentState ->
                                    currentState.copy(
                                        isConverting = false,
                                        errorMessage = response.error
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        _state.update { currentState ->
                            currentState.copy(
                                isConverting = false,
                                errorMessage = result.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                    }
                }
            }
        }
    }

    private fun loadHistoricalRates(
        fromCurrency: String,
        toCurrency: String,
        timeFrame: TimeFrame
    ) {
        _state.update { currentState ->
            currentState.copy(
                isHistoricalRatesLoading = true,
                selectedTimeFrame = timeFrame
            )
        }

        val endDate = Calendar.getInstance().time
        val startDate = Calendar.getInstance().apply {
            time = endDate
            add(
                Calendar.DAY_OF_YEAR, -when (timeFrame) {
                    TimeFrame.DAYS_30 -> 30
                    TimeFrame.DAYS_90 -> 90
                }
            )
        }.time

        viewModelScope.launch {
            val result = getTimeSeriesDataUseCase(
                sourceCurrency = fromCurrency,
                targetCurrency = toCurrency,
                startDate = startDate,
                endDate = endDate
            )

            when (result) {
                is Resource.Success -> {
                    result.data?.let { response ->
                        if (response.isSuccessful) {
                            val ratePoints = response.exchangeRates.map { (dateStr, ratesMap) ->
                                val rate = ratesMap[toCurrency] ?: 0.0
                                val date =
                                    SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateStr)
                                        ?: Date()
                                RatePoint(date, rate)
                            }.sortedBy { it.date }

                            _state.update { currentState ->
                                currentState.copy(
                                    historicalRates = ratePoints,
                                    isHistoricalRatesLoading = false,
                                    errorMessage = null
                                )
                            }
                        } else {
                            _state.update { currentState ->
                                currentState.copy(
                                    isHistoricalRatesLoading = false,
                                    historyErrorMessage = response.error?.info
                                )
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isHistoricalRatesLoading = false,
                            errorMessage = result.message
                        )
                    }
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    fun setTimeFrame(timeFrame: TimeFrame) {
        val fromCurrency = _state.value.sourceCurrency
        val toCurrency = _state.value.targetCurrency

        if (fromCurrency != null && toCurrency != null) {
            loadHistoricalRates(fromCurrency.code, toCurrency.code, timeFrame)
        }
    }
}
