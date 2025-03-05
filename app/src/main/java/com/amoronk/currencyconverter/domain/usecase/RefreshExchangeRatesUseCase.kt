package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import javax.inject.Inject

class RefreshExchangeRatesUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(baseCurrency: String): Resource<Unit> {
        return repository.refreshExchangeRates(baseCurrency)
    }
}
