package com.alejandrorios.art_catalog_app.ui.screens.artworks

import com.alejandrorios.art_catalog_app.domain.models.Artwork

data class ArtworksUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val artworksCurrentPage: Int = 1,
    val artworksTotalPages: Int = 0,
    val artworks: List<Artwork> = emptyList(),
    val errorMessage: String? = null
) {
    val isEmpty = !isLoading && artworks.isEmpty() && errorMessage.isNullOrBlank()
}
