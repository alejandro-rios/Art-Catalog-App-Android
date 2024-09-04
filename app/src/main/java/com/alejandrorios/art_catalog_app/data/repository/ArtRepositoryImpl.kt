package com.alejandrorios.art_catalog_app.data.repository

import com.alejandrorios.art_catalog_app.data.mappers.APIArtworkDetailResultMapper
import com.alejandrorios.art_catalog_app.data.mappers.APIArtworkResultsMapper
import com.alejandrorios.art_catalog_app.data.network.ArtAPIService
import com.alejandrorios.art_catalog_app.data.utils.CallResponse
import com.alejandrorios.art_catalog_app.data.utils.handleResponse
import com.alejandrorios.art_catalog_app.di.IoDispatcher
import com.alejandrorios.art_catalog_app.domain.models.ArtworkDetail
import com.alejandrorios.art_catalog_app.domain.models.ArtworkResults
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

class ArtRepositoryImpl @Inject constructor(
    private val service: ArtAPIService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ArtRepository {

    override suspend fun getArtworks(page: Int, limit: Int): CallResponse<ArtworkResults> = withContext(dispatcher) {
        suspendCoroutine { continuation ->
            service.getArtworks(page, limit).handleResponse(continuation = continuation) { apiResponse ->
                APIArtworkResultsMapper.mapAsArtworkResults(apiResponse)
            }
        }
    }

    override suspend fun getArtworkDetails(artworkId: Int): CallResponse<ArtworkDetail> = withContext(dispatcher) {
        suspendCoroutine { continuation ->
            service.getArtworkDetails(id = artworkId).handleResponse(continuation = continuation) { apiResponse ->
                APIArtworkDetailResultMapper.mapAsArtworkDetail(apiResponse)
            }
        }
    }
}
