package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.remote.model.CurrencyTimeSeriesResponse
import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import java.util.Date
import javax.inject.Inject

class GetTimeSeriesDataUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(
        sourceCurrency: String,
        targetCurrency: String,
        startDate: Date,
        endDate: Date
    ): Resource<CurrencyTimeSeriesResponse> {
        return repository.getTimeSeriesData(sourceCurrency, targetCurrency, startDate, endDate)
    }
}
