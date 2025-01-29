package com.alejandrorios.art_catalog_app.di

import android.content.Context
import com.alejandrorios.art_catalog_app.data.utils.AppDispatchers
import com.alejandrorios.art_catalog_app.data.utils.AppDispatchersImpl
import com.alejandrorios.art_catalog_app.helpers.NotificationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNotificationHelper(@ApplicationContext context: Context): NotificationHelper = NotificationHelper(context)

    @Provides
    fun provideAppDispatchers(): AppDispatchers = AppDispatchersImpl()

    @Provides
    @Named("enableDelay")
    fun provideEnableDelay(): Boolean = true
}

