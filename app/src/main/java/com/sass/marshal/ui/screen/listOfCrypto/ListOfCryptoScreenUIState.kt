package com.sass.marshal.ui.screen.listOfCrypto

import com.sass.domain.model.currency.CryptoCurrency

data class ListOfCryptoScreenUIState(
    val loading: Boolean = false,
    val error: String? = null,
    val cryptoCurrencyList: List<CryptoCurrency> = emptyList(),
)
