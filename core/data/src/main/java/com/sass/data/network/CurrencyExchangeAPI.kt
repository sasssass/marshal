package com.sass.data.network

import com.sass.data.model.CurrencyExchangeRateDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyExchangeAPI {
    @GET("v1/latest")
    suspend fun getExchangeRates(
        @Query("base") baseCurrency: String = "USD",
    ): CurrencyExchangeRateDto
}
