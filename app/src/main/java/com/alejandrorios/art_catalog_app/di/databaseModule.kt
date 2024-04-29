package com.alejandrorios.art_catalog_app.di

import android.app.Application
import androidx.room.Room
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.data.db.ArtworksDatabase
import com.alejandrorios.art_catalog_app.data.db.DBNAME
import org.koin.dsl.module

val dataBaseModule = module {
    single { provideDataBase(get()) }
    single { provideDao(get()) }
}

fun provideDataBase(application: Application): ArtworksDatabase =
    Room.databaseBuilder(
        application,
        ArtworksDatabase::class.java,
        "$DBNAME.db"
    ).fallbackToDestructiveMigration().build()

fun provideDao(artworksDataBase: ArtworksDatabase): ArtworksDao = artworksDataBase.dao
