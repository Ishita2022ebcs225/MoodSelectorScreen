package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledCBTActivityViewModel @Inject constructor(
    private val repository: ScheduledCBTActivityRepository,
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
     * No reflection is required on this screen.
     */

    fun completeScheduledActivity(
        activity: ScheduledCBTActivityEntity,
        onCompleted: () -> Unit = {}
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.completeScheduledActivity(
                activity
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )

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

