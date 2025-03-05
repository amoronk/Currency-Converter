package com.amoronk.currencyconverter.di

import android.app.Application
import androidx.room.Room
import com.amoronk.currencyconverter.data.local.db.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCurrencyDatabase(app: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            app,
            CurrencyDatabase::class.java,
            "currency_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(db: CurrencyDatabase) = db.currencyDao()

    @Provides
    @Singleton
    fun provideExchangeRateDao(db: CurrencyDatabase) = db.exchangeRateDao()
}
