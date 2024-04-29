package com.alejandrorios.art_catalog_app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class APIArtworkResults (
    val pagination: APIArtworkPaging,
    val data: List<APIArtwork>,
    val config: APIArtworkImageConfig,
)

