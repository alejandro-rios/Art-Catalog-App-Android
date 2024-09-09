package com.alejandrorios.art_catalog_app.ui

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.di.appModule
import com.alejandrorios.art_catalog_app.di.dataBaseModule
import com.alejandrorios.art_catalog_app.di.dataModule
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import com.alejandrorios.art_catalog_app.ui.screens.artwork_detail.ArtworkDetailScreen
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import com.alejandrorios.art_catalog_app.utils.FakeArtRepositoryImpl
import com.alejandrorios.art_catalog_app.utils.KoinTestRule
import com.alejandrorios.art_catalog_app.utils.mockedArtworkDetails
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.koin.compose.KoinContext
import org.koin.compose.getKoin
import org.koin.dsl.module

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ArtworksDetailScreenTest {

    private val instrumentedTestModule = module {
        single<ArtRepository> { FakeArtRepositoryImpl() }
    }

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(modules = listOf(appModule, instrumentedTestModule, dataModule, dataBaseModule))

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun artworkDetailScreen_is_loaded_with_art_details() {
        launchArtworkDetailScreen(shouldSucceed = true)

        with(composeTestRule) {
            val imageDescription = context.getString(R.string.artwork_thumb_description)

            onNodeWithTag("loadingView").assertIsDisplayed()

            waitUntil {
                onAllNodesWithContentDescription(imageDescription).fetchSemanticsNodes().size == 1
            }

            onNodeWithTag("loadingView").assertIsNotDisplayed()
            onNodeWithText(mockedArtworkDetails.title).assertIsDisplayed()
            onNodeWithText(mockedArtworkDetails.dimensions!!).assertIsDisplayed()
            onNodeWithText(mockedArtworkDetails.description!!).assertIsDisplayed()
        }
    }

    @Test
    fun artworkDetailScreen_shows_fail_screen() {
        launchArtworkDetailScreen(shouldSucceed = false)

        with(composeTestRule) {
            val errorTitle = context.getString(R.string.error_title)
            val errorMessage = context.getString(R.string.error_message)

            onNodeWithTag("loadingView").assertIsDisplayed()

            waitUntil {
                onAllNodesWithText(errorTitle).fetchSemanticsNodes().size == 1
            }

            onNodeWithTag("loadingView").assertIsNotDisplayed()
            onNodeWithText(errorTitle).assertIsDisplayed()
            onNodeWithText(errorMessage).assertIsDisplayed()
            onNodeWithTag("retryButton").assertIsDisplayed()
        }
    }

    private fun launchArtworkDetailScreen(shouldSucceed: Boolean) {
        composeTestRule.setContent {
            KoinContext {
                val mockedRepository: FakeArtRepositoryImpl = getKoin().get<ArtRepository>() as FakeArtRepositoryImpl
                mockedRepository.isSuccess = shouldSucceed

                Art_catalog_appTheme {
                    ArtworkDetailScreen( 12472){}
                }
            }
        }
    }
}
