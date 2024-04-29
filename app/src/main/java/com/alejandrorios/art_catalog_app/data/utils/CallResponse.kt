package com.alejandrorios.art_catalog_app.data.utils

sealed class CallResponse<out T> {
    data class Success<out T>(val data: T) : CallResponse<T>()
    data class Failure(val t: Throwable) : CallResponse<Nothing>()

    companion object {
        fun <T> success(data: T): CallResponse<T> = Success(data)
        fun <T> failure(t: Throwable): CallResponse<Nothing> = Failure(t)
    }
}
