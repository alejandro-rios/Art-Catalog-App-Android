package com.alejandrorios.art_catalog_app.di

import com.alejandrorios.art_catalog_app.helpers.NotificationHelper
import com.alejandrorios.art_catalog_app.ui.screens.artwork_detail.ArtworkDetailViewModel
import com.alejandrorios.art_catalog_app.ui.screens.artwork_favorites.ArtworkFavoritesViewModel
import com.alejandrorios.art_catalog_app.ui.screens.artworks.ArtworksViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // NotificationHandler
    single{ NotificationHelper(get()) }

    // ViewModels
    viewModel { ArtworksViewModel(get(), get()) }
    viewModel { ArtworkDetailViewModel(get(), get(), get()) }
    viewModel { ArtworkFavoritesViewModel(get(), get()) }
}
