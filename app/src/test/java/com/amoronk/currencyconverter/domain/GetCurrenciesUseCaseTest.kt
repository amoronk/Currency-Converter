package com.amoronk.currencyconverter.domain

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.model.Currency
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import com.amoronk.currencyconverter.domain.usecase.GetCurrenciesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrenciesUseCaseTest {

    private lateinit var repository: CurrencyConverterRepository
    private lateinit var getCurrenciesUseCase: GetCurrenciesUseCase

    @Before
    fun setup() {
        repository = mockk()
        getCurrenciesUseCase = GetCurrenciesUseCase(repository)
    }

    @Test
    fun `when repository returns success with currencies, use case should return the same`() =
        runTest {
            val currencies = listOf(
                Currency(code = "USD", name = "US Dollar", symbol = "$"),
                Currency(code = "EUR", name = "Euro", symbol = "€")
            )
            val expected = Resource.Success(currencies)
            coEvery { repository.getAllCurrencies() } returns flowOf(expected)
            val actual = getCurrenciesUseCase().first()
            assertEquals(expected, actual)
        }

    @Test
    fun `when repository returns error, use case should return the same error`() = runTest {
        val errorMessage = "Error fetching currencies"
        val expected = Resource.Error<List<Currency>>(errorMessage)
        coEvery { repository.getAllCurrencies() } returns flowOf(expected)
        val actual = getCurrenciesUseCase().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `when repository returns loading, use case should return loading state`() = runTest {
        val expected = Resource.Loading<List<Currency>>()
        coEvery { repository.getAllCurrencies() } returns flowOf(expected)
        val actual = getCurrenciesUseCase().first()

        assertEquals(expected, actual)
    }
}
