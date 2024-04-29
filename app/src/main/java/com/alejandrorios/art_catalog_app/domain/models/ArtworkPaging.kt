package com.alejandrorios.art_catalog_app.domain.models

data class ArtworkPaging(
    val total: Int,
    val totalPages: Int,
    val offset: Int,
    val limit: Int,
    val currentPage: Int
)
