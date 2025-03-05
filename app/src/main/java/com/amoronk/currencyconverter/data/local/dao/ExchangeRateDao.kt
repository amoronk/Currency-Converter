package com.amoronk.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amoronk.currencyconverter.data.local.model.ExchangeRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeRateDao {
    @Query("SELECT * FROM exchange_rates")
    fun getAllRates(): Flow<List<ExchangeRateEntity>>

    @Query("SELECT * FROM exchange_rates WHERE sourceCurrency = :sourceCurrency")
    suspend fun getRatesForBaseCurrency(sourceCurrency: String): List<ExchangeRateEntity>

    @Query("SELECT * FROM exchange_rates WHERE sourceCurrency = :sourceCurrency AND targetCurrency = :targetCurrency")
    fun getRate(sourceCurrency: String, targetCurrency: String): ExchangeRateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<ExchangeRateEntity>)
}
