package com.alejandrorios.art_catalog_app.utils

import com.alejandrorios.art_catalog_app.di.DomainModule
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Required for Hilt in order to replace the [DomainModule], with Koin this object is unnecessary :v
 */
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModule::class]
)
@Module
object FakeRepositoryModule {
    private var shouldSucceed = true

    fun setShouldSucceed(value: Boolean) {
        shouldSucceed = value
    }

    @Provides
    @Singleton
    fun provideFakeArtRepository(): ArtRepository = FakeArtRepositoryImpl(shouldSucceed)
}
