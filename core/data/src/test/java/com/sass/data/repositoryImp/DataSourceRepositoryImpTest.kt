package com.sass.data.repositoryImp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import app.cash.turbine.test
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DataSourceRepositoryImpTest {
    @Test
    fun `we can change theme type`() =
        runTest {
            val dataStore: DataStore<Preferences> = mock()
            val preferences: Preferences = mock()
            val themeTypeKey = stringPreferencesKey(THEME_TYPE)

            whenever(preferences.contains(themeTypeKey)).thenReturn(true)
            whenever(preferences[themeTypeKey]).thenReturn(ThemeType.Default.name)

            whenever(dataStore.data).thenReturn(flowOf(preferences))
            whenever(dataStore.edit(any())).then {
                whenever(preferences[themeTypeKey]).thenReturn(ThemeType.Dark.name)
            }

            val dataSourceRepository = DataSourceRepositoryImp(dataStore)

            dataSourceRepository.setDarkTheme(ThemeType.Dark)

            dataSourceRepository.observeDarkTheme().test {
                val response = awaitItem()
                assert(response == ThemeType.Dark)
                awaitComplete()
            }
        }

    @Test
    fun `we can change currency type`() =
        runTest {
            val dataStore: DataStore<Preferences> = mock()
            val preferences: Preferences = mock()
            val currency = stringPreferencesKey(CURRENCY_TYPE)

            whenever(preferences.contains(currency)).thenReturn(true)
            whenever(preferences[currency]).thenReturn(CurrencyType.SwedishKrona.name)

            whenever(dataStore.data).thenReturn(flowOf(preferences))
            whenever(dataStore.edit(any())).then {
                whenever(preferences[currency]).thenReturn(CurrencyType.USDollar.name)
            }

            val dataSourceRepository = DataSourceRepositoryImp(dataStore)

            dataSourceRepository.changeCurrencyType(CurrencyType.USDollar)

            dataSourceRepository.observeCurrencyType().test {
                val response = awaitItem()
                assert(response == CurrencyType.USDollar)
                awaitComplete()
            }
        }
}
