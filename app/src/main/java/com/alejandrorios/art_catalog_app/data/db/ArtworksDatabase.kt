package com.alejandrorios.art_catalog_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alejandrorios.art_catalog_app.domain.models.Artwork

const val DBNAME = "art_catalog"

@Database(entities = [Artwork::class], version = 1)
abstract class ArtworksDatabase: RoomDatabase() {

    abstract val dao: ArtworksDao
}
