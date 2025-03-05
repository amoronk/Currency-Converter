package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.remote.model.CurrencyRateResponse
import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import javax.inject.Inject

class GetHistoricalRateUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(
        date: String,
        baseCurrency: String,
        symbols: String
    ): Resource<CurrencyRateResponse> {
        return repository.getHistoricalRate(date, baseCurrency, symbols)
    }
}
