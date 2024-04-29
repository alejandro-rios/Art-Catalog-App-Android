package com.alejandrorios.art_catalog_app.mappers

import com.alejandrorios.art_catalog_app.data.mappers.APIArtworkResultsMapper
import com.alejandrorios.art_catalog_app.data.models.APIArtworkResults
import com.alejandrorios.art_catalog_app.domain.models.ArtworkResults
import com.alejandrorios.art_catalog_app.utils.mockedAPIArtwork
import com.alejandrorios.art_catalog_app.utils.mockedAPIConfig
import com.alejandrorios.art_catalog_app.utils.mockedAPIPaging
import com.alejandrorios.art_catalog_app.utils.mockedArtwork
import com.alejandrorios.art_catalog_app.utils.mockedPaging
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIArtworkResultsMapperTest {
    @Test
    fun `When mapAsArtworkResults is called then should return ArtworkResult`() {
        val apiModel = APIArtworkResults(pagination = mockedAPIPaging, data = listOf(mockedAPIArtwork), config = mockedAPIConfig)

        val result = APIArtworkResultsMapper.mapAsArtworkResults(apiModel)

        result shouldBeEqualTo ArtworkResults(
            pagination = mockedPaging,
            data = listOf(mockedArtwork)
        )
    }
}
