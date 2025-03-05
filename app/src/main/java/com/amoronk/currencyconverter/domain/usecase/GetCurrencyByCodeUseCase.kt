package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import javax.inject.Inject

class GetCurrencyByCodeUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(code: String): Resource<Currency?> {
        return repository.getCurrencyByCode(code)
    }
}
