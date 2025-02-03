package com.sass.domain.model.utils

sealed class Response<T> {
    data class Data<T>(
        val data: T,
    ) : Response<T>()

    data class Error<T>(
        val error: Exception,
    ) : Response<T>()

    data class Loading<T>(
        val loadingMessage: String = "Loading",
    ) : Response<T>()

    class IDLE<T> : Response<T>()
}

fun <T, OutPut> cast(
    response: Result<T>,
    mapper: (T) -> OutPut,
): Response<OutPut> {
    if (response.isSuccess) {
        try {
            val casted = mapper(response.getOrThrow())
            return Response.Data(casted)
        } catch (e: Exception) {
            return Response.Error(e)
        }
    } else {
        return Response.Error(Exception(response.exceptionOrNull()!!))
    }
}

fun <X, Y, OutPut> concat(
    response1: Response<X>,
    response2: Response<Y>,
    zipper: (X, Y) -> OutPut,
): Response<OutPut> {
    if (response1 is Response.Data && response2 is Response.Data) {
        return Response.Data(zipper(response1.data, response2.data))
    } else {
        return Response.Error(Exception("Failed to concat"))
    }
}
