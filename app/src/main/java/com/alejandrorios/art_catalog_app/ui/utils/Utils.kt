package com.alejandrorios.art_catalog_app.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.alejandrorios.art_catalog_app.di.NotificationHelperEntryPoint
import com.alejandrorios.art_catalog_app.helpers.NotificationHelper
import dagger.hilt.EntryPoints

@Composable
fun rememberNotificationHelper(): NotificationHelper {
    val applicationContext = LocalContext.current.applicationContext

    return remember(applicationContext) {
        EntryPoints.get(
            applicationContext,
            NotificationHelperEntryPoint::class.java
        ).notificationHelper
    }
}
