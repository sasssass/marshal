package com.sass.marshal.fake

import com.sass.domain.model.currency.CryptoCurrency
import com.sass.domain.model.currency.CurrencyType
import com.sass.domain.model.currency.Links
import com.sass.domain.model.utils.Response
import com.sass.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCurrencyRepository : CurrencyRepository {
    override fun allCurrencies(currencyType: CurrencyType): Flow<Response<List<CryptoCurrency>>> =
        flow {
            emit(Response.Loading())
            emit(
                Response.Data(
                    listOf(
                        CryptoCurrency(
                            name = "Bitcoin",
                            id = 1,
                            code = "BTC",
                            icon = "",
                            value = 9.0,
                            marketCap = 10.0,
                            totalSupply = 11.0,
                            valueType = CurrencyType.USDollar,
                            links =
                                Links(
                                    twitter = "x.com",
                                    github = null,
                                    facebook = null,
                                    website = null,
                                    reddit = null,
                                ),
                        ),
                    ),
                ),
            )
        }

    override fun getCurrency(
        currencyType: CurrencyType,
        id: String,
    ): Flow<Response<CryptoCurrency>> =
        flow {
            emit(Response.Loading())
            emit(
                Response.Data(
                    CryptoCurrency(
                        name = "Bitcoin",
                        id = 1,
                        code = "BTC",
                        icon = "",
                        value = 9.0,
                        marketCap = 10.0,
                        totalSupply = 11.0,
                        valueType = CurrencyType.USDollar,
                        links =
                            Links(
                                twitter = "x.com",
                                github = null,
                                facebook = null,
                                website = null,
                                reddit = null,
                            ),
                    ),
                ),
            )
        }
}
