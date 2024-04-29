package com.alejandrorios.art_catalog_app.data.mappers

import com.alejandrorios.art_catalog_app.data.models.APIArtworkResults
import com.alejandrorios.art_catalog_app.domain.models.ArtworkResults

/**
 * The API response comes like this:
 * {
 *   "pagination": {
 *     "total": 124938,
 *     "limit": 1,
 *     "offset": 0,
 *   },
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
 * To get the image Url on the Artwork model, is necessary to concatenate the "config iiif_url" with the "imageId" and the
 * IMAGE_URL_COMPONENT constant, the project doesn't require the config model for Domain/Ui so the mapper just returns the imageUrl component
 *
 */
object APIArtworkResultsMapper {

    fun mapAsArtworkResults(apiModel: APIArtworkResults): ArtworkResults = with(apiModel) {
        ArtworkResults(
            pagination = APIArtworkPagingMapper.mapAsArtworkPaging(pagination),
            data = data.map { APIArtworkMapper.mapAsArtwork(it, config.iiifUrl) }
        )
    }
}
