package com.amoronk.currencyconverter.data.util

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException


suspend fun <T> safeCall(
    call: suspend () -> T
): Resource<T> {
    return try {
        Resource.Success(call.invoke())
    } catch (e: UnknownHostException) {
        Resource.Error(
            message = "No internet connection. Please check your network and try again."
        )
    } catch (e: IOException) {
        Resource.Error(
            message = "Couldn't connect to the server. Please check your internet and try again."
        )
    } catch (e: HttpException) {
        Resource.Error(
            message = "Something went wrong. Please try again later."
        )
    } catch (e: Exception) {
        Resource.Error(
            message = "An unexpected error occurred. Please try again."
        )
    }
}
