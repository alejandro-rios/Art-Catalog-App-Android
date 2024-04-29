package com.alejandrorios.art_catalog_app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIArtwork(
    val id: Int,
    val title: String,
    @SerialName("artist_title")
    val artistTitle: String?,
    @SerialName("artwork_type_title")
    val artworkTypeTitle: String,
    @SerialName("image_id")
    val imageId: String?,
)
