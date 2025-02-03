package com.sass.domain.usecase.setting

import com.sass.domain.model.setting.ThemeType
import com.sass.domain.repository.DataSourceRepository
import com.sass.domain.usecase.BaseUseCaseFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeStateUseCase
    @Inject
    constructor(
        private val repository: DataSourceRepository,
    ) : BaseUseCaseFlow<Unit, ThemeType>() {
        override suspend fun invoke(input: Unit): Flow<ThemeType> = repository.observeDarkTheme()
    }
