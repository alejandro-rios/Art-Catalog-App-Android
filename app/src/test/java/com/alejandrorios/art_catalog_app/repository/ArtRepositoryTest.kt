package com.alejandrorios.art_catalog_app.repository

import com.alejandrorios.art_catalog_app.data.models.APIArtworkDetailResult
import com.alejandrorios.art_catalog_app.data.models.APIArtworkResults
import com.alejandrorios.art_catalog_app.data.network.ArtAPIService
import com.alejandrorios.art_catalog_app.data.repository.ArtRepositoryImpl
import com.alejandrorios.art_catalog_app.data.utils.CallResponse
import com.alejandrorios.art_catalog_app.data.utils.NetworkErrorException
import com.alejandrorios.art_catalog_app.utils.MockKableTest
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtRepositoryTest : MockKableTest {

    @MockK
    lateinit var artAPIService: ArtAPIService

    private lateinit var repository: ArtRepositoryImpl

    @Before
    override fun setUp() {
        super.setUp()
        repository = ArtRepositoryImpl(artAPIService)
    }

    @Test
    fun `given repository when getArtworks it's called then should get a APIArtworkResults`() {
        val call = mockk<Call<APIArtworkResults>>()
        val responseData = mockk<Response<APIArtworkResults>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIArtworkResults>>()

        coEvery {
            artAPIService.getArtworks(1, 10)
        } returns call

        coEvery {
            responseData.isSuccessful
        } returns true

        coEvery {
            responseData.errorBody()
        } returns null

        coEvery {
            responseData.body()
        } returns mockk(relaxed = true)

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onResponse(call, responseData)
        }

        runBlocking {
            repository.getArtworks(1, 10)
        } shouldBeInstanceOf CallResponse.Success::class

        coVerify {
            artAPIService.getArtworks(1, 10)
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when getArtworkDetails it's called then should get a APIArtworkDetailResult`() {
        val call = mockk<Call<APIArtworkDetailResult>>()
        val responseData = mockk<Response<APIArtworkDetailResult>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIArtworkDetailResult>>()

        coEvery {
            artAPIService.getArtworkDetails(54634)
        } returns call

        coEvery {
            responseData.isSuccessful
        } returns true

        coEvery {
            responseData.errorBody()
        } returns null

        coEvery {
            responseData.body()
        } returns mockk(relaxed = true)

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onResponse(call, responseData)
        }

        runBlocking {
            repository.getArtworkDetails(54634)
        } shouldBeInstanceOf CallResponse.Success::class

        coVerify {
            artAPIService.getArtworkDetails(54634)
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when getArtworks it's called and fails then should throw a NetworkErrorException`() {
        val call = mockk<Call<APIArtworkResults>>()
        val responseData = mockk<Response<APIArtworkResults>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIArtworkResults>>()

        coEvery {
            artAPIService.getArtworks( 10, 0)
        } returns call

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onFailure(call, NetworkErrorException("some message"))
        }

        coEvery {
            responseData.errorBody()
        } throws NetworkErrorException("some message")

        runBlocking {
            repository.getArtworks( 10, 0)
        } shouldBeInstanceOf CallResponse.Failure::class

        coVerify {
            artAPIService.getArtworks( 10, 0)
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when getArtworkDetails it's called and fails then should throw a NetworkErrorException`() {
        val call = mockk<Call<APIArtworkDetailResult>>()
        val responseData = mockk<Response<APIArtworkDetailResult>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIArtworkDetailResult>>()

        coEvery {
            artAPIService.getArtworkDetails(98765)
        } returns call

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onFailure(call, NetworkErrorException("some message"))
        }

        coEvery {
            responseData.errorBody()
        } throws NetworkErrorException("some message")

        runBlocking {
            repository.getArtworkDetails(98765)
        } shouldBeInstanceOf CallResponse.Failure::class

        coVerify {
            artAPIService.getArtworkDetails(98765)
            call.enqueue(any())
        }
    }
}
