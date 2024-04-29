package com.alejandrorios.art_catalog_app.mappers

import com.alejandrorios.art_catalog_app.data.mappers.APIArtworkDetailResultMapper
import com.alejandrorios.art_catalog_app.data.models.APIArtworkDetail
import com.alejandrorios.art_catalog_app.data.models.APIArtworkDetailResult
import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail
import com.alejandrorios.art_catalog_app.utils.mockedAPIConfig
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIArtworkDetailResultMapperTest {
    @Test
    fun `When mapAsArtworkDetail is called then should return ArtworkDetail`() {
        val data = APIArtworkDetail(
            id = 12472,
            title = "Stone",
            imageId = "e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9",
            artistDisplay = "Joan Miró\nSpanish, 1893–1983",
            placeOfOrigin = "Spain",
            description = "Art description",
            shortDescription = "Alma Thomas was enthralled by astronauts and outer space.",
            dimensions = "82.7 × 41 × 12.7 cm (32 1/2 × 16 1/4 × 5 in.)",
            artworkTypeTitle = "Sculpture",
            artistTitle = "Joan Miró",
        )

        val apiModel = APIArtworkDetailResult(data = data, config = mockedAPIConfig)

        val result = APIArtworkDetailResultMapper.mapAsArtworkDetail(apiModel)

        result shouldBeEqualTo ArtworkDetail(
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
    }
}
