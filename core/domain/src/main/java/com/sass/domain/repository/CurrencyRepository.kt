package com.sass.domain.repository

import com.sass.domain.model.currency.CryptoCurrency
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.utils.Response
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun allCurrencies(currencyType: CurrencyType): Flow<Response<List<CryptoCurrency>>>

    fun getCurrency(
        currencyType: CurrencyType,
        id: String,
    ): Flow<Response<CryptoCurrency>>
}
