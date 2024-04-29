package com.alejandrorios.art_catalog_app.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Artwork(
    @PrimaryKey
    val id: Int,
    val title: String,
    val artistTitle: String?,
    val artworkTypeTitle: String,
    val imageUrl: String,
)
