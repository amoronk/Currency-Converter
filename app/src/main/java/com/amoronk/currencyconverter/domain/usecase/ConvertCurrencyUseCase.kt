package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.CurrencyConversion
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import java.math.BigDecimal
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(
        sourceCurrency: String,
        targetCurrency: String,
        amount: BigDecimal
    ): Resource<CurrencyConversion> {
        return repository.convertCurrency(sourceCurrency, targetCurrency, amount)
    }
}
