package com.amoronk.currencyconverter.domain

import com.amoronk.currencyconverter.data.remote.model.ConversionDetails
import com.amoronk.currencyconverter.data.remote.model.CurrencyConversionRequest
import com.amoronk.currencyconverter.data.remote.model.CurrencyConversionResponse
import com.amoronk.currencyconverter.data.util.Resource
import com.amoronk.currencyconverter.domain.repo.CurrencyConverterRepository
import com.amoronk.currencyconverter.domain.usecase.ConvertCurrencyUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ConvertCurrencyUseCaseTest {
    
    private lateinit var repository: CurrencyConverterRepository
    private lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase
    
    @Before
    fun setup() {
        repository = mockk()
        convertCurrencyUseCase = ConvertCurrencyUseCase(repository)
    }
    
    @Test
    fun `when repository returns successful conversion, use case should return the same response`() = runTest {
        val from = "EUR"
        val to = "USD"
        val amount = BigDecimal("100")
        val response = CurrencyConversionResponse(
            isSuccessful = true,
            conversionRequest = CurrencyConversionRequest(sourceCurrency = from, targetCurrency = to, amountToConvert = amount),
            conversionDetails = ConversionDetails(timestamp = 1234567890, exchangeRate = 1.1),
            convertedAmount = BigDecimal("110.00"),
            historical = null
        )
        val expected = Resource.Success(response)
        
        coEvery { repository.convertCurrency(from, to, amount) } returns expected
        
        val actual = convertCurrencyUseCase(from, to, amount)
        
        assertEquals(expected, actual)
    }
    
    @Test
    fun `when repository returns error, use case should return the same error`() = runTest {
        val from = "EUR"
        val to = "USD"
        val amount = BigDecimal("100")
        val errorMessage = "Error converting currencies"
        val expected = Resource.Error<CurrencyConversionResponse>(errorMessage)
        
        coEvery { repository.convertCurrency(from, to, amount) } returns expected
        
        val actual = convertCurrencyUseCase(from, to, amount)
        
        assertEquals(expected, actual)
    }
    
    @Test
    fun `when repository returns loading, use case should return loading state`() = runTest {
        val from = "EUR"
        val to = "USD"
        val amount = BigDecimal("100")
        val expected = Resource.Loading<CurrencyConversionResponse>()
        
        coEvery { repository.convertCurrency(from, to, amount) } returns expected
        
        val actual = convertCurrencyUseCase(from, to, amount)
        
        assertEquals(expected, actual)
    }
}
