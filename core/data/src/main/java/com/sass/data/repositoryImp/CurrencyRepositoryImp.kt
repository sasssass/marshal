package com.sass.data.repositoryImp

import com.sass.data.network.CryptoCurrencyAPI
import com.sass.data.network.CurrencyExchangeAPI
import com.sass.data.network.utils.requestCallResponse
import com.sass.domain.model.currency.CryptoCurrency
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.currency.Links
import com.sass.domain.model.utils.Response
import com.sass.domain.model.utils.cast
import com.sass.domain.model.utils.concat
import com.sass.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CurrencyRepositoryImp
    @Inject
    constructor(
        private val api: CryptoCurrencyAPI,
        private val apiExchange: CurrencyExchangeAPI,
    ) : CurrencyRepository {
        override fun allCurrencies(currencyType: CurrencyType): Flow<Response<List<CryptoCurrency>>> =
            flow {
                emit(Response.Loading())

                val exchangeRate: Double? =
                    if (currencyType == CurrencyType.SwedishKrona) {
                        val usdToSwedish = requestCallResponse { apiExchange.getExchangeRates() }

                        if (usdToSwedish.isSuccess) {
                            usdToSwedish.getOrThrow().rates.SEK
                        } else {
                            emit(Response.Error(Exception(usdToSwedish.exceptionOrNull())))
                            null
                        }
                    } else {
                        1.0
                    }

                exchangeRate?.let { rate ->
                    val rawResponse = requestCallResponse { api.allCurrencies() }
                    val response =
                        cast(rawResponse) {
                            it.data.map {
                                CryptoCurrency(
                                    name = it.name,
                                    id = it.id,
                                    code = it.symbol,
                                    icon = "https://s2.coinmarketcap.com/static/img/coins/64x64/${it.id}.png",
                                    value = it.quote.usd.price * rate,
                                    totalSupply = it.totalSupply,
                                    marketCap = it.quote.usd.marketCap,
                                    valueType = currencyType,
                                    links = null,
                                )
                            }
                        }

                    emit(response)
                }
            }.flowOn(IO)

        override fun getCurrency(
            currencyType: CurrencyType,
            id: String,
        ): Flow<Response<CryptoCurrency>> =
            flow {
                emit(Response.Loading())

                val exchangeRate: Double? =
                    if (currencyType == CurrencyType.SwedishKrona) {
                        val usdToSwedish = requestCallResponse { apiExchange.getExchangeRates() }

                        if (usdToSwedish.isSuccess) {
                            usdToSwedish.getOrThrow().rates.SEK
                        } else {
                            emit(Response.Error(Exception(usdToSwedish.exceptionOrNull())))
                            null
                        }
                    } else {
                        1.0
                    }

                exchangeRate?.let {
                    val rawResponse = requestCallResponse { api.getCurrency(id) }
                    val rawResponseLink = requestCallResponse { api.getCurrencyInfo(id) }
                    val response =
                        cast(rawResponse) {
                            it.data[id]!!.run {
                                val cryptoCurrency =
                                    CryptoCurrency(
                                        name = name,
                                        id = id.toInt(),
                                        code = symbol,
                                        icon = "https://s2.coinmarketcap.com/static/img/coins/64x64/$id.png",
                                        value = quote.usd.price * exchangeRate,
                                        totalSupply = totalSupply,
                                        marketCap = quote.usd.marketCap,
                                        valueType = currencyType,
                                        links = null,
                                    )
                                cryptoCurrency
                            }
                        }

                    val links =
                        cast(rawResponseLink) {
                            it.data[id]!!.run {
                                Links(
                                    reddit = urls.reddit.firstOrNull(),
                                    twitter = urls.twitter.firstOrNull(),
                                    github = urls.sourceCode.firstOrNull(),
                                    facebook = urls.facebook.firstOrNull(),
                                    website = urls.website.firstOrNull(),
                                )
                            }
                        }
                    val finalResponse =
                        concat(links, response) { _links, _response ->
                            _response.copy(
                                links = _links,
                            )
                        }
                    emit(finalResponse)
                }
            }.flowOn(IO)
    }
