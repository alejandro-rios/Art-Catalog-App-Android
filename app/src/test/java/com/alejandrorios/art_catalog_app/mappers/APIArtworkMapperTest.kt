package com.alejandrorios.art_catalog_app.mappers

import com.alejandrorios.art_catalog_app.data.mappers.APIArtworkMapper
import com.alejandrorios.art_catalog_app.utils.mockedAPIArtwork
import com.alejandrorios.art_catalog_app.utils.mockedArtwork
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIArtworkMapperTest {
    @Test
    fun `When mapAsArtworkDetail is called then should return Artwork`() {
        val result = APIArtworkMapper.mapAsArtwork(mockedAPIArtwork, "https://www.artic.edu/iiif/2")

        result shouldBeEqualTo mockedArtwork
    }
}
