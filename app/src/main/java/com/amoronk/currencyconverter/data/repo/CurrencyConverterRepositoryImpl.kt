package com.amoronk.currencyconverter.data.repo

import com.amoronk.currencyconverter.data.local.CurrencyConverterDataStore
import com.amoronk.currencyconverter.data.local.model.CurrencyEntity
import com.amoronk.currencyconverter.data.local.model.ExchangeRateEntity
import com.amoronk.currencyconverter.data.mapper.CurrencyMapper
import com.amoronk.currencyconverter.data.remote.CurrencyConverterDataSource
import com.amoronk.currencyconverter.data.remote.model.CurrencyRateResponse
import com.amoronk.currencyconverter.data.remote.model.CurrencyTimeSeriesResponse
import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.data.util.safeCall
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.domain.model.CurrencyConversion
import com.amoronk.currencyconverter.domain.model.ExchangeRate
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.util.Date
import javax.inject.Inject

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CurrencyConverterDataSource,
    private val localDataStore: CurrencyConverterDataStore,
    private val currencyMapper: CurrencyMapper,
) : CurrencyConverterRepository {
    override fun getAllCurrencies(): Flow<Resource<List<Currency>>> {
        return localDataStore.getAllCurrencies()
            .map { currencies ->
                Resource.Success(currencyMapper.mapToCurrencyDomainModelList(currencies))
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getCurrencyByCode(code: String): Resource<Currency?> =
        withContext(Dispatchers.IO) {
            return@withContext safeCall {
                localDataStore.getCurrencyByCode(code)?.let { entity ->
                    currencyMapper.mapToCurrencyDomainModel(entity)
                }
            }
        }

    override suspend fun refreshCurrencies(): Resource<Unit> = withContext(Dispatchers.IO) {
        return@withContext safeCall {
            val remoteCurrencies = remoteDataSource.getCurrencies()
            if (remoteCurrencies.success) {
                val currencyEntities = remoteCurrencies.symbols.map { (code, name) ->
                    CurrencyEntity(
                        code = code,
                        name = name,
                        symbol = code
                    )
                }

                localDataStore.saveCurrencies(currencyEntities)
            } else {
                throw Exception("Failed to fetch currencies from API")
            }
        }
    }

    override fun getAllExchangeRates(): Flow<Resource<List<ExchangeRate>>> {
        return localDataStore.getAllRates()
            .map { rates ->
                Resource.Success(currencyMapper.mapToExchangeRateDomainModelList(rates))
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getExchangeRatesForBaseCurrency(baseCurrency: String): Resource<List<ExchangeRate>> =
        withContext(Dispatchers.IO) {
            return@withContext safeCall {
                val entities = localDataStore.getRatesForBaseCurrency(baseCurrency)
                currencyMapper.mapToExchangeRateDomainModelList(entities)
            }
        }

    override suspend fun getExchangeRate(
        baseCurrency: String,
        targetCurrency: String
    ): Resource<ExchangeRate?> = withContext(Dispatchers.IO) {
        return@withContext safeCall {
            localDataStore.getRate(baseCurrency, targetCurrency)?.let { entity ->
                currencyMapper.mapToExchangeRateDomainModel(entity)
            }
        }
    }

    override suspend fun refreshExchangeRates(baseCurrency: String): Resource<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext safeCall {
                val baseEUR = "EUR"

                val rateResponse = remoteDataSource.getLatestRates(baseEUR)

                if (rateResponse.isSuccessful) {
                    val rateEntities = mutableListOf<ExchangeRateEntity>()

                    if (baseCurrency == baseEUR) {
                        rateResponse.exchangeRates.forEach { (currencyCode, rate) ->
                            rateEntities.add(
                                ExchangeRateEntity(
                                    sourceCurrency = baseEUR,
                                    targetCurrency = currencyCode,
                                    rate = rate,
                                    lastUpdated = System.currentTimeMillis()
                                )
                            )
                        }
                    } else {
                        val baseRate = rateResponse.exchangeRates[baseCurrency] ?: 0.0

                        if (baseRate > 0) {
                            rateEntities.add(
                                ExchangeRateEntity(
                                    sourceCurrency = baseCurrency,
                                    targetCurrency = baseEUR,
                                    rate = 1.0 / baseRate,
                                    lastUpdated = System.currentTimeMillis()
                                )
                            )

                            rateResponse.exchangeRates.forEach { (currencyCode, eurRate) ->
                                if (currencyCode != baseCurrency) {
                                    val rate = eurRate / baseRate

                                    rateEntities.add(
                                        ExchangeRateEntity(
                                            sourceCurrency = baseCurrency,
                                            targetCurrency = currencyCode,
                                            rate = rate,
                                            lastUpdated = System.currentTimeMillis()
                                        )
                                    )
                                }
                            }
                        } else {
                            throw Exception("Invalid base currency")
                        }
                    }

                    localDataStore.saveRates(rateEntities)
                } else {
                    throw Exception("Failed to fetch exchange rates from API")
                }
            }
        }

    override suspend fun convertCurrency(
        sourceCurrency: String,
        targetCurrency: String,
        amount: BigDecimal
    ): Resource<CurrencyConversion> = withContext(Dispatchers.IO) {
        return@withContext safeCall {
            val response = remoteDataSource.convertCurrency(sourceCurrency, targetCurrency, amount)
           currencyMapper.mapToCurrencyConversionDomainModel(response)
        }
    }

    override suspend fun getHistoricalRate(
        date: String,
        baseCurrency: String,
        symbols: String
    ): Resource<CurrencyRateResponse> = withContext(Dispatchers.IO) {
        return@withContext safeCall {
            remoteDataSource.getHistoricalRate(date, baseCurrency, symbols)
        }
    }

    override suspend fun getTimeSeriesData(
        sourceCurrency: String,
        targetCurrency: String,
        startDate: Date,
        endDate: Date
    ): Resource<CurrencyTimeSeriesResponse> = withContext(Dispatchers.IO) {
        return@withContext safeCall {
            remoteDataSource.getTimeSeries(sourceCurrency, targetCurrency, startDate, endDate)
        }
    }
}
