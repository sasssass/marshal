package com.sass.domain.usecase.setting

import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.repository.DataSourceRepository
import com.sass.domain.usecase.BaseUseCaseFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedCurrencyTypeUseCase
    @Inject
    constructor(
        private val repo: DataSourceRepository,
    ) : BaseUseCaseFlow<Unit, CurrencyType>() {
        override suspend fun invoke(input: Unit): Flow<CurrencyType> = repo.observeCurrencyType()
    }
