package com.amoronk.currencyconverter.data.local

import com.amoronk.currencyconverter.data.local.model.CurrencyEntity
import com.amoronk.currencyconverter.data.local.model.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyConverterDataStore {
    fun getAllCurrencies(): Flow<List<CurrencyEntity>>

    suspend fun getCurrencyByCode(code: String): CurrencyEntity?

    suspend fun saveCurrencies(currencies: List<CurrencyEntity>)

    fun getAllRates(): Flow<List<ExchangeRateEntity>>

    suspend fun getRatesForBaseCurrency(baseCurrency: String): List<ExchangeRateEntity>

    suspend fun getRate(baseCurrency: String, targetCurrency: String): ExchangeRateEntity?

    suspend fun saveRates(rates: List<ExchangeRateEntity>)
}
