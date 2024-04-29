package com.alejandrorios.art_catalog_app.mappers

import com.alejandrorios.art_catalog_app.data.mappers.APIArtworkPagingMapper
import com.alejandrorios.art_catalog_app.utils.mockedAPIPaging
import com.alejandrorios.art_catalog_app.utils.mockedPaging
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIArtworkPagingMapperTest {
    @Test
    fun `When mapAsArtworkPaging is called then should return ArtworkPaging`() {
        val result = APIArtworkPagingMapper.mapAsArtworkPaging(mockedAPIPaging)

        result shouldBeEqualTo mockedPaging
    }
}
