package com.alejandrorios.art_catalog_app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class APIArtworkDetailResult(
    val data: APIArtworkDetail,
    val config: APIArtworkImageConfig,
)
