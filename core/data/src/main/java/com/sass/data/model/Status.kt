package com.sass.data.model

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("credit_count") val creditCount: Int,
    @SerializedName("elapsed") val elapsed: Int,
    @SerializedName("error_code") val errorCode: Int,
    @SerializedName("error_message") val errorMessage: Any?,
    @SerializedName("notice") val notice: Any?,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("total_count") val totalCount: Int,
)
