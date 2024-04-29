package com.alejandrorios.art_catalog_app.data.network

import com.alejandrorios.art_catalog_app.data.models.APIArtworkDetailResult
import com.alejandrorios.art_catalog_app.data.models.APIArtworkResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtAPIService {

    @GET(ARTWORKS_PATH)
    fun getArtworks(@Query("page") page: Int = 1, @Query("limit") limit: Int): Call<APIArtworkResults>

    @GET(ARTWORK_DETAIL_PATH)
    fun getArtworkDetails(@Path("id") id: Int): Call<APIArtworkDetailResult>
}
