package com.alejandrorios.art_catalog_app.domain.repository

import com.alejandrorios.art_catalog_app.data.utils.CallResponse
import com.alejandrorios.art_catalog_app.data.utils.PAGE_SIZE
import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail
import com.alejandrorios.art_catalog_app.domain.models.ArtworkResults

interface ArtRepository {

    suspend fun getArtworks(page: Int, limit: Int = PAGE_SIZE): CallResponse<ArtworkResults>

    suspend fun getArtworkDetails(artworkId: Int): CallResponse<ArtworkDetail>
}
