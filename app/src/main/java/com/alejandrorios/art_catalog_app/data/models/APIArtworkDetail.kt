package com.alejandrorios.art_catalog_app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIArtworkDetail(
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("image_id")
    val imageId: String?,
    @SerialName("artist_display")
    val artistDisplay: String?,
    @SerialName("place_of_origin")
    val placeOfOrigin: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("short_description")
    val shortDescription: String?,
    @SerialName("dimensions")
    val dimensions: String?,
    @SerialName("artwork_type_title")
    val artworkTypeTitle: String,
    @SerialName("artist_title")
    val artistTitle: String?,
)
