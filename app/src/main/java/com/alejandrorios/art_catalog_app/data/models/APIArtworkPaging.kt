package com.alejandrorios.art_catalog_app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIArtworkPaging(
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    val offset: Int,
    val limit: Int,
    @SerialName("current_page")
    val currentPage: Int
)
