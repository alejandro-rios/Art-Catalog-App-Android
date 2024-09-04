package com.alejandrorios.art_catalog_app.navigation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.rule.GrantPermissionRule
import com.alejandrorios.art_catalog_app.ui.MainActivity
import com.alejandrorios.art_catalog_app.ui.navigation.ArtworkCatalogNavGraph
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS)

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createEmptyComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupArtworkCatalogNavHost() {
        hiltTestRule.inject()

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            navController = TestNavHostController(activity)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            activity.setContent {
                ArtworkCatalogNavGraph(navController = navController)
            }
        }
    }

    @Test
    fun artworkCatalogNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Artworks Screen")
            .assertIsDisplayed()
    }
}
