package com.amoronk.currencyconverter.data.local

import com.amoronk.currencyconverter.data.local.dao.CurrencyDao
import com.amoronk.currencyconverter.data.local.dao.ExchangeRateDao
import com.amoronk.currencyconverter.data.local.model.CurrencyEntity
import com.amoronk.currencyconverter.data.local.model.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyConverterDataStoreImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val exchangeRateDao: ExchangeRateDao
) : CurrencyConverterDataStore {
    override fun getAllCurrencies(): Flow<List<CurrencyEntity>> {
        return currencyDao.getAllCurrencies()
    }

    override suspend fun getCurrencyByCode(code: String): CurrencyEntity? {
        return currencyDao.getCurrencyByCode(code)
    }

    override suspend fun saveCurrencies(currencies: List<CurrencyEntity>) {
        currencyDao.insertCurrencies(currencies)
    }

    override fun getAllRates(): Flow<List<ExchangeRateEntity>> {
        return exchangeRateDao.getAllRates()
    }

    override suspend fun getRatesForBaseCurrency(baseCurrency: String): List<ExchangeRateEntity> {
        return exchangeRateDao.getRatesForBaseCurrency(baseCurrency)
    }

    override suspend fun getRate(
        baseCurrency: String,
        targetCurrency: String
    ): ExchangeRateEntity? {
        return exchangeRateDao.getRate(baseCurrency, targetCurrency)
    }

    override suspend fun saveRates(rates: List<ExchangeRateEntity>) {
        return exchangeRateDao.insertRates(rates)
    }
}
