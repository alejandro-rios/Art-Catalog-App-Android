package com.alejandrorios.art_catalog_app.domain.models

data class ArtworkDetail(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val artistDisplay: String?,
    val placeOfOrigin: String?,
    val description: String?,
    val shortDescription: String?,
    val dimensions: String?,
    val artworkTypeTitle: String,
    val artistTitle: String?,
) {
    // This mapper is used only to save the model in database
    fun mapAsArtwork(): Artwork = with(this) {
        Artwork(
            id = id,
            title = title,
            imageUrl = imageUrl,
            artworkTypeTitle = artworkTypeTitle,
            artistTitle = artistTitle,
        )
    }
}
