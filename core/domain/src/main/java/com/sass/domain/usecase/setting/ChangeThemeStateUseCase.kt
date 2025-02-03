package com.sass.domain.usecase.setting

import com.sass.domain.model.setting.ThemeType
import com.sass.domain.repository.DataSourceRepository
import com.sass.domain.usecase.BaseUseCase
import javax.inject.Inject

class ChangeThemeStateUseCase
    @Inject
    constructor(
        private val repository: DataSourceRepository,
    ) : BaseUseCase<ThemeType, Unit>() {
        override suspend fun invoke(input: ThemeType) {
            repository.setDarkTheme(input)
        }
    }
