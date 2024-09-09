package com.alejandrorios.art_catalog_app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alejandrorios.art_catalog_app.ui.screens.artwork_detail.ArtworkDetailScreen
import com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites.ArtworkFavoritesScreen
import com.alejandrorios.art_catalog_app.ui.screens.artworks.ArtworksScreen
import kotlinx.serialization.Serializable

@Composable
fun ArtworkCatalogNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            ArtworksScreen { artworkId ->
                navController.navigate(ArtworkNavDetail(artworkId))
            }
        }
        composable<ArtworkNavDetail>(
            enterTransition = {
                return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))
            },
            popExitTransition = {
                return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
            },
        ) { backStackEntry ->
            val artworkNavDetail: ArtworkNavDetail = backStackEntry.toRoute()

            ArtworkDetailScreen(artworkId = artworkNavDetail.id) { navController.popBackStack() }
        }
        composable<ArtworkFavorites> {
            ArtworkFavoritesScreen { artworkId ->
                navController.navigate(ArtworkNavDetail(artworkId))
            }
        }
    }
}

@Serializable
data object Home

@Serializable
data class ArtworkNavDetail(val id: Int)

@Serializable
data object ArtworkFavorites
