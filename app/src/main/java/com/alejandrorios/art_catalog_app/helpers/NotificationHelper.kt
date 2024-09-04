package com.alejandrorios.art_catalog_app.helpers

import android.app.NotificationManager
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.alejandrorios.art_catalog_app.R
import javax.inject.Inject
import kotlin.random.Random

const val notificationChannelID = "artwork_catalog_app_channel_id"

class NotificationHelper @Inject constructor(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showSimpleNotification(@StringRes message: Int) {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(message))
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}

