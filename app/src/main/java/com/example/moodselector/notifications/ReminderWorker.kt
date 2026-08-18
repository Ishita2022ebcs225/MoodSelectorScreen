package com.example.moodselector.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.moodselector.R

class ReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(
    appContext,
    workerParams
) {

    override fun doWork(): Result {

        val reminderType =
            inputData.getString(
                ReminderScheduler.REMINDER_TYPE
            )
                ?: return Result.failure()

        val reminderTime =
            inputData.getString(
                ReminderScheduler.REMINDER_TIME
            )
                ?: return Result.failure()

        createNotificationChannel()

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            if (
                androidx.core.content.ContextCompat
                    .checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                scheduleNext(
                    reminderType,
                    reminderTime
                )

                return Result.success()
            }
        }

        val title: String
        val message: String

        when (reminderType) {

            ReminderScheduler.MOOD -> {

                title =
                    "Mood check-in"

                message =
                    "Take a moment to check in with how you're feeling."
            }

            ReminderScheduler.JOURNAL -> {

                title =
                    "Journal reminder"

                message =
                    "Take a moment to reflect and write in your journal."
            }

            ReminderScheduler.WELLBEING -> {

                title =
                    "Wellbeing reminder"

                message =
                    "Take a little time for yourself today."
            }

            else -> {

                return Result.failure()
            }
        }

        val notification =
            NotificationCompat
                .Builder(
                    applicationContext,
                    CHANNEL_ID
                )
                .setSmallIcon(
                    R.mipmap.ic_launcher
                )
                .setContentTitle(
                    title
                )
                .setContentText(
                    message
                )
                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )
                .setAutoCancel(
                    true
                )
                .build()

        NotificationManagerCompat
            .from(applicationContext)
            .notify(
                reminderType.hashCode(),
                notification
            )

        scheduleNext(
            reminderType,
            reminderTime
        )

        return Result.success()
    }


    private fun scheduleNext(
        type: String,
        time: String
    ) {

        ReminderScheduler.schedule(
            context = applicationContext,
            type = type,
            time = time
        )
    }


    private fun createNotificationChannel() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {

                    description =
                        "Mood, journal, and wellbeing reminders"
                }

            val notificationManager =
                applicationContext
                    .getSystemService(
                        Context.NOTIFICATION_SERVICE
                    ) as NotificationManager

            notificationManager.createNotificationChannel(
                channel
            )
        }
    }


    companion object {

        private const val CHANNEL_ID =
            "wellbeing_reminders"
    }
}