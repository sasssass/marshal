package com.sass.marshal.viewModel

import app.cash.turbine.test
import com.sass.domain.usecase.crypto.GetListOfCryptoUseCase
import com.sass.domain.usecase.setting.GetSavedCurrencyTypeUseCase
import com.sass.marshal.fake.FakeCurrencyRepository
import com.sass.marshal.fake.FakeDataSourceRepository
import com.sass.marshal.ui.screen.listOfCrypto.ListOfCryptoScreenViewModel
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
class ListOfCryptoScreenViewModelTest {
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
    fun `view model can get list of currencies and then it can search some of them`() =
        runTest {
            val viewModel =
                ListOfCryptoScreenViewModel(
                    GetListOfCryptoUseCase(FakeCurrencyRepository()),
                    GetSavedCurrencyTypeUseCase(FakeDataSourceRepository()),
                )
            viewModel.uiState.test {
                advanceUntilIdle()
                awaitItem() // first collect idle state
                val responseLoading = awaitItem()
                assert(responseLoading.loading)

                val response = awaitItem()
                assert(response.cryptoCurrencyList.isNotEmpty())

                viewModel.search("test")
                val responseSearched = awaitItem()
                assert(responseSearched.cryptoCurrencyList.isEmpty())
            }
        }
}
