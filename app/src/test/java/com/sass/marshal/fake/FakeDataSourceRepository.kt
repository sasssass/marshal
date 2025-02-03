package com.sass.marshal.fake

import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import com.sass.domain.repository.DataSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeDataSourceRepository : DataSourceRepository {
    private var type: MutableStateFlow<CurrencyType> =
        MutableStateFlow(CurrencyType.USDollar)

    private var theme: MutableStateFlow<ThemeType> =
        MutableStateFlow(ThemeType.Default)

    override suspend fun observeCurrencyType(): Flow<CurrencyType> = type.asStateFlow()

    override suspend fun changeCurrencyType(currencyType: CurrencyType) {
        type.update {
            currencyType
        }
    }

    override suspend fun observeDarkTheme(): Flow<ThemeType> = theme.asStateFlow()

    override suspend fun setDarkTheme(themType: ThemeType) {
        theme.update {
            themType
        }
    }
}
