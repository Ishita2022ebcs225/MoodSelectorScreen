package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledCBTActivityViewModel @Inject constructor(
    private val repository: ScheduledCBTActivityRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * ALL SCHEDULED ACTIVITIES
     * --------------------------------------------------
     */

    val scheduledActivities:
            Flow<List<ScheduledCBTActivityEntity>> =
        repository.getAllScheduledActivities()


    /*
     * --------------------------------------------------
     * SCHEDULED ACTIVITY COUNT
     * --------------------------------------------------
     */

    val scheduledActivityCount:
            Flow<Int> =
        repository.getScheduledActivityCount()


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

        return repository.getScheduledActivityById(
            id
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

        viewModelScope.launch {

            /*
             * Retrieve the existing record when
             * editing so that createdAt is preserved.
             */

            val existingActivity =
                if (id != 0) {

                    repository.getScheduledActivityById(
                        id
                    )

                } else {
                    null
                }


            val activity =
                ScheduledCBTActivityEntity(

                    id = id,

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

        viewModelScope.launch {

            repository.completeScheduledActivity(
                activity
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

        viewModelScope.launch {

            repository.deleteScheduledActivity(
                activity
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

        viewModelScope.launch {

            repository.deleteScheduledActivityById(
                id
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

        viewModelScope.launch {

            repository.deleteScheduledActivityByActivityId(
                activityId
            )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ALL SCHEDULED ACTIVITIES
     * --------------------------------------------------
     */

    fun deleteAllScheduledActivities() {

        viewModelScope.launch {

            repository.deleteAllScheduledActivities()
        }
    }
}