package com.amoronk.currencyconverter.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amoronk.currencyconverter.data.local.dao.CurrencyDao
import com.amoronk.currencyconverter.data.local.dao.ExchangeRateDao
import com.amoronk.currencyconverter.data.local.model.CurrencyEntity
import com.amoronk.currencyconverter.data.local.model.ExchangeRateEntity

@Database(
    entities = [CurrencyEntity::class, ExchangeRateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun exchangeRateDao(): ExchangeRateDao
}
