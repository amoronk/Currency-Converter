package com.amoronk.currencyconverter.domain

import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import com.amoronk.currencyconverter.domain.usecase.RefreshCurrenciesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RefreshCurrenciesUseCaseTest {

    private lateinit var repository: CurrencyConverterRepository
    private lateinit var refreshCurrenciesUseCase: RefreshCurrenciesUseCase

    @Before
    fun setup() {
        repository = mockk()
        refreshCurrenciesUseCase = RefreshCurrenciesUseCase(repository)
    }

    @Test
    fun `when repository returns success, use case should return success`() = runTest {
        val expected = Resource.Success(Unit)
        coEvery { repository.refreshCurrencies() } returns expected

        val actual = refreshCurrenciesUseCase()

        assertEquals(expected, actual)
    }

    @Test
    fun `when repository returns error, use case should return the same error`() = runTest {
        val errorMessage = "Error refreshing currencies"
        val expected = Resource.Error<Unit>(errorMessage)
        coEvery { repository.refreshCurrencies() } returns expected

        val actual = refreshCurrenciesUseCase()

        assertEquals(expected, actual)
    }

    @Test
    fun `when repository returns loading, use case should return loading state`() = runTest {
        val expected = Resource.Loading<Unit>()
        coEvery { repository.refreshCurrencies() } returns expected

        val actual = refreshCurrenciesUseCase()

        assertEquals(expected, actual)
    }
}
