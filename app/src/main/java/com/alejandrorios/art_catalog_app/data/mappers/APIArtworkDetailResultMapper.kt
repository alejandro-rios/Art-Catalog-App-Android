package com.alejandrorios.art_catalog_app.data.mappers

import com.alejandrorios.art_catalog_app.data.models.APIArtworkDetailResult
import com.alejandrorios.art_catalog_app.data.utils.IMAGE_URL_COMPONENT
import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail

/**
 * The API response comes like this:
 * {
 *   "data": [
 *     {
 *       "id": 46027,
 *       "api_model": "artworks",
 *       "title": "From Torreón to Lerdo",
 *     }
 *   ],
 *   "config": {
 *     "iiif_url": "https://www.artic.edu/iiif/2"
 *   }
 * }
 *
 * To get the image Url, is necessary to concatenate the "config iiif_url" with the "imageId" and the IMAGE_URL_COMPONENT
 * constant, the project doesn't require the config model for Domain/Ui so the mapper just returns the imageUrl component
 *
 */
object APIArtworkDetailResultMapper {

    fun mapAsArtworkDetail(apiModel: APIArtworkDetailResult): ArtworkDetail = with(apiModel) {
        ArtworkDetail(
            id = data.id,
            title = data.title,
            imageUrl = data.imageId?.let { "${config.iiifUrl}/$it$IMAGE_URL_COMPONENT" } ?: "",
            artistDisplay = data.artistDisplay,
            placeOfOrigin = data.placeOfOrigin,
            description = data.description,
            shortDescription = data.shortDescription,
            dimensions = data.dimensions,
            artworkTypeTitle = data.artworkTypeTitle,
            artistTitle = data.artistTitle,
        )
    }
}
