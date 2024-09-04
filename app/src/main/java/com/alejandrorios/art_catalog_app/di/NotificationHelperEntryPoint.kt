package com.alejandrorios.art_catalog_app.di

import com.alejandrorios.art_catalog_app.helpers.NotificationHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NotificationHelperEntryPoint {
    val notificationHelper: NotificationHelper
}
