package com.amoronk.currencyconverter.data.mapper

import com.amoronk.currencyconverter.data.local.model.CurrencyEntity
import com.amoronk.currencyconverter.data.local.model.ExchangeRateEntity
import com.amoronk.currencyconverter.data.remote.model.CurrencyConversionResponse
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.domain.model.CurrencyConversion
import com.amoronk.currencyconverter.domain.model.ExchangeRate
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyMapper @Inject constructor() {
    fun mapToCurrencyDomainModel(entity: CurrencyEntity): Currency {
        return Currency(
            code = entity.code,
            name = entity.name,
            symbol = entity.symbol ?: entity.code
        )
    }

    fun mapToCurrencyDomainModelList(entities: List<CurrencyEntity>): List<Currency> {
        return entities.map { mapToCurrencyDomainModel(it) }
    }

    fun mapToExchangeRateDomainModel(entity: ExchangeRateEntity): ExchangeRate {
        return ExchangeRate(
            sourceCurrency = entity.sourceCurrency,
            targetCurrency = entity.targetCurrency,
            rate = entity.rate,
            date = Date(entity.lastUpdated)
        )
    }

    fun mapToExchangeRateDomainModelList(entities: List<ExchangeRateEntity>): List<ExchangeRate> {
        return entities.map { mapToExchangeRateDomainModel(it) }
    }

    fun mapToCurrencyConversionDomainModel(response: CurrencyConversionResponse): CurrencyConversion {
        return CurrencyConversion(
            isSuccessful = response.isSuccessful,
            sourceCurrency = response.conversionRequest.sourceCurrency,
            targetCurrency = response.conversionRequest.targetCurrency,
            sourceAmount = response.conversionRequest.amountToConvert,
            convertedAmount = response.convertedAmount,
            exchangeRate = response.conversionDetails.exchangeRate,
            timestamp = response.conversionDetails.timestamp,
            isHistorical = response.historical ?: false,
            error = response.error?.info
        )
    }
}
