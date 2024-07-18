package com.alejandrorios.art_catalog_app.utils

import com.alejandrorios.art_catalog_app.data.utils.CallResponse
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail
import com.alejandrorios.art_catalog_app.domain.models.ArtworkPaging
import com.alejandrorios.art_catalog_app.domain.models.ArtworkResults
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository

class FakeArtRepositoryImpl : ArtRepository {
    var isSuccess: Boolean = true

    override suspend fun getArtworks(page: Int, limit: Int): CallResponse<ArtworkResults> =
        if (isSuccess) {
            CallResponse.Success(mockedArtworkResults)
        } else {
            CallResponse.Failure(Exception("Something happened"))
        }

    override suspend fun getArtworkDetails(artworkId: Int): CallResponse<ArtworkDetail> =
        if (isSuccess) {
            CallResponse.Success(mockedArtworkDetails)
        } else {
            CallResponse.Failure(Exception("Something happened"))
        }
}

val mockedArtwork = Artwork(
    id = 12472,
    title = "Stone",
    artworkTypeTitle = "Sculpture",
    artistTitle = "Joan Miró",
    imageUrl = "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
)

val mockedArtworkResults = ArtworkResults(
    pagination = ArtworkPaging(
        total = 97,
        limit = 0,
        offset = 0,
        currentPage = 1,
        totalPages = 10
    ),
    data = listOf(mockedArtwork)
)

val mockedArtworkDetails = ArtworkDetail(
    id = 12472,
    title = "Stone",
    imageUrl = "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
    artistDisplay = "Joan Miró\nSpanish, 1893–1983",
    placeOfOrigin = "Spain",
    description = "Art description",
    shortDescription = "Alma Thomas was enthralled by astronauts and outer space.",
    dimensions = "82.7 × 41 × 12.7 cm (32 1/2 × 16 1/4 × 5 in.)",
    artworkTypeTitle = "Sculpture",
    artistTitle = "Joan Miró",
)
