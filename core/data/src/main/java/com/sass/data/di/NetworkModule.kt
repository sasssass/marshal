package com.sass.data.di

import com.sass.data.network.CryptoCurrencyAPI
import com.sass.data.network.CurrencyExchangeAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL_CRYPTO = "https://pro-api.coinmarketcap.com/"
const val BASE_URL_EXCHANGE = "https://api.frankfurter.dev"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideCurrencyAPI(): CryptoCurrencyAPI =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL_CRYPTO)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        },
                    ).addInterceptor(
                        Interceptor { chain ->
                            val request: Request =
                                chain
                                    .request()
                                    .newBuilder()
                                    .addHeader("X-CMC_PRO_API_KEY", "45890bb8-b690-47a4-aadb-a370dab6f5e6")
                                    .build()
                            chain.proceed(request)
                        },
                    ).build(),
            ).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoCurrencyAPI::class.java)

    @Provides
    fun provideCurrencyExchangeAPI(): CurrencyExchangeAPI =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL_EXCHANGE)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        },
                    ).build(),
            ).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyExchangeAPI::class.java)
}
