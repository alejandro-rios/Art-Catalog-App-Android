package com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.helpers.NotificationHelper
import com.alejandrorios.art_catalog_app.ui.components.EmptyView
import com.alejandrorios.art_catalog_app.ui.components.ShimmerLoadingView
import com.alejandrorios.art_catalog_app.ui.components.SwipeableListView
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ArtworkFavoritesScreen(
    navigateToArtworkDetails: (artworkId: Int) -> Unit,
    viewModel: ArtworkFavoritesViewModel = koinViewModel(),
    notificationHelper: NotificationHelper = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadArtworks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .semantics {  contentDescription = "Artwork Favorites" },
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
                    navigateToArtworkDetails(artworkId)
                },
                onDeleteItem = {
                    viewModel.removeArtwork(it)
                    notificationHelper.showSimpleNotification(R.string.removed_from_favorites_message)
                },
            )
        }
    }
}
