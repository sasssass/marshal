package com.sass.domain.model.currency

data class CryptoCurrency(
    val name: String,
    val id: Int,
    val code: String,
    val icon: String,
    val value: Double,
    val marketCap: Double,
    val totalSupply: Double,
    val valueType: CurrencyType,
    val links: Links?,
)

data class Links(
    val twitter: String?,
    val github: String?,
    val facebook: String?,
    val website: String?,
    val reddit: String?,
)
