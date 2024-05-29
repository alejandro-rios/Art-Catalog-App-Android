package com.alejandrorios.art_catalog_app.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.alejandrorios.art_catalog_app.ui.navigation.ArtworkCatalogNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupArtworkCatalogNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            ArtworkCatalogNavGraph(navController = navController)
        }
    }
    @Test
    fun artworkCatalogNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Artworks Screen")
            .assertIsDisplayed()
    }
}
