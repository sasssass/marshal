package com.sass.data.model

import com.google.gson.annotations.SerializedName

data class CryptocurrenciesDto(
    @SerializedName("data") val data: List<CryptocurrenciesDtoData>,
    @SerializedName("status") val status: Status,
)

data class CryptocurrenciesDtoData(
    @SerializedName("circulating_supply") val circulatingSupply: Double,
    @SerializedName("cmc_rank") val cmcRank: Int,
    @SerializedName("date_added") val dateAdded: String,
    @SerializedName("id") val id: Int,
    @SerializedName("infinite_supply") val infiniteSupply: Boolean,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("max_supply") val maxSupply: Double,
    @SerializedName("name") val name: String,
    @SerializedName("num_market_pairs") val numMarketPairs: Int,
    @SerializedName("platform") val platform: Platform,
    @SerializedName("quote") val quote: Quote,
    @SerializedName("self_reported_circulating_supply") val selfReportedCirculatingSupply: Double,
    @SerializedName("self_reported_market_cap") val selfReportedMarketCap: Double,
    @SerializedName("slug") val slug: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("total_supply") val totalSupply: Double,
    @SerializedName("tvl_ratio") val tvlRatio: Double,
)

data class Platform(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("token_address") val tokenAddress: String,
)

data class Quote(
    @SerializedName("USD") val usd: USD,
)

data class USD(
    @SerializedName("fully_diluted_market_cap") val fullyDilutedMarketCap: Double,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("market_cap") val marketCap: Double,
    @SerializedName("market_cap_dominance") val marketCapDominance: Double,
    @SerializedName("percent_change_1h") val percentChange1h: Double,
    @SerializedName("percent_change_24h") val percentChange24h: Double,
    @SerializedName("percent_change_30d") val percentChange30d: Double,
    @SerializedName("percent_change_60d") val percentChange60d: Double,
    @SerializedName("percent_change_7d") val percentChange7d: Double,
    @SerializedName("percent_change_90d") val percentChange90d: Double,
    @SerializedName("price") val price: Double,
    @SerializedName("tvl") val tvl: Double,
    @SerializedName("volume_24h") val volume24h: Double,
    @SerializedName("volume_change_24h") val volumeChange24h: Double,
)
