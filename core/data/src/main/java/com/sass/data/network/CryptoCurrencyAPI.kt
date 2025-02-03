package com.sass.data.network

import com.sass.data.model.CryptocurrenciesDto
import com.sass.data.model.CryptocurrencyDto
import com.sass.data.model.CryptocurrencyInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCurrencyAPI {
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun allCurrencies(): CryptocurrenciesDto

    @GET("/v2/cryptocurrency/quotes/latest")
    suspend fun getCurrency(
        @Query("id") id: String,
    ): CryptocurrencyDto

    @GET("/v1/cryptocurrency/info")
    suspend fun getCurrencyInfo(
        @Query("id") id: String,
    ): CryptocurrencyInfoDto
}
