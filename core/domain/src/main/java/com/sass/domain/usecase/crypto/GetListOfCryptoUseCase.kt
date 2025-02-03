package com.sass.domain.usecase.crypto

import com.sass.domain.model.currency.CryptoCurrency
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.utils.Response
import com.sass.domain.repository.CurrencyRepository
import com.sass.domain.usecase.BaseUseCaseFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListOfCryptoUseCase
    @Inject
    constructor(
        private val repository: CurrencyRepository,
    ) : BaseUseCaseFlow<CurrencyType, Response<List<CryptoCurrency>>>() {
        override suspend fun invoke(input: CurrencyType): Flow<Response<List<CryptoCurrency>>> = repository.allCurrencies(input)
    }
