package com.sass.data.repositoryImp

import app.cash.turbine.test
import com.sass.data.model.CurrencyExchangeRateDto
import com.sass.data.model.RatesDto
import com.sass.data.network.CryptoCurrencyAPI
import com.sass.data.network.CurrencyExchangeAPI
import com.sass.data.utils.readJsonFile
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.utils.Response
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyRepositoryImpTest {
    private val mockWebServer = MockWebServer()

    @Before
    fun before() {
        mockWebServer.start()
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun `repo can get list of all currencies`() =
        runTest {
            val api = fakeCryptoCurrencyAPI()

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        readJsonFile("list_of_currencies.json"),
                    ),
            )

            val currencyExchangeAPI: CurrencyExchangeAPI = mock()
            whenever(currencyExchangeAPI.getExchangeRates()).thenReturn(
                fakeCurrencyExchangeRateDto(),
            )
            val repo = CurrencyRepositoryImp(api, currencyExchangeAPI)
            repo.allCurrencies(CurrencyType.USDollar).test {
                val responseLoading = awaitItem()
                assert(responseLoading is Response.Loading)

                val response = awaitItem()
                (response as Response.Data).let {
                    assert(it.data.first().name == "Bitcoin")
                }
                awaitComplete()
            }
        }

    @Test
    fun `repo can get one specific currency`() =
        runTest {
            val api = fakeCryptoCurrencyAPI()

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        readJsonFile("terracoin.json"),
                    ),
            )
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        readJsonFile("terracoin_link.json"),
                    ),
            )
            val currencyExchangeAPI: CurrencyExchangeAPI = mock()
            whenever(currencyExchangeAPI.getExchangeRates()).thenReturn(
                fakeCurrencyExchangeRateDto(),
            )
            val repo = CurrencyRepositoryImp(api, currencyExchangeAPI)
            repo.getCurrency(CurrencyType.USDollar, "4").test {
                val responseLoading = awaitItem()
                assert(responseLoading is Response.Loading)

                val response = awaitItem()
                (response as Response.Data).let {
                    assert(it.data.links!!.twitter == "https://twitter.com/Terracoin_TRC")
                    assert(it.data.name == "Terracoin")
                }
                awaitComplete()
            }
        }

    fun fakeCryptoCurrencyAPI() =
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(CryptoCurrencyAPI::class.java)

    fun fakeCurrencyExchangeRateDto(): CurrencyExchangeRateDto =
        CurrencyExchangeRateDto(
            amount = 1.0,
            base = "",
            date = "",
            rates =
                RatesDto(
                    SEK = 10.0,
                ),
        )
}
