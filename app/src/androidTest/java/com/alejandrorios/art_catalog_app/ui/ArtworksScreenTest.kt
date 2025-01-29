package com.alejandrorios.art_catalog_app.ui

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.ui.screens.artworks.ArtworksScreen
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import com.alejandrorios.art_catalog_app.utils.FakeRepositoryModule
import com.alejandrorios.art_catalog_app.utils.mockedArtwork
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
class ArtworksScreenTest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS)

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
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
        FakeRepositoryModule.setShouldSucceed(shouldSucceed)
        hiltTestRule.inject()

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            activity.setContent {
                Art_catalog_appTheme {
                    ArtworksScreen{}
                }
            }
        }
    }
}
