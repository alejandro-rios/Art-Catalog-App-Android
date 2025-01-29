package com.alejandrorios.art_catalog_app.view_models

import app.cash.turbine.test
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.data.utils.AppDispatchers
import com.alejandrorios.art_catalog_app.data.utils.CallResponse
import com.alejandrorios.art_catalog_app.data.utils.NetworkErrorException
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import com.alejandrorios.art_catalog_app.ui.screens.artwork_detail.ArtworkDetailViewModel
import com.alejandrorios.art_catalog_app.utils.MainDispatcherRule
import com.alejandrorios.art_catalog_app.utils.MockKableTest
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArtworkDetailViewModelTest : MockKableTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var repositoryMock: ArtRepository

    @MockK
    lateinit var daoMock: ArtworksDao

    private lateinit var viewModel: ArtworkDetailViewModel
    private val dispatcher = mockk<AppDispatchers>(relaxed = true) {
        every { io } returns UnconfinedTestDispatcher()
    }
    private val result = mockk<Artwork>(relaxed = true)
    private val resultDetail = mockk<ArtworkDetail>(relaxed = true) {
        every { mapAsArtwork() } returns result
    }

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = ArtworkDetailViewModel(
            artRepository = repositoryMock,
            dao = daoMock,
            dispatcher = dispatcher,
            enableDelay = false
        )
    }

    @Test
    fun `Should set update artworkDetails when getArtworkDetails is invoked`() = runTest {
        coEvery {
            repositoryMock.getArtworkDetails(any())
        } answers {
            CallResponse.success(resultDetail)
        }

        coEvery {
            daoMock.findArtworkById(any())
        } returns flow {
            emit(result)
        }

        viewModel.uiState.test {
            viewModel.getArtworkDetail(artworkId = 123865)
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()
            loadingStep.errorMessage.shouldBeNull()

            val resultDetailStep = awaitItem()

            resultDetailStep.isLoading.shouldBeTrue()
            resultDetailStep.isSaved.shouldBeFalse()
            resultDetailStep.errorMessage.shouldBeNull()
            resultDetailStep.artworkDetails shouldBeEqualTo resultDetail

            val resultStep = awaitItem()

            resultStep.isLoading.shouldBeFalse()
            resultStep.isSaved.shouldBeTrue()
        }

        coVerify(exactly = 1) {
            repositoryMock.getArtworkDetails(artworkId = 123865)
            daoMock.findArtworkById(artworkId = 123865)
        }

        confirmVerified(repositoryMock, daoMock)
    }

    @Test
    fun `Should set save locally when saveArtwork is invoked`() = runTest {
        coEvery {
            repositoryMock.getArtworkDetails(any())
        } answers {
            CallResponse.success(resultDetail)
        }

        coEvery {
            daoMock.findArtworkById(any())
        } returns flow {
            emit(null)
        }

        coJustRun {
            daoMock.insertArtwork(any())
        }

        viewModel.getArtworkDetail(artworkId = 123865)

        viewModel.uiState.test {
            awaitItem()

            viewModel.saveArtwork()

            val resultSaveStep = awaitItem()

            resultSaveStep.isSaved.shouldBeTrue()
        }

        coVerify(exactly = 1) {
            repositoryMock.getArtworkDetails(artworkId = 123865)
            daoMock.findArtworkById(artworkId = 123865)
            daoMock.insertArtwork(result)
        }

        confirmVerified(repositoryMock, daoMock)
    }

    @Test
    fun `Should delete artwork locally when removeArtwork is invoked`() = runTest {
        coEvery {
            repositoryMock.getArtworkDetails(any())
        } answers {
            CallResponse.success(resultDetail)
        }

        coEvery {
            daoMock.findArtworkById(any())
        } returns flow {
            emit(result)
        }

        coJustRun { daoMock.deleteArtwork(any()) }

        viewModel.getArtworkDetail(artworkId = 123865)

        viewModel.uiState.test {
            val validationStep = awaitItem()

            validationStep.isSaved.shouldBeTrue()

            viewModel.removeArtwork()

            val resultSaveStep = awaitItem()

            resultSaveStep.isSaved.shouldBeFalse()
        }

        coVerify(exactly = 1) {
            repositoryMock.getArtworkDetails(artworkId = 123865)
            daoMock.findArtworkById(artworkId = 123865)
            daoMock.deleteArtwork(result)
        }

        confirmVerified(repositoryMock, daoMock)
    }

    @Test
    fun `Should set update error message when getArtworkDetails is invoked and fails`() = runTest {
        coEvery {
            repositoryMock.getArtworkDetails(any())
        } answers {
            CallResponse.failure<NetworkErrorException>(NetworkErrorException("An error occurred"))
        }

        viewModel.uiState.test {
            viewModel.getArtworkDetail(artworkId = 0)
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()
            loadingStep.errorMessage.shouldBeNull()

            val resultStep = awaitItem()
            resultStep.isLoading.shouldBeFalse()
            resultStep.errorMessage shouldBeEqualTo "An error occurred"
            resultStep.artworkDetails.shouldBeNull()
        }

        coVerify(exactly = 1) {
            repositoryMock.getArtworkDetails(artworkId = 0)
        }

        confirmVerified(repositoryMock)
    }
}


