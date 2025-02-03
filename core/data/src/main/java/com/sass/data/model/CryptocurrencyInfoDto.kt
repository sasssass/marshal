package com.sass.data.model

import com.google.gson.annotations.SerializedName

data class CryptocurrencyInfoDto(
    @SerializedName("data") val data: Map<String, CryptocurrencyInfoDtoData>,
    @SerializedName("status") val status: Status,
)

data class CryptocurrencyInfoDtoData(
    @SerializedName("urls") val urls: Urls,
    @SerializedName("description") val description: String,
    @SerializedName("tags") val tags: List<String>,
)

data class Urls(
    @SerializedName("announcement") val announcement: List<String>,
    @SerializedName("chat") val chat: List<String>,
    @SerializedName("explorer") val explorer: List<String>,
    @SerializedName("facebook") val facebook: List<String>,
    @SerializedName("message_board") val messageBoard: List<String>,
    @SerializedName("reddit") val reddit: List<String>,
    @SerializedName("source_code") val sourceCode: List<String>,
    @SerializedName("technical_doc") val technicalDoc: List<String>,
    @SerializedName("twitter") val twitter: List<String>,
    @SerializedName("website") val website: List<String>,
)
