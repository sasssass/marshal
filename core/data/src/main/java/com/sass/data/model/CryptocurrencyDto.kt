package com.sass.data.model

data class CryptocurrencyDto(
    val data: Map<String, CryptocurrenciesDtoData>,
    val status: Status,
)
