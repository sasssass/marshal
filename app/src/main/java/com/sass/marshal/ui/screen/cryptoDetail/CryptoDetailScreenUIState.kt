package com.sass.marshal.ui.screen.cryptoDetail

import com.sass.domain.model.currency.CryptoCurrency

data class CryptoDetailScreenUIState(
    val loading: Boolean = false,
    val error: String? = null,
    val cryptoCurrency: CryptoCurrency? = null,
)
