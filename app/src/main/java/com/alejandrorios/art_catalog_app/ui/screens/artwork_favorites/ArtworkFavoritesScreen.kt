package com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.helpers.NotificationHelper
import com.alejandrorios.art_catalog_app.ui.components.EmptyView
import com.alejandrorios.art_catalog_app.ui.components.ShimmerLoadingView
import com.alejandrorios.art_catalog_app.ui.components.SwipeableListView
import com.alejandrorios.art_catalog_app.ui.navigation.NavigationItem
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ArtworkFavoritesScreen(
    navController: NavController,
    viewModel: ArtworkFavoritesViewModel = koinViewModel(),
    notificationHelper: NotificationHelper = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadArtworks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when {
            uiState.isLoading -> ShimmerLoadingView()
            !uiState.isLoading && uiState.artworks.isEmpty() ->
                EmptyView(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = stringResource(id = R.string.no_favorites),
                    message = stringResource(id = R.string.no_favorites_message),
                )

            else -> SwipeableListView(
                items = uiState.artworks,
                onClickItem = { artworkId ->
                    navController.navigate("${NavigationItem.ArtworkDetails.route}/$artworkId")
                },
                onDeleteItem = {
                    viewModel.removeArtwork(it)
                    notificationHelper.showSimpleNotification(R.string.removed_from_favorites_message)
                },
            )
        }
    }
}
