package com.alejandrorios.art_catalog_app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.alejandrorios.art_catalog_app.di.appModule
import com.alejandrorios.art_catalog_app.di.dataBaseModule
import com.alejandrorios.art_catalog_app.di.dataModule
import com.alejandrorios.art_catalog_app.di.domainModule
import com.alejandrorios.art_catalog_app.helpers.notificationChannelID
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ArtCatalogApplication: Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val notificationChannel = NotificationChannel(
            notificationChannelID,
            "Artwork Catalog App Notification name",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Setting up the channel
        notificationManager.createNotificationChannel(notificationChannel)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ArtCatalogApplication)
            modules(appModule, domainModule, dataModule, dataBaseModule)
        }
    }
}
