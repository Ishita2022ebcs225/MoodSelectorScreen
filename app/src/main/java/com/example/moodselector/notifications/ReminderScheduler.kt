package com.example.moodselector.notifications

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object ReminderScheduler {

    const val REMINDER_TYPE =
        "reminder_type"

    const val REMINDER_TIME =
        "reminder_time"

    const val MOOD =
        "mood"

    const val JOURNAL =
        "journal"

    const val WELLBEING =
        "wellbeing"


    private fun workName(
        type: String
    ): String =
        "daily_reminder_$type"


    fun schedule(
        context: Context,
        type: String,
        time: String
    ) {

        /*
         * --------------------------------------------------
         * CALCULATE NEXT REMINDER
         * --------------------------------------------------
         *
         * Calendar is used instead of java.time so this
         * works with the application's minSdk of 24.
         */

        val timeParts =
            time.split(":")

        if (timeParts.size != 2) {
            return
        }

        val hour =
            timeParts[0].toIntOrNull()
                ?: return

        val minute =
            timeParts[1].toIntOrNull()
                ?: return

        val now =
            Calendar.getInstance()

        val nextReminder =
            Calendar.getInstance().apply {

                set(
                    Calendar.HOUR_OF_DAY,
                    hour
                )

                set(
                    Calendar.MINUTE,
                    minute
                )

                set(
                    Calendar.SECOND,
                    0
                )

                set(
                    Calendar.MILLISECOND,
                    0
                )

                /*
                 * If today's reminder time has already
                 * passed, schedule it for tomorrow.
                 */

                if (
                    !after(now)
                ) {

                    add(
                        Calendar.DAY_OF_YEAR,
                        1
                    )
                }
            }

        val delay =
            nextReminder.timeInMillis -
                    now.timeInMillis

        /*
         * --------------------------------------------------
         * WORK REQUEST
         * --------------------------------------------------
         */

        val request =
            OneTimeWorkRequestBuilder<ReminderWorker>()

                .setInitialDelay(
                    delay,
                    TimeUnit.MILLISECONDS
                )

                .setInputData(
                    androidx.work.Data.Builder()

                        .putString(
                            REMINDER_TYPE,
                            type
                        )

                        .putString(
                            REMINDER_TIME,
                            time
                        )

                        .build()
                )

                .build()

        /*
         * --------------------------------------------------
         * SCHEDULE UNIQUE REMINDER
         * --------------------------------------------------
         */

        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(

                workName(type),

                ExistingWorkPolicy.REPLACE,

                request
            )
    }


    fun cancel(
        context: Context,
        type: String
    ) {

        WorkManager
            .getInstance(context)
            .cancelUniqueWork(
                workName(type)
            )
    }
}