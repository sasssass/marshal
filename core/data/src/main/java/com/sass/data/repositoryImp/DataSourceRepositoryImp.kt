package com.sass.data.repositoryImp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import com.sass.domain.repository.DataSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val CURRENCY_TYPE = "currency_type"
private const val THEME_TYPE = "theme_type"

class DataSourceRepositoryImp
    @Inject
    constructor(
        private val context: Application,
    ) : DataSourceRepository {
        private val Context.dataStore by preferencesDataStore(name = "marshal_setting")

        override suspend fun observeCurrencyType(): Flow<CurrencyType> =
            context.dataStore.data.map {
                val currencyTypeInString = it[stringPreferencesKey(CURRENCY_TYPE)]
                when (currencyTypeInString) {
                    CurrencyType.SwedishKrona.code -> CurrencyType.SwedishKrona
                    else -> CurrencyType.USDollar
                }
            }

        override suspend fun changeCurrencyType(currencyType: CurrencyType) {
            context.dataStore.edit {
                it[stringPreferencesKey(CURRENCY_TYPE)] = currencyType.code
            }
        }

        override suspend fun observeDarkTheme(): Flow<ThemeType> =
            context.dataStore.data.map {
                val themeType = it[stringPreferencesKey(THEME_TYPE)]
                when (themeType) {
                    ThemeType.Dark.name -> ThemeType.Dark
                    ThemeType.Light.name -> ThemeType.Light
                    else -> ThemeType.Default
                }
            }

        override suspend fun setDarkTheme(themType: ThemeType) {
            context.dataStore.edit {
                it[stringPreferencesKey(THEME_TYPE)] = themType.name
            }
        }
    }
