package com.alejandrorios.art_catalog_app.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtwork(artwork: Artwork)

    @Delete
    suspend fun deleteArtwork(artwork: Artwork)

    @Query("SELECT * FROM Artwork")
    fun getArtworks(): Flow<List<Artwork>>

    @Query("SELECT * FROM Artwork WHERE id = :artworkId")
    fun findArtworkById(artworkId: Int): Flow<Artwork?>
}
