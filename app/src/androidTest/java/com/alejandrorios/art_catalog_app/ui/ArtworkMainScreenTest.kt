package com.alejandrorios.art_catalog_app.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.rule.GrantPermissionRule
import com.alejandrorios.art_catalog_app.di.appModule
import com.alejandrorios.art_catalog_app.di.dataBaseModule
import com.alejandrorios.art_catalog_app.di.dataModule
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import com.alejandrorios.art_catalog_app.ui.screens.artwork_main.ArtworkMainScreen
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import com.alejandrorios.art_catalog_app.utils.FakeArtRepositoryImpl
import com.alejandrorios.art_catalog_app.utils.KoinTestRule
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.koin.compose.KoinContext
import org.koin.dsl.module

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ArtworkMainScreenTest {

    private val instrumentedTestModule = module {
        single<ArtRepository> { FakeArtRepositoryImpl() }
    }

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS)

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(modules = listOf(appModule, instrumentedTestModule, dataModule, dataBaseModule))

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun artworkMainScreen_should_show_bottom_bar_nav() {
        launchArtworksScreen()

        with(composeTestRule) {
            onNodeWithText("Home").assertIsDisplayed()
            onNodeWithText("Home").assertIsSelected()
            onNodeWithText("Favorites").assertIsDisplayed()
            onNodeWithText("Favorites").assertIsNotSelected()
        }
    }

    private fun launchArtworksScreen() {
        composeTestRule.setContent {
            KoinContext {
                Art_catalog_appTheme {
                    ArtworkMainScreen()
                }
            }
        }
    }
}
