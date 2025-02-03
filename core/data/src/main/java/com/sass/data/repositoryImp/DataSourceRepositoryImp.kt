package com.sass.data.repositoryImp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import com.sass.domain.repository.DataSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val CURRENCY_TYPE = "currency_type"
const val THEME_TYPE = "theme_type"

class DataSourceRepositoryImp
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : DataSourceRepository {
        override suspend fun observeCurrencyType(): Flow<CurrencyType> =
            dataStore.data.map {
                val currencyTypeInString = it[stringPreferencesKey(CURRENCY_TYPE)]
                when (currencyTypeInString) {
                    CurrencyType.SwedishKrona.code -> CurrencyType.SwedishKrona
                    else -> CurrencyType.USDollar
                }
            }

        override suspend fun changeCurrencyType(currencyType: CurrencyType) {
            dataStore.edit {
                it[stringPreferencesKey(CURRENCY_TYPE)] = currencyType.code
            }
        }

        override suspend fun observeDarkTheme(): Flow<ThemeType> =
            dataStore.data.map {
                val themeType = it[stringPreferencesKey(THEME_TYPE)]
                when (themeType) {
                    ThemeType.Dark.name -> ThemeType.Dark
                    ThemeType.Light.name -> ThemeType.Light
                    else -> ThemeType.Default
                }
            }

        override suspend fun setDarkTheme(themType: ThemeType) {
            dataStore.edit {
                it[stringPreferencesKey(THEME_TYPE)] = themType.name
            }
        }
    }
