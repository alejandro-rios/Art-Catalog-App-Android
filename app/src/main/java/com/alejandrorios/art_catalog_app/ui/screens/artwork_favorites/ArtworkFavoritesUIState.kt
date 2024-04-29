package com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites

import com.alejandrorios.art_catalog_app.domain.models.Artwork

data class ArtworkFavoritesUIState(
    val isLoading: Boolean = true,
    val artworks: List<Artwork> = emptyList(),
)
