package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.ExchangeRate
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import javax.inject.Inject

class GetExchangeRatesForBaseCurrencyUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(baseCurrency: String): Resource<List<ExchangeRate>> {
        return repository.getExchangeRatesForBaseCurrency(baseCurrency)
    }
}
