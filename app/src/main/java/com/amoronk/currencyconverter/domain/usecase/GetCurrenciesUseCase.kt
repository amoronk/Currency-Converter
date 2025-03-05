package com.amoronk.currencyconverter.domain.usecase

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    operator fun invoke(): Flow<Resource<List<Currency>>> {
        return repository.getAllCurrencies()
    }
}
