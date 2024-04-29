package com.alejandrorios.art_catalog_app.data.mappers

import com.alejandrorios.art_catalog_app.data.models.APIArtworkPaging
import com.alejandrorios.art_catalog_app.domain.models.ArtworkPaging

object APIArtworkPagingMapper {

    fun mapAsArtworkPaging(apiModel: APIArtworkPaging): ArtworkPaging = with(apiModel) {
        ArtworkPaging(
            total = total,
            totalPages = totalPages,
            offset = offset,
            limit = limit,
            currentPage = currentPage
        )
    }
}
