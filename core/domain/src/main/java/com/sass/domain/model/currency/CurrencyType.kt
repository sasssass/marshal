package com.sass.domain.model.currency

enum class CurrencyType(
    val code: String,
    val sign: String,
    val realName: String,
) {
    SwedishKrona("sek", "kr", "Swedish krona"),
    USDollar("usd", "$", "US Dollar"),
}
