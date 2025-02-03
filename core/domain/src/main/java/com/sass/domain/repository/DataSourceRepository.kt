package com.sass.domain.repository

import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {
    suspend fun observeCurrencyType(): Flow<CurrencyType>

    suspend fun changeCurrencyType(currencyType: CurrencyType)

    suspend fun observeDarkTheme(): Flow<ThemeType>

    suspend fun setDarkTheme(themType: ThemeType)
}
