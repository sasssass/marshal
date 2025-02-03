package com.sass.domain.usecase.crypto

import com.sass.domain.model.currency.CryptoCurrency
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.utils.Response
import com.sass.domain.repository.CurrencyRepository
import com.sass.domain.usecase.BaseUseCaseFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptoUseCase
    @Inject
    constructor(
        private val repository: CurrencyRepository,
    ) : BaseUseCaseFlow<GetCryptoUseCase.Param, Response<CryptoCurrency>>() {
        data class Param(
            val currencyType: CurrencyType,
            val currencyId: String,
        )

        override suspend fun invoke(input: Param): Flow<Response<CryptoCurrency>> =
            repository.getCurrency(input.currencyType, input.currencyId)
    }
