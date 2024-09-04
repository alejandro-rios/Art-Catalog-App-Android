package com.alejandrorios.art_catalog_app.di

import android.content.Context
import androidx.room.Room
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.data.db.ArtworksDatabase
import com.alejandrorios.art_catalog_app.data.db.DBNAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideArtworksDatabase(@ApplicationContext appContext: Context): ArtworksDatabase =
        Room.databaseBuilder(
            appContext,
            ArtworksDatabase::class.java,
            "$DBNAME.db"
        ).fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun provideDao(artworksDataBase: ArtworksDatabase): ArtworksDao = artworksDataBase.dao
}
