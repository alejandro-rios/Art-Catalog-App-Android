package com.alejandrorios.art_catalog_app.view_models

import app.cash.turbine.test
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites.ArtworkFavoritesViewModel
import com.alejandrorios.art_catalog_app.utils.MainDispatcherRule
import com.alejandrorios.art_catalog_app.utils.MockKableTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArtworkFavoritesViewModelTest : MockKableTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var daoMock: ArtworksDao

    private lateinit var viewModel: ArtworkFavoritesViewModel
    private val resultArtworks = mockk<List<Artwork>>(relaxed = true)
    private val mockedArtwork = mockk<Artwork>(relaxed = true)

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = ArtworkFavoritesViewModel( daoMock)
    }

    @Test
    fun `Should set update artworks when loadArtworks is invoked`() = runTest {
        coEvery {
            daoMock.getArtworks()
        } returns flow {
            emit(resultArtworks)
        }

        viewModel.uiState.test {
            viewModel.loadArtworks()
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()

            val resultDetailStep = awaitItem()

            resultDetailStep.isLoading.shouldBeFalse()
            resultDetailStep.artworks shouldBeEqualTo resultArtworks
        }
    }

    @Test
    fun `Should set delete artwork when removeArtwork is invoked`() = runTest {
        coEvery {
            daoMock.getArtworks()
        } returns flow {
            emit(resultArtworks)
        }

        coEvery {
            daoMock.deleteArtwork(mockedArtwork)
        } just runs

        viewModel.loadArtworks()

        advanceUntilIdle()

        viewModel.uiState.test {
            awaitItem()

            viewModel.removeArtwork(mockedArtwork)

            val resultSaveStep = awaitItem()

            resultSaveStep.isLoading.shouldBeFalse()
        }
    }
}
