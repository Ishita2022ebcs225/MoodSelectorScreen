package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CBTDailyProgressRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ScheduledCBTActivityViewModel @Inject constructor(
    private val repository: ScheduledCBTActivityRepository,
    private val dailyProgressRepository: CBTDailyProgressRepository,
    private val authRepository: AuthRepository,
    private val cloudBackupRepository: CloudBackupRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * CURRENT USER ID
     * --------------------------------------------------
     */

    private val userId: String?
        get() = authRepository.currentUser?.uid


    /*
     * --------------------------------------------------
     * ALL SCHEDULED ACTIVITIES
     * --------------------------------------------------
     */

    val scheduledActivities:
            Flow<List<ScheduledCBTActivityEntity>> =
        repository.getAllScheduledActivities(
            userId = userId ?: ""
        )


    /*
     * --------------------------------------------------
     * SCHEDULED ACTIVITY COUNT
     * --------------------------------------------------
     */

    val scheduledActivityCount:
            Flow<Int> =
        repository.getScheduledActivityCount(
            userId = userId ?: ""
        )


    /*
     * --------------------------------------------------
     * GET ONE SCHEDULED ACTIVITY
     * --------------------------------------------------
     *
     * Used when editing an existing scheduled activity.
     */

    suspend fun getScheduledActivityById(
        id: Int
    ): ScheduledCBTActivityEntity? {

        val currentUserId =
            userId ?: return null

        return repository.getScheduledActivityById(
            id = id,
            userId = currentUserId
        )
    }


    /*
     * --------------------------------------------------
     * SAVE NEW OR UPDATED SCHEDULED ACTIVITY
     * --------------------------------------------------
     *
     * id == 0:
     *     Creates a new scheduled activity.
     *
     * id != 0:
     *     Updates the existing scheduled activity.
     *
     * Saving or editing a scheduled activity does NOT
     * count as a CBT completion.
     */

    fun saveScheduledActivity(
        id: Int = 0,
        activityId: String,
        activityTitle: String,
        activityDescription: String,
        activityName: String,
        activityType: String,
        scheduledWhen: String,
        scheduledWhere: String,
        onSaved: () -> Unit = {}
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            /*
             * Retrieve the existing record when
             * editing so that createdAt is preserved.
             */

            val existingActivity =
                if (id != 0) {

                    repository.getScheduledActivityById(
                        id = id,
                        userId = currentUserId
                    )

                } else {
                    null
                }


            val activity =
                ScheduledCBTActivityEntity(

                    id = id,

                    userId =
                        currentUserId,

                    activityId =
                        activityId,

                    activityTitle =
                        activityTitle,

                    activityDescription =
                        activityDescription,

                    activityName =
                        activityName,

                    activityType =
                        activityType,

                    scheduledWhen =
                        scheduledWhen,

                    scheduledWhere =
                        scheduledWhere,

                    createdAt =
                        existingActivity?.createdAt
                            ?: System.currentTimeMillis()
                )


            repository.saveScheduledActivity(
                activity
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )


            onSaved()
        }
    }


    /*
     * --------------------------------------------------
     * COMPLETE ONE SCHEDULED ACTIVITY
     * --------------------------------------------------
     *
     * This is called from ScheduledActivitiesScreen.
     *
     * The repository:
     *
     * 1. Creates a CBTActivityCompletionEntity.
     * 2. Saves it to CBT Progress.
     * 3. Deletes only this scheduled instance.
     *
     * After the completion is successfully persisted,
     * the user's daily CBT progress is incremented once.
     */

    fun completeScheduledActivity(
        activity: ScheduledCBTActivityEntity,
        onCompleted: () -> Unit = {}
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            /*
             * Persist the actual CBT completion and remove
             * this scheduled instance.
             */

            repository.completeScheduledActivity(
                activity
            )

            /*
             * --------------------------------------------------
             * UPDATE DAILY CBT PROGRESS
             * --------------------------------------------------
             *
             * Daily progress is based on the date on which
             * the completion is recorded.
             *
             * Format:
             * yyyy-MM-dd
             */

            val currentDate =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.US
                ).format(
                    Date()
                )

            /*
             * Daily progress is supplementary to the actual
             * completion record. If it fails, the completion
             * itself remains successfully persisted.
             */

            runCatching {

                dailyProgressRepository
                    .incrementDailyCompletion(
                        userId = currentUserId,
                        date = currentDate
                    )
            }

            /*
             * Cloud backup remains best-effort.
             */

            runCatching {

                cloudBackupRepository
                    .backupUserData(
                        userId = currentUserId
                    )
            }

            onCompleted()
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ONE SCHEDULED ACTIVITY
     * --------------------------------------------------
     */

    fun deleteScheduledActivity(
        activity: ScheduledCBTActivityEntity
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteScheduledActivity(
                activity
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ONE BY DATABASE ID
     * --------------------------------------------------
     */

    fun deleteScheduledActivityById(
        id: Int
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteScheduledActivityById(
                id = id,
                userId = currentUserId
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ALL INSTANCES FOR CBT ACTIVITY
     * --------------------------------------------------
     */

    fun deleteScheduledActivityByActivityId(
        activityId: String
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteScheduledActivityByActivityId(
                activityId = activityId,
                userId = currentUserId
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ALL SCHEDULED ACTIVITIES
     * --------------------------------------------------
     */

    fun deleteAllScheduledActivities() {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteAllScheduledActivities(
                currentUserId
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }
}