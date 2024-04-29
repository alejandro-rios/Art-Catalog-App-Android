package com.alejandrorios.art_catalog_app.data.mappers

import com.alejandrorios.art_catalog_app.data.models.APIArtwork
import com.alejandrorios.art_catalog_app.data.utils.IMAGE_URL_COMPONENT
import com.alejandrorios.art_catalog_app.domain.models.Artwork

/**
 * Check [APIArtworkResultsMapper] for the explanation of the imageUrl param
 */
object APIArtworkMapper {

    fun mapAsArtwork(apiModel: APIArtwork, iiifUrl: String): Artwork = with(apiModel) {
        Artwork(
            id = id,
            title = title,
            artistTitle = artistTitle,
            artworkTypeTitle = artworkTypeTitle,
            imageUrl = imageId?.let { "$iiifUrl/$it$IMAGE_URL_COMPONENT" } ?: "",
        )
    }
}
