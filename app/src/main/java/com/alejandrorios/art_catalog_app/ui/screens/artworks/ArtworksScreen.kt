package com.alejandrorios.art_catalog_app.ui.screens.artworks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alejandrorios.art_catalog_app.ui.components.EmptyView
import com.alejandrorios.art_catalog_app.ui.components.ErrorView
import com.alejandrorios.art_catalog_app.ui.components.PaginationListView
import com.alejandrorios.art_catalog_app.ui.components.ShimmerLoadingView
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArtworksScreen(
    viewModel: ArtworksViewModel = koinViewModel(),
    navigateToArtworkDetails: (artworkId: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchArtworks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = "Artworks Screen" },
    ) {
        when {
            uiState.isEmpty -> EmptyView(modifier = Modifier.padding(horizontal = 16.dp))
            uiState.isLoading -> ShimmerLoadingView()
            !uiState.errorMessage.isNullOrBlank() -> ErrorView(
                modifier = Modifier.padding(16.dp),
                onRetry = viewModel::fetchArtworks,
            )

            else -> PaginationListView(
                items = uiState.artworks,
                isLoadingItems = uiState.isLoadingMore,
                onClickItem = { artworkId ->
                    navigateToArtworkDetails(artworkId)
                },
                onNewItems = viewModel::loadMoreResults
            )
        }
    }
}
