package com.alejandrorios.art_catalog_app.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.di.appModule
import com.alejandrorios.art_catalog_app.di.dataBaseModule
import com.alejandrorios.art_catalog_app.di.dataModule
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import com.alejandrorios.art_catalog_app.ui.screens.artworks.ArtworksScreen
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import com.alejandrorios.art_catalog_app.utils.FakeArtRepositoryImpl
import com.alejandrorios.art_catalog_app.utils.KoinTestRule
import com.alejandrorios.art_catalog_app.utils.mockedArtwork
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.koin.compose.KoinContext
import org.koin.compose.getKoin
import org.koin.dsl.module

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ArtworksScreenTest {

    private val instrumentedTestModule = module {
        single<ArtRepository> { FakeArtRepositoryImpl() }
    }

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(modules = listOf(appModule, instrumentedTestModule, dataModule, dataBaseModule))

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun artworkScreen_is_loaded_with_one_item() {
        launchArtworksScreen(shouldSucceed = true)

        with(composeTestRule) {
            onNodeWithTag("paginationListView").assertIsDisplayed()
            onNodeWithTag("artworkTile").assertIsDisplayed()
            onNodeWithText(mockedArtwork.title).assertIsDisplayed()
            onNodeWithText(mockedArtwork.artworkTypeTitle).assertIsDisplayed()
            onNodeWithText(mockedArtwork.artistTitle!!).assertIsDisplayed()
        }
    }

    @Test
    fun artworkScreen_shows_fail_screen() {
        launchArtworksScreen(shouldSucceed = false)

        with(composeTestRule) {
            onNodeWithTag("paginationListView").assertIsNotDisplayed()
            onNodeWithTag("artworkTile").assertIsNotDisplayed()

            val errorTitle = context.getString(R.string.error_title)
            val errorMessage = context.getString(R.string.error_message)
            onNodeWithText(errorTitle).assertIsDisplayed()
            onNodeWithText(errorMessage).assertIsDisplayed()
            onNodeWithTag("retryButton").assertIsDisplayed()
        }
    }

    private fun launchArtworksScreen(shouldSucceed: Boolean) {
        composeTestRule.setContent {
            KoinContext {
                val mockedRepository: FakeArtRepositoryImpl = getKoin().get<ArtRepository>() as FakeArtRepositoryImpl
                mockedRepository.isSuccess = shouldSucceed

                Art_catalog_appTheme {
                    ArtworksScreen({})
                }
            }
        }
    }
}
