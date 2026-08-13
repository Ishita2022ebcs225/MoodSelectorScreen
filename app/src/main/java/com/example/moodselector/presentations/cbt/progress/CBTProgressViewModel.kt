package com.example.moodselector.presentations.cbt.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.domain.repository.CBTProgressRepository
import com.example.moodselector.domain.repository.FiveMinuteStarterCompletionRepository
import com.example.moodselector.domain.repository.MindfulMeditationCompletionRepository
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CBTProgressViewModel @Inject constructor(
    private val repository: CBTProgressRepository,
    private val scheduledRepository: ScheduledCBTActivityRepository,
    private val fiveMinuteStarterRepository:
    FiveMinuteStarterCompletionRepository,
    private val mindfulMeditationRepository:
    MindfulMeditationCompletionRepository
) : ViewModel() {

    /*
     * ==================================================
     * ACTIVITY SCHEDULING / GENERAL CBT COMPLETIONS
     * ==================================================
     */

    val completions:
            Flow<List<CBTActivityCompletionEntity>> =
        repository.getAllCompletions()


    /*
     * ==================================================
     * FIVE-MINUTE STARTER COMPLETIONS
     * ==================================================
     */

    val fiveMinuteStarterCompletions:
            Flow<List<FiveMinuteStarterCompletionEntity>> =
        fiveMinuteStarterRepository.getAllCompletions()


    /*
     * ==================================================
     * MINDFUL MEDITATION COMPLETIONS
     * ==================================================
     */

    val mindfulMeditationCompletions:
            Flow<List<MindfulMeditationCompletionEntity>> =
        mindfulMeditationRepository.getAllCompletions()


    /*
     * ==================================================
     * COMBINED PROGRESS TIMELINE
     * ==================================================
     *
     * Every completion remains visible.
     *
     * Repeating an exercise creates another timeline
     * entry, which preserves the user's practice history.
     */

    val progressItems:
            Flow<List<CBTProgressItem>> =
        combine(
            repository.getAllCompletions(),
            fiveMinuteStarterRepository.getAllCompletions(),
            mindfulMeditationRepository.getAllCompletions()
        ) {
                cbtCompletions,
                starterCompletions,
                meditationCompletions ->

            val activitySchedulingItems =
                cbtCompletions.map { completion ->

                    CBTProgressItem.ActivityCompletion(
                        completion = completion
                    )
                }

            val fiveMinuteStarterItems =
                starterCompletions.map { completion ->

                    CBTProgressItem.FiveMinuteStarterCompletion(
                        completion = completion
                    )
                }

            val mindfulMeditationItems =
                meditationCompletions.map { completion ->

                    CBTProgressItem.MindfulMeditationCompletion(
                        completion = completion
                    )
                }

            (
                    activitySchedulingItems +
                            fiveMinuteStarterItems +
                            mindfulMeditationItems
                    ).sortedByDescending {
                    it.completedAt
                }
        }


    /*
     * ==================================================
     * UNIQUE COMPLETED EXERCISE COUNT
     * ==================================================
     *
     * Counts DIFFERENT exercises completed at least once.
     *
     * Example:
     *
     * Activity Scheduling × 3
     * Five-Minute Starter × 2
     * Mindful Meditation × 4
     *
     * Result:
     *
     * 3 completed exercises
     *
     * Repeating an exercise never increases this count.
     */

    val uniqueCompletedExerciseCount:
            Flow<Int> =
        combine(
            repository.getAllCompletions(),
            fiveMinuteStarterRepository.getAllCompletions(),
            mindfulMeditationRepository.getAllCompletions()
        ) {
                cbtCompletions,
                starterCompletions,
                meditationCompletions ->

            /*
             * ------------------------------------------
             * GENERAL CBT EXERCISES
             * ------------------------------------------
             *
             * Each activityId represents one exercise.
             */

            val uniqueCbtActivities =
                cbtCompletions
                    .map { completion ->
                        completion.activityId
                    }
                    .filter { activityId ->
                        activityId.isNotBlank()
                    }
                    .toSet()
                    .size


            /*
             * ------------------------------------------
             * FIVE-MINUTE STARTER
             * ------------------------------------------
             *
             * All records represent the same exercise.
             */

            val fiveMinuteStarterCount =
                if (starterCompletions.isNotEmpty()) {
                    1
                } else {
                    0
                }


            /*
             * ------------------------------------------
             * MINDFUL MEDITATION
             * ------------------------------------------
             *
             * All records represent the same exercise.
             */

            val mindfulMeditationCount =
                if (meditationCompletions.isNotEmpty()) {
                    1
                } else {
                    0
                }


            /*
             * ------------------------------------------
             * TOTAL UNIQUE EXERCISES
             * ------------------------------------------
             */

            uniqueCbtActivities +
                    fiveMinuteStarterCount +
                    mindfulMeditationCount
        }


    /*
     * ==================================================
     * INDIVIDUAL COMPLETION COUNTS
     * ==================================================
     *
     * These represent actual completion records,
     * rather than unique exercises.
     */

    val completionCount:
            Flow<Int> =
        repository.getCompletionCount()

    val fiveMinuteStarterCompletionCount:
            Flow<Int> =
        fiveMinuteStarterRepository.getCompletionCount()

    val mindfulMeditationCompletionCount:
            Flow<Int> =
        mindfulMeditationRepository.getCompletionCount()


    /*
     * ==================================================
     * SCHEDULED ACTIVITIES
     * ==================================================
     */

    val scheduledActivities:
            Flow<List<ScheduledCBTActivityEntity>> =
        scheduledRepository.getAllScheduledActivities()

    val scheduledActivityCount:
            Flow<Int> =
        scheduledRepository.getScheduledActivityCount()


    /*
     * ==================================================
     * SAVE SCHEDULED ACTIVITY
     * ==================================================
     */

    fun saveScheduledActivity(
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

            val scheduledActivity =
                ScheduledCBTActivityEntity(
                    id = 0,
                    activityId = activityId,
                    activityTitle = activityTitle,
                    activityDescription = activityDescription,
                    activityName = activityName,
                    activityType = activityType,
                    scheduledWhen = scheduledWhen,
                    scheduledWhere = scheduledWhere,
                    createdAt = System.currentTimeMillis()
                )

            scheduledRepository.saveScheduledActivity(
                scheduledActivity
            )

            onSaved()
        }
    }


    /*
     * ==================================================
     * DELETE ONE SCHEDULED ACTIVITY
     * ==================================================
     */

    fun deleteScheduledActivity(
        activity: ScheduledCBTActivityEntity
    ) {

        viewModelScope.launch {

            scheduledRepository.deleteScheduledActivity(
                activity
            )
        }
    }


    /*
     * ==================================================
     * DELETE SCHEDULED ACTIVITY BY ID
     * ==================================================
     */

    fun deleteScheduledActivityById(
        id: Int
    ) {

        viewModelScope.launch {

            scheduledRepository
                .deleteScheduledActivityById(id)
        }
    }


    /*
     * ==================================================
     * DELETE ALL SCHEDULES FOR ACTIVITY
     * ==================================================
     */

    fun deleteScheduledActivityByActivityId(
        activityId: String
    ) {

        viewModelScope.launch {

            scheduledRepository
                .deleteScheduledActivityByActivityId(
                    activityId
                )
        }
    }


    /*
     * ==================================================
     * SAVE ACTIVITY COMPLETION
     * ==================================================
     */

    fun saveActivityCompletion(
        activityId: String,
        activityTitle: String,
        activityDescription: String,
        activityName: String,
        activityType: String,
        scheduledWhen: String,
        scheduledWhere: String,
        reflection: String,
        onSaved: () -> Unit = {}
    ) {

        viewModelScope.launch {

            val completion =
                CBTActivityCompletionEntity(
                    activityId = activityId,
                    activityTitle = activityTitle,
                    activityDescription = activityDescription,
                    activityName = activityName,
                    activityType = activityType,
                    scheduledWhen = scheduledWhen,
                    scheduledWhere = scheduledWhere,
                    reflection = reflection,
                    completedAt = System.currentTimeMillis()
                )

            repository.saveCompletion(
                completion
            )

            onSaved()
        }
    }


    /*
     * ==================================================
     * COMPLETE ONE SCHEDULED ACTIVITY
     * ==================================================
     */

    fun completeScheduledActivity(
        activity: ScheduledCBTActivityEntity,
        reflection: String,
        onCompleted: () -> Unit = {}
    ) {

        viewModelScope.launch {

            val completion =
                CBTActivityCompletionEntity(
                    activityId = activity.activityId,
                    activityTitle = activity.activityTitle,
                    activityDescription =
                        activity.activityDescription,
                    activityName = activity.activityName,
                    activityType = activity.activityType,
                    scheduledWhen = activity.scheduledWhen,
                    scheduledWhere = activity.scheduledWhere,
                    reflection = reflection,
                    completedAt = System.currentTimeMillis()
                )

            repository.saveCompletion(
                completion
            )

            scheduledRepository
                .deleteScheduledActivityById(
                    activity.id
                )

            onCompleted()
        }
    }


    /*
     * ==================================================
     * DELETE CBT COMPLETION
     * ==================================================
     */

    fun deleteCompletion(
        completion: CBTActivityCompletionEntity
    ) {

        viewModelScope.launch {

            repository.deleteCompletion(
                completion
            )
        }
    }


    /*
     * ==================================================
     * DELETE ALL CBT COMPLETIONS
     * ==================================================
     */

    fun deleteAllCompletions() {

        viewModelScope.launch {

            repository.deleteAllCompletions()
        }
    }


    /*
     * ==================================================
     * DELETE ALL SCHEDULED ACTIVITIES
     * ==================================================
     */

    fun deleteAllScheduledActivities() {

        viewModelScope.launch {

            scheduledRepository
                .deleteAllScheduledActivities()
        }
    }
}


/*
 * ======================================================
 * PROGRESS ITEM
 * ======================================================
 *
 * Presentation-layer model used to combine the
 * independent CBT completion systems into one timeline.
 *
 * The database entities remain completely separate.
 */

sealed class CBTProgressItem {

    /*
     * --------------------------------------------------
     * ACTIVITY SCHEDULING / GENERAL CBT COMPLETION
     * --------------------------------------------------
     */

    data class ActivityCompletion(
        val completion: CBTActivityCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() = completion.completedAt
    }


    /*
     * --------------------------------------------------
     * FIVE-MINUTE STARTER COMPLETION
     * --------------------------------------------------
     */

    data class FiveMinuteStarterCompletion(
        val completion: FiveMinuteStarterCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() = completion.completedAt
    }


    /*
     * --------------------------------------------------
     * MINDFUL MEDITATION COMPLETION
     * --------------------------------------------------
     */

    data class MindfulMeditationCompletion(
        val completion: MindfulMeditationCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() = completion.completedAt
    }


    /*
     * --------------------------------------------------
     * COMPLETION TIMESTAMP
     * --------------------------------------------------
     */

    abstract val completedAt: Long
}