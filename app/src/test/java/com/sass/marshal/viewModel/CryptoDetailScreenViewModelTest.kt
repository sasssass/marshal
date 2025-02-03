package com.sass.marshal.viewModel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sass.domain.usecase.crypto.GetCryptoUseCase
import com.sass.domain.usecase.setting.GetSavedCurrencyTypeUseCase
import com.sass.marshal.fake.FakeCurrencyRepository
import com.sass.marshal.fake.FakeDataSourceRepository
import com.sass.marshal.ui.screen.cryptoDetail.CryptoDetailScreenViewModel
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
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CryptoDetailScreenViewModelTest {
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
    fun `view model can get specific currency`() =
        runTest {
            val savedStateHandle: SavedStateHandle = mock()
            whenever(savedStateHandle.contains("1")).thenReturn(true)
            whenever(savedStateHandle.get<String>(any())).thenReturn("1")

            val viewModel =
                CryptoDetailScreenViewModel(
                    savedStateHandle,
                    GetCryptoUseCase(FakeCurrencyRepository()),
                    GetSavedCurrencyTypeUseCase(FakeDataSourceRepository()),
                )
            viewModel.uiState.test {
                advanceUntilIdle()
                awaitItem() // first collect idle state
                val responseLoading = awaitItem()
                assert(responseLoading.loading)

                val response = awaitItem()
                assert(response.cryptoCurrency!!.name == "Bitcoin")
            }
        }
}
