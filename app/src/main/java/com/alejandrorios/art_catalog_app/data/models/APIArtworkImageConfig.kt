package com.alejandrorios.art_catalog_app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIArtworkImageConfig(
    @SerialName("iiif_url")
    val iiifUrl: String,
)
