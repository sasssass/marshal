package com.sass.marshal.viewModel

import app.cash.turbine.test
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.setting.ThemeType
import com.sass.domain.usecase.setting.ChangeSavedCurrencyTypeUseCase
import com.sass.domain.usecase.setting.ChangeThemeStateUseCase
import com.sass.domain.usecase.setting.GetSavedCurrencyTypeUseCase
import com.sass.domain.usecase.setting.GetThemeStateUseCase
import com.sass.marshal.fake.FakeDataSourceRepository
import com.sass.marshal.ui.screen.setting.SettingScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `see if changing theme works fine`() =
        runTest {
            val repo = FakeDataSourceRepository()
            val viewModel =
                SettingScreenViewModel(
                    GetSavedCurrencyTypeUseCase(repo),
                    ChangeSavedCurrencyTypeUseCase(repo),
                    GetThemeStateUseCase(repo),
                    ChangeThemeStateUseCase(repo),
                )

            viewModel.uiState.test {
                advanceUntilIdle()
                val first = awaitItem()
                assert(first.themeType == ThemeType.Default)

                viewModel.changeThemeState(ThemeType.Dark)
                val second = awaitItem()
                assert(second.themeType == ThemeType.Dark)
            }
        }

    @Test
    fun `see if changing currency type works fine`() =
        runTest {
            val repo = FakeDataSourceRepository()
            val viewModel =
                SettingScreenViewModel(
                    GetSavedCurrencyTypeUseCase(repo),
                    ChangeSavedCurrencyTypeUseCase(repo),
                    GetThemeStateUseCase(repo),
                    ChangeThemeStateUseCase(repo),
                )

            viewModel.uiState.test {
                advanceUntilIdle()
                val first = awaitItem()
                assert(first.currencyType == CurrencyType.USDollar)

                viewModel.switchCurrencyType(CurrencyType.SwedishKrona)
                val second = awaitItem()
                assert(second.currencyType == CurrencyType.SwedishKrona)
            }
        }
}
