package com.alejandrorios.art_catalog_app.view_models

import app.cash.turbine.test
import com.alejandrorios.art_catalog_app.data.utils.CallResponse
import com.alejandrorios.art_catalog_app.data.utils.NetworkErrorException
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import com.alejandrorios.art_catalog_app.domain.models.ArtworkPaging
import com.alejandrorios.art_catalog_app.domain.models.ArtworkResults
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import com.alejandrorios.art_catalog_app.ui.screens.artworks.ArtworksViewModel
import com.alejandrorios.art_catalog_app.utils.MainDispatcherRule
import com.alejandrorios.art_catalog_app.utils.MockKableTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArtworksViewModelTest : MockKableTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var repositoryMock: ArtRepository

    private lateinit var viewModel: ArtworksViewModel
    private val result = mockk<ArtworkResults>(relaxed = true)
    private val moreResults = mockk<ArtworkResults>(relaxed = true)
    private val artworkList = mockk<List<Artwork>>(relaxed = true)
    private val moreArtworks = mockk<List<Artwork>>(relaxed = true)
    private val paging = ArtworkPaging(
        total = 50,
        totalPages = 20,
        offset = 0,
        limit = 10,
        currentPage = 1
    )
    private val pagingMore = paging.copy(currentPage = 2)

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = ArtworksViewModel(repositoryMock)
    }

    @Test
    fun `Should set update artworks when fetchArtworks is invoked`() = runTest {
        coEvery {
            repositoryMock.getArtworks(1)
        } answers {
            CallResponse.success(result)
        }

        every {
            result.pagination
        } returns paging

        every {
            result.data
        } returns artworkList

        viewModel.uiState.test {
            awaitItem()

            viewModel.fetchArtworks()
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()

            val resultStep = awaitItem()

            resultStep.isLoading.shouldBeFalse()
            resultStep.artworksCurrentPage shouldBeEqualTo 1
            resultStep.artworksTotalPages shouldBeEqualTo 20
            resultStep.artworks shouldBeEqualTo artworkList
        }
    }

    @Test
    fun `Should set update error message when fetchArtworks is invoked and fails`() = runTest {
        coEvery {
            repositoryMock.getArtworks(1)
        } answers {
            CallResponse.failure<NetworkErrorException>(NetworkErrorException("An error occurred"))
        }

        viewModel.uiState.test {
            awaitItem()

            viewModel.fetchArtworks()
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()

            val resultStep = awaitItem()

            resultStep.isLoading.shouldBeFalse()
            resultStep.artworksCurrentPage shouldBeEqualTo 1
            resultStep.artworksTotalPages shouldBeEqualTo 0
            resultStep.errorMessage shouldBeEqualTo "An error occurred"
            resultStep.artworks shouldBeEqualTo emptyList()
        }
    }

    @Test
    fun `Should set update more artworks when loadMoreResults is invoked`() = runTest {
        coEvery {
            repositoryMock.getArtworks(1)
        } answers {
            CallResponse.success(result)
        }

        every {
            result.pagination
        } returns paging

        every {
            result.data
        } returns artworkList

        every {
            artworkList.size
        } returns 10

        coEvery {
            repositoryMock.getArtworks(2)
        } answers {
            CallResponse.success(moreResults)
        }

        every {
            moreResults.pagination
        } returns pagingMore

        every {
            moreResults.data
        } returns moreArtworks

        viewModel.fetchArtworks()

        advanceUntilIdle()

        viewModel.uiState.test {
            awaitItem()

            viewModel.loadMoreResults()
            val loadingStep = awaitItem()

            loadingStep.isLoadingMore.shouldBeTrue()
            loadingStep.isLoading.shouldBeFalse()

            val resultStep = awaitItem()

            resultStep.isLoadingMore.shouldBeFalse()
            resultStep.isLoading.shouldBeFalse()
            resultStep.artworksCurrentPage shouldBeEqualTo 2
            resultStep.artworks shouldBeEqualTo artworkList + moreArtworks
        }
    }

    @Test
    fun `Should set update error message when loadMoreResults is invoked and fails`() = runTest {
        coEvery {
            repositoryMock.getArtworks(1)
        } answers {
            CallResponse.success(result)
        }

        every {
            result.pagination
        } returns paging

        every {
            result.data
        } returns artworkList

        every {
            artworkList.size
        } returns 10

        coEvery {
            repositoryMock.getArtworks(2)
        } answers {
            CallResponse.failure<NetworkErrorException>(NetworkErrorException("An error occurred"))
        }

        every {
            artworkList.size
        } returns 10

        viewModel.fetchArtworks()

        advanceUntilIdle()

        viewModel.uiState.test {
            awaitItem()

            viewModel.loadMoreResults()
            val loadingStep = awaitItem()

            loadingStep.isLoadingMore.shouldBeTrue()
            loadingStep.isLoading.shouldBeFalse()

            val resultStep = awaitItem()

            resultStep.isLoadingMore.shouldBeFalse()
            resultStep.isLoading.shouldBeFalse()
            resultStep.artworksCurrentPage shouldBeEqualTo 1
            resultStep.errorMessage shouldBeEqualTo "An error occurred"
            resultStep.artworks shouldBeEqualTo artworkList
        }
    }
}
