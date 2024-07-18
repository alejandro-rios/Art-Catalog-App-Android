package com.alejandrorios.art_catalog_app.ui.screens.artwork_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alejandrorios.art_catalog_app.R
import com.alejandrorios.art_catalog_app.helpers.NotificationHelper
import com.alejandrorios.art_catalog_app.ui.components.ArtworkDetailView
import com.alejandrorios.art_catalog_app.ui.components.ErrorView
import com.alejandrorios.art_catalog_app.ui.components.LoadingView
import com.alejandrorios.art_catalog_app.ui.utils.bounceClickable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ArtworkDetailScreen(
    onGoBackPressed: () -> Unit,
    artworkId: Int,
    viewModel: ArtworkDetailViewModel = koinViewModel(),
    notificationHelper: NotificationHelper = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(artworkId) {
        viewModel.getArtworkDetail(artworkId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onGoBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_arrow_description)
                        )
                    }
                },
                actions = {
                    if (!uiState.isLoading && uiState.errorMessage.isNullOrBlank()) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .semantics { testTagsAsResourceId = true }
                                .testTag(stringResource(R.string.favorite_description))
                        ) {
                            Icon(
                                imageVector = if (uiState.isSaved) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Filled.FavoriteBorder
                                },
                                contentDescription = stringResource(R.string.favorite_description),
                                modifier = Modifier.bounceClickable(
                                    minScale = 0.5f,
                                    onAnimationFinished = {
                                        val message = if (uiState.isSaved) {
                                            viewModel.removeArtwork()
                                            R.string.removed_from_favorites_message
                                        } else {
                                            viewModel.saveArtwork()
                                            R.string.saved_in_favorites_message
                                        }

                                        notificationHelper.showSimpleNotification(message)
                                    }
                                )
                            )
                        }
                    }
                }
            )
        },
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoadingView(
                modifier = Modifier
                    .padding(paddingValues)
            )

            !uiState.errorMessage.isNullOrBlank() -> ErrorView(
                modifier = Modifier.padding(16.dp),
                onRetry = {
                    viewModel.getArtworkDetail(artworkId)
                },
            )

            else -> ArtworkDetailView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                artworkDetails = uiState.artworkDetails!!,
            )
        }
    }
}
