package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.ExchangeRate
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(baseCurrency: String, targetCurrency: String): Resource<ExchangeRate?> {
        return repository.getExchangeRate(baseCurrency, targetCurrency)
    }
}
