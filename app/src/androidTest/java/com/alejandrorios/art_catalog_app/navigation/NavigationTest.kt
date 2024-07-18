package com.alejandrorios.art_catalog_app.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.alejandrorios.art_catalog_app.di.appModule
import com.alejandrorios.art_catalog_app.di.dataBaseModule
import com.alejandrorios.art_catalog_app.di.dataModule
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import com.alejandrorios.art_catalog_app.ui.navigation.ArtworkCatalogNavGraph
import com.alejandrorios.art_catalog_app.utils.FakeArtRepositoryImpl
import com.alejandrorios.art_catalog_app.utils.KoinTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinContext
import org.koin.dsl.module

class NavigationTest {

    private val instrumentedTestModule = module {
        single<ArtRepository> { FakeArtRepositoryImpl() }
    }

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(modules = listOf(appModule, instrumentedTestModule, dataModule, dataBaseModule))

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupArtworkCatalogNavHost() {
        composeTestRule.setContent {
            KoinContext {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
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
