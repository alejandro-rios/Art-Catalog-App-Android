package com.alejandrorios.art_catalog_app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alejandrorios.art_catalog_app.ui.screens.artwork_detail.ArtworkDetailScreen
import com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites.ArtworkFavoritesScreen
import com.alejandrorios.art_catalog_app.ui.screens.artworks.ArtworksScreen

@Composable
fun ArtworkCatalogNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            ArtworksScreen(navController)
        }
        composable(
            route = "${NavigationItem.ArtworkDetails.route}/{artworkId}",
            enterTransition = {
                return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
            },
            arguments = listOf(navArgument("artworkId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val artworkId = backStackEntry.arguments?.getString("artworkId")

            ArtworkDetailScreen(navController, artworkId = artworkId!!.toInt())
        }
        composable(
            route = NavigationItem.ArtworkFavorites.route,
        ) {
            ArtworkFavoritesScreen(navController)
        }
    }
}

enum class Screen {
    HOME,
    ARTWORK_DETAILS,
    ARTWORK_FAVORITES,
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object ArtworkDetails : NavigationItem(Screen.ARTWORK_DETAILS.name)
    data object ArtworkFavorites : NavigationItem(Screen.ARTWORK_FAVORITES.name)
}
