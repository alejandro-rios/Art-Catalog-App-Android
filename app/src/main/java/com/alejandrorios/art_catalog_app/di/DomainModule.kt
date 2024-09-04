package com.alejandrorios.art_catalog_app.di

import com.alejandrorios.art_catalog_app.data.repository.ArtRepositoryImpl
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    @Singleton
    abstract fun bindArtRepository(artRepositoryImpl: ArtRepositoryImpl): ArtRepository
}
