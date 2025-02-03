package com.sass.domain.usecase.setting

import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.repository.DataSourceRepository
import com.sass.domain.usecase.BaseUseCase
import javax.inject.Inject

class ChangeSavedCurrencyTypeUseCase
    @Inject
    constructor(
        private val repo: DataSourceRepository,
    ) : BaseUseCase<CurrencyType, Unit>() {
        override suspend fun invoke(input: CurrencyType) {
            repo.changeCurrencyType(input)
        }
    }
