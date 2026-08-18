package com.example.moodselector.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.moodselector.R

object NotificationHelper {

    private const val CHANNEL_ID =
        "hermind_notifications"

    private const val CHANNEL_NAME =
        "HerMind Notifications"

    private const val CHANNEL_DESCRIPTION =
        "Mood tracking and wellbeing reminders"

    fun createNotificationChannel(
        context: Context
    ) {

        /*
         * Notification channels were introduced in
         * Android 8.0 (API 26).
         *
         * Older Android versions do not need channels.
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationManager =
                context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {

                    description =
                        CHANNEL_DESCRIPTION
                }

            notificationManager.createNotificationChannel(
                channel
            )
        }
    }

    fun showNotification(
        context: Context,
        title: String,
        body: String
    ) {

        /*
         * POST_NOTIFICATIONS is required on
         * Android 13+ when targeting recent SDKs.
         */

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        createNotificationChannel(
            context
        )

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(
                    R.drawable.ic_launcher_foreground
                )
                .setContentTitle(
                    title
                )
                .setContentText(
                    body
                )
                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )
                .setAutoCancel(
                    true
                )
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                System.currentTimeMillis().toInt(),
                notification
            )
    }
}
