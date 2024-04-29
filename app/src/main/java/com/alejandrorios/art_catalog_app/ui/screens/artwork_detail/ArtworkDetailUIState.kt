package com.alejandrorios.art_catalog_app.ui.screens.artwork_detail

import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail

data class ArtworkDetailUIState(
    val isLoading: Boolean = true,
    val isSaved: Boolean = false,
    val artworkDetails: ArtworkDetail? = null,
    val errorMessage: String? = null,
)
