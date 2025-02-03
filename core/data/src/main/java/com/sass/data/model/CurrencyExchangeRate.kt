package com.sass.data.model

data class CurrencyExchangeRateDto(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: RatesDto,
)

data class RatesDto(
    val SEK: Double,
)
