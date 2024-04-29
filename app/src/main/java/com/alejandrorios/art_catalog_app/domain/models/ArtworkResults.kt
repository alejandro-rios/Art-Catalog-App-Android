package com.alejandrorios.art_catalog_app.domain.models

data class ArtworkResults (
    val pagination: ArtworkPaging,
    val data: List<Artwork>
)
