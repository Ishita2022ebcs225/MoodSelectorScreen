package com.example.moodselector

import android.app.Application
import com.example.moodselector.notifications.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoodApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /*
         * --------------------------------------------------
         * NOTIFICATION CHANNEL
         * --------------------------------------------------
         *
         * Notification channels are only required on
         * Android 8.0 (API 26) and above.
         *
         * NotificationHelper handles the API check,
         * so the application remains compatible with
         * minSdk 24.
         */

        NotificationHelper.createNotificationChannel(
            context = this
        )
    }
}
