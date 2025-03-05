package com.amoronk.currencyconverter.di

import com.amoronk.currencyconverter.data.local.CurrencyConverterDataStore
import com.amoronk.currencyconverter.data.local.CurrencyConverterDataStoreImpl
import com.amoronk.currencyconverter.data.remote.CurrencyConverterDataSource
import com.amoronk.currencyconverter.data.remote.CurrencyConverterDataSourceImpl
import com.amoronk.currencyconverter.data.repo.CurrencyConverterRepositoryImpl
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyModule {

    @Binds
    @Singleton
    abstract fun bindCurrencyRemoteDataSource(
        impl: CurrencyConverterDataSourceImpl
    ): CurrencyConverterDataSource

    @Binds
    @Singleton
    abstract fun bindCurrencyLocalDataSource(
        impl: CurrencyConverterDataStoreImpl
    ): CurrencyConverterDataStore

    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(
        impl: CurrencyConverterRepositoryImpl
    ): CurrencyConverterRepository
}
