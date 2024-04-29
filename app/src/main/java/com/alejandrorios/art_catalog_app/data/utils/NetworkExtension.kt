package com.alejandrorios.art_catalog_app.data.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

inline fun <param, result> Call<param>.handleResponse(
    continuation: Continuation<CallResponse<result>>,
    crossinline transformation: (predicate: param) -> result
) {
    enqueue(object : Callback<param> {
        override fun onResponse(call: Call<param>, response: Response<param>) {
            with(continuation) {
                if (!response.isSuccessful || response.errorBody() != null) {
                    resume(
                        CallResponse.failure<NetworkErrorException>(
                            NetworkErrorException(
                                response.errorBody()?.string() ?: "Response body is null"
                            )
                        )
                    )
                    return
                }

                try {
                    resume(CallResponse.success(transformation(response.body()!!)))
                } catch (e: Exception) {
                    resume(CallResponse.failure<Throwable>(Throwable(e.localizedMessage ?: "Unknown error")))
                }
            }
        }

        override fun onFailure(call: Call<param>, t: Throwable) {
            println(t.fillInStackTrace())
            continuation.resume(CallResponse.failure<NetworkErrorException>(t.fillInStackTrace()))
        }
    })
}
