package com.alejandrorios.art_catalog_app.di

import com.alejandrorios.art_catalog_app.data.repository.ArtRepositoryImpl
import com.alejandrorios.art_catalog_app.domain.repository.ArtRepository
import org.koin.dsl.module

val domainModule = module {
    // Repository
    single<ArtRepository> { ArtRepositoryImpl(get()) }
}
