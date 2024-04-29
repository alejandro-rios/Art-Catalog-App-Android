package com.alejandrorios.art_catalog_app.utils

import com.alejandrorios.art_catalog_app.data.models.APIArtwork
import com.alejandrorios.art_catalog_app.data.models.APIArtworkImageConfig
import com.alejandrorios.art_catalog_app.data.models.APIArtworkPaging
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import com.alejandrorios.art_catalog_app.domain.models.ArtworkPaging

val mockedAPIConfig = APIArtworkImageConfig(iiifUrl = "https://www.artic.edu/iiif/2")
val mockedAPIPaging = APIArtworkPaging(
    total = 97,
    limit = 0,
    offset = 0,
    currentPage = 1,
    totalPages = 10
)
val mockedPaging = ArtworkPaging(
    total = 97,
    limit = 0,
    offset = 0,
    currentPage = 1,
    totalPages = 10
)
val mockedAPIArtwork = APIArtwork(
    id = 12472,
    title = "Stone",
    artworkTypeTitle = "Sculpture",
    artistTitle = "Joan Miró",
    imageId = "e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9",
)
val mockedArtwork = Artwork(
    id = 12472,
    title = "Stone",
    artworkTypeTitle = "Sculpture",
    artistTitle = "Joan Miró",
    imageUrl = "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
)
