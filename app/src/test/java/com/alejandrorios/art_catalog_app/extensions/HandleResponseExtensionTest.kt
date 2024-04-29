package com.alejandrorios.art_catalog_app.extensions

import com.alejandrorios.art_catalog_app.data.utils.CallResponse
import com.alejandrorios.art_catalog_app.data.utils.NetworkErrorException
import com.alejandrorios.art_catalog_app.data.utils.handleResponse
import com.alejandrorios.art_catalog_app.utils.MockKableTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.Locale
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class HandleResponseExtensionTest : MockKableTest {

    private val continuation = mockk<Continuation<CallResponse<String>>>(relaxed = true)

    @Test
    fun `given continuation when handleResponse it's invoked then should get a String`() = runBlocking {
        val call = mockk<Call<String>>()
        val responseBody = "Response body"

        every { call.enqueue(any()) } answers {
            val callback = arg<retrofit2.Callback<String>>(0)
            callback.onResponse(call, Response.success(responseBody))
        }

        call.handleResponse(continuation) { it.uppercase(Locale.getDefault()) }

        verify {
            continuation.resume(
                CallResponse.success("RESPONSE BODY")
            )
        }
    }

    @Test
    fun `given continuation when handleResponse it's invoked with errorBody then should get a NetworkErrorException`() =
        runBlocking {
            val call = mockk<Call<String>>()
            val errorBody = "error response"

            every { call.enqueue(any()) } answers {
                val callback = arg<retrofit2.Callback<String>>(0)
                callback.onResponse(call, Response.error(404, errorBody.toResponseBody("text/plain".toMediaTypeOrNull())))
            }

            call.handleResponse(continuation) { it.uppercase(Locale.getDefault()) }

            verify {
                continuation.resume(
                    match {
                        it is CallResponse.Failure &&
                                it.t is NetworkErrorException &&
                                it.t.message == errorBody
                    }
                )
            }
        }

    @Test
    fun `given continuation when handleResponse it's invoked with body as null then should get a Throwable`() = runBlocking {
        val call = mockk<Call<String>>()

        every { call.enqueue(any()) } answers {
            val callback = arg<retrofit2.Callback<String>>(0)
            callback.onFailure(call, Throwable("Unknown error"))
        }

        call.handleResponse(continuation) { it.uppercase(Locale.getDefault()) }

        verify {
            continuation.resume(
                match {
                    it is CallResponse.Failure && it.t.message == "Unknown error"
                }
            )
        }
    }
}

