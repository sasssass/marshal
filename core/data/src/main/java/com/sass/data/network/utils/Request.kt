package com.sass.data.network.utils

import android.util.Log
import retrofit2.HttpException

suspend fun <T> requestCallResponse(request: suspend () -> T): Result<T> {
    try {
        val response = request()
        return Result.success(response)
    } catch (e: Exception) {
        Log.e("ERROR", e.message.toString())
        return Result.failure(
            Exception(
                when (e) {
                    is HttpException -> {
                        if (e.response()?.code() == 429) {
                            "Too many request to the server\n please try again later"
                        } else {
                            ""
                        }
                    }

                    else -> "Error"
                },
            ),
        )
    }
}
