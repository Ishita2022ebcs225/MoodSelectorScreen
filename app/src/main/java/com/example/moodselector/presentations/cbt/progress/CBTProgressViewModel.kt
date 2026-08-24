package com.example.moodselector.presentations.cbt.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.ABCModelCompletionEntity
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.data.local.entity.CBTDailyProgressEntity
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.data.local.entity.Grounding54321CompletionEntity
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.data.local.entity.SelfCompassionReflectionCompletionEntity
import com.example.moodselector.domain.repository.ABCModelCompletionRepository
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CBTDailyProgressRepository
import com.example.moodselector.domain.repository.CBTProgressRepository
import com.example.moodselector.domain.repository.FiveMinuteStarterCompletionRepository
import com.example.moodselector.domain.repository.Grounding54321CompletionRepository
import com.example.moodselector.domain.repository.MindfulMeditationCompletionRepository
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import com.example.moodselector.domain.repository.SelfCompassionReflectionCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class CBTProgressViewModel @Inject constructor(
    private val repository: CBTProgressRepository,
    private val scheduledRepository: ScheduledCBTActivityRepository,
    private val fiveMinuteStarterRepository:
    FiveMinuteStarterCompletionRepository,
    private val mindfulMeditationRepository:
    MindfulMeditationCompletionRepository,
    private val grounding54321Repository:
    Grounding54321CompletionRepository,
    private val abcModelRepository:
    ABCModelCompletionRepository,
    private val selfCompassionReflectionRepository:
    SelfCompassionReflectionCompletionRepository,
    private val dailyProgressRepository:
    CBTDailyProgressRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    /*
     * ==================================================
     * CURRENT USER ID
     * ==================================================
     */

    private val userId: String?
        get() = authRepository.currentUser?.uid


    /*
     * ==================================================
     * CURRENT DATE
     * ==================================================
     *
     * Stored using the same yyyy-MM-dd format expected
     * by CBTDailyProgressEntity.
     */

    private val currentDate: String
        get() = LocalDate.now().toString()


    /*
     * ==================================================
     * DAILY CBT PROGRESS
     * ==================================================
     *
     * This observes the persisted daily progress record.
     */

    val dailyProgress:
            Flow<CBTDailyProgressEntity?> =
        dailyProgressRepository.observeDailyProgress(
            userId = userId ?: "",
            date = currentDate
        )


    /*
     * ==================================================
     * DAILY CBT PROGRESS FOR SELECTED DATE
     * ==================================================
     *
     * Used by screens such as MoodGraphScreen where
     * the user can select a specific calendar date.
     */

    fun observeDailyProgressForDate(
        date: String
    ): Flow<CBTDailyProgressEntity?> =
        dailyProgressRepository.observeDailyProgress(
            userId = userId ?: "",
            date = date
        )


    /*
     * ==================================================
     * ACTIVITY SCHEDULING / GENERAL CBT COMPLETIONS
     * ==================================================
     */

    val completions:
            Flow<List<CBTActivityCompletionEntity>> =
        repository.getAllCompletions(
            userId = userId ?: ""
        )


    /*
     * ==================================================
     * FIVE-MINUTE STARTER COMPLETIONS
     * ==================================================
     */

    val fiveMinuteStarterCompletions:
            Flow<List<FiveMinuteStarterCompletionEntity>> =
        fiveMinuteStarterRepository.getAllCompletions(
            userId = userId ?: ""
        )


    /*
     * ==================================================
     * MINDFUL MEDITATION COMPLETIONS
     * ==================================================
     */

    val mindfulMeditationCompletions:
            Flow<List<MindfulMeditationCompletionEntity>> =
        mindfulMeditationRepository.getAllCompletions(
            userId = userId ?: ""
        )


    /*
     * ==================================================
     * 5-4-3-2-1 GROUNDING COMPLETIONS
     * ==================================================
     */

    val grounding54321Completions:
            Flow<List<Grounding54321CompletionEntity>> =
        grounding54321Repository.getAllCompletions(
            userId = userId ?: ""
        )


    /*
     * ==================================================
     * ABC MODEL COMPLETIONS
     * ==================================================
     *
     * Only fully completed ABC records are included.
     *
     * A = Activating Event
     * B = Beliefs
     * C = Consequences
     */

    val abcModelCompletions:
            Flow<List<ABCModelCompletionEntity>> =
        abcModelRepository
            .getAllCompletions(
                userId = userId ?: ""
            )
            .map { entries ->
                entries.filter {
                    it.isCompleted()
                }
            }


    /*
     * ==================================================
     * SELF-COMPASSION REFLECTION COMPLETIONS
     * ==================================================
     */

    val selfCompassionReflectionCompletions:
            Flow<List<SelfCompassionReflectionCompletionEntity>> =
        selfCompassionReflectionRepository
            .getAllCompletions(
                userId = userId ?: ""
            )


    /*
     * ==================================================
     * TODAY'S UNIQUE CBT EXERCISE COUNT
     * ==================================================
     *
     * Each DIFFERENT exercise completed today counts once.
     *
     * Repeating the same exercise multiple times today
     * does not increase the count.
     *
     * Example:
     *
     * Five-Minute Starter
     * Five-Minute Starter
     * ABC Model
     *
     * Result = 2
     *
     * This is the count intended for the daily progress
     * display.
     *
     * The flows are combined in smaller groups so Kotlin
     * can correctly infer each individual flow type.
     */

    val dailyCompletionCount:
            Flow<Int> =

        combine(
            completions,
            fiveMinuteStarterCompletions,
            mindfulMeditationCompletions
        ) {
                cbtCompletions,
                starterCompletions,
                meditationCompletions ->

            Triple(
                cbtCompletions,
                starterCompletions,
                meditationCompletions
            )

        }.combine(
            grounding54321Completions
        ) {
                existing,
                groundingCompletions ->

            val uniqueExercises =
                mutableSetOf<String>()

            val cbtCompletions =
                existing.first

            val starterCompletions =
                existing.second

            val meditationCompletions =
                existing.third

            cbtCompletions
                .filter {
                    it.isCompletedToday()
                }
                .forEach { completion ->

                    if (completion.activityId.isNotBlank()) {
                        uniqueExercises.add(
                            "activity:${completion.activityId}"
                        )
                    }
                }

            if (
                starterCompletions.any {
                    it.isCompletedToday()
                }
            ) {
                uniqueExercises.add(
                    "five_minute_starter"
                )
            }

            if (
                meditationCompletions.any {
                    it.isCompletedToday()
                }
            ) {
                uniqueExercises.add(
                    "mindful_meditation"
                )
            }

            if (
                groundingCompletions.any {
                    it.isCompletedToday()
                }
            ) {
                uniqueExercises.add(
                    "grounding_54321"
                )
            }

            uniqueExercises

        }.combine(
            abcModelCompletions
        ) {
                uniqueExercises,
                abcCompletions ->

            if (
                abcCompletions.any {
                    it.isCompletedToday()
                }
            ) {
                uniqueExercises.apply {
                    add("abc_model")
                }
            } else {
                uniqueExercises
            }

        }.combine(
            selfCompassionReflectionCompletions
        ) {
                uniqueExercises,
                selfCompassionCompletions ->

            if (
                selfCompassionCompletions.any {
                    it.isCompletedToday()
                }
            ) {
                uniqueExercises.apply {
                    add("self_compassion_reflection")
                }
            } else {
                uniqueExercises
            }

        }.map {
            it.size
        }


    /*
     * ==================================================
     * COMBINED PROGRESS TIMELINE
     * ==================================================
     *
     * Every completed exercise remains visible.
     *
     * Repeating an exercise creates another timeline
     * entry.
     *
     * Partial ABC entries are excluded.
     */

    val progressItems:
            Flow<List<CBTProgressItem>> =

        combine(
            completions,
            fiveMinuteStarterCompletions,
            mindfulMeditationCompletions,
            grounding54321Completions,
            abcModelCompletions
        ) {
                cbtCompletions,
                starterCompletions,
                meditationCompletions,
                groundingCompletions,
                abcCompletions ->

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

            val grounding54321Items =
                groundingCompletions.map { completion ->
                    CBTProgressItem.Grounding54321Completion(
                        completion = completion
                    )
                }

            val abcModelItems =
                abcCompletions.map { completion ->
                    CBTProgressItem.ABCModelCompletion(
                        completion = completion
                    )
                }

            activitySchedulingItems +
                    fiveMinuteStarterItems +
                    mindfulMeditationItems +
                    grounding54321Items +
                    abcModelItems

        }.combine(
            selfCompassionReflectionCompletions
        ) { existingItems, selfCompassionCompletions ->

            val selfCompassionItems =
                selfCompassionCompletions.map { completion ->
                    CBTProgressItem.SelfCompassionReflectionCompletion(
                        completion = completion
                    )
                }

            (
                    existingItems +
                            selfCompassionItems
                    ).sortedByDescending {
                    it.completedAt
                }
        }


    /*
     * ==================================================
     * UNIQUE COMPLETED EXERCISE COUNT
     * ==================================================
     *
     * Counts DIFFERENT exercises completed at least once
     * across the user's complete CBT history.
     *
     * Repeating the same exercise does not increase this
     * number.
     */

    val uniqueCompletedExerciseCount:
            Flow<Int> =

        combine(
            completions,
            fiveMinuteStarterCompletions,
            mindfulMeditationCompletions,
            grounding54321Completions,
            abcModelCompletions
        ) {
                cbtCompletions,
                starterCompletions,
                meditationCompletions,
                groundingCompletions,
                abcCompletions ->

            val uniqueCbtActivities =
                cbtCompletions
                    .map {
                        it.activityId
                    }
                    .filter {
                        it.isNotBlank()
                    }
                    .toSet()
                    .size

            val fiveMinuteStarterCount =
                if (starterCompletions.isNotEmpty()) {
                    1
                } else {
                    0
                }

            val mindfulMeditationCount =
                if (meditationCompletions.isNotEmpty()) {
                    1
                } else {
                    0
                }

            val grounding54321Count =
                if (groundingCompletions.isNotEmpty()) {
                    1
                } else {
                    0
                }

            val abcModelCount =
                if (abcCompletions.isNotEmpty()) {
                    1
                } else {
                    0
                }

            uniqueCbtActivities +
                    fiveMinuteStarterCount +
                    mindfulMeditationCount +
                    grounding54321Count +
                    abcModelCount

        }.combine(
            selfCompassionReflectionCompletions
        ) { existingCount, selfCompassionCompletions ->

            val selfCompassionCount =
                if (selfCompassionCompletions.isNotEmpty()) {
                    1
                } else {
                    0
                }

            existingCount +
                    selfCompassionCount
        }


    /*
     * ==================================================
     * INDIVIDUAL COMPLETION COUNTS
     * ==================================================
     */

    val completionCount:
            Flow<Int> =
        repository.getCompletionCount(
            userId = userId ?: ""
        )


    val fiveMinuteStarterCompletionCount:
            Flow<Int> =
        fiveMinuteStarterRepository.getCompletionCount(
            userId = userId ?: ""
        )


    val mindfulMeditationCompletionCount:
            Flow<Int> =
        mindfulMeditationRepository.getCompletionCount(
            userId = userId ?: ""
        )


    val grounding54321CompletionCount:
            Flow<Int> =
        grounding54321Repository.getCompletionCount(
            userId = userId ?: ""
        )


    /*
     * ==================================================
     * ABC MODEL COMPLETION COUNT
     * ==================================================
     */

    val abcModelCompletionCount:
            Flow<Int> =
        abcModelCompletions.map {
            it.size
        }


    /*
     * ==================================================
     * SELF-COMPASSION REFLECTION COMPLETION COUNT
     * ==================================================
     */

    val selfCompassionReflectionCompletionCount:
            Flow<Int> =
        selfCompassionReflectionCompletions.map {
            it.size
        }


    /*
     * ==================================================
     * SCHEDULED ACTIVITIES
     * ==================================================
     */

    val scheduledActivities:
            Flow<List<ScheduledCBTActivityEntity>> =
        scheduledRepository.getAllScheduledActivities(
            userId = userId ?: ""
        )


    val scheduledActivityCount:
            Flow<Int> =
        scheduledRepository.getScheduledActivityCount(
            userId = userId ?: ""
        )


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

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            val scheduledActivity =
                ScheduledCBTActivityEntity(
                    id = 0,
                    userId = currentUserId,
                    activityId = activityId,
                    activityTitle = activityTitle,
                    activityDescription = activityDescription,
                    activityName = activityName,
                    activityType = activityType,
                    scheduledWhen = scheduledWhen,
                    scheduledWhere = scheduledWhere,
                    createdAt =
                        System.currentTimeMillis()
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

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            scheduledRepository
                .deleteScheduledActivityById(
                    id = id,
                    userId = currentUserId
                )
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

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            scheduledRepository
                .deleteScheduledActivityByActivityId(
                    activityId = activityId,
                    userId = currentUserId
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

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            val completion =
                CBTActivityCompletionEntity(
                    userId = currentUserId,
                    activityId = activityId,
                    activityTitle = activityTitle,
                    activityDescription = activityDescription,
                    activityName = activityName,
                    activityType = activityType,
                    scheduledWhen = scheduledWhen,
                    scheduledWhere = scheduledWhere,
                    reflection = reflection,
                    completedAt =
                        System.currentTimeMillis()
                )

            repository.saveCompletion(
                completion
            )

            /*
             * The persisted daily progress record is still
             * incremented for every explicit completion.
             */

            dailyProgressRepository.incrementDailyCompletion(
                userId = currentUserId,
                date = currentDate
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
                    userId =
                        activity.userId,

                    activityId =
                        activity.activityId,

                    activityTitle =
                        activity.activityTitle,

                    activityDescription =
                        activity.activityDescription,

                    activityName =
                        activity.activityName,

                    activityType =
                        activity.activityType,

                    scheduledWhen =
                        activity.scheduledWhen,

                    scheduledWhere =
                        activity.scheduledWhere,

                    reflection =
                        reflection,

                    completedAt =
                        System.currentTimeMillis()
                )

            repository.saveCompletion(
                completion
            )

            /*
             * The persisted daily progress record is still
             * incremented for every explicit completion.
             */

            dailyProgressRepository.incrementDailyCompletion(
                userId = activity.userId,
                date = currentDate
            )

            /*
             * The scheduled activity itself is removed
             * after its completion has been persisted.
             */

            scheduledRepository
                .deleteScheduledActivityById(
                    id = activity.id,
                    userId = activity.userId
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

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteAllCompletions(
                userId = currentUserId
            )
        }
    }


    /*
     * ==================================================
     * DELETE ALL SCHEDULED ACTIVITIES
     * ==================================================
     */

    fun deleteAllScheduledActivities() {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            scheduledRepository
                .deleteAllScheduledActivities(
                    userId = currentUserId
                )
        }
    }


    /*
     * ==================================================
     * DELETE ABC COMPLETION
     * ==================================================
     */

    fun deleteABCModelCompletion(
        completion: ABCModelCompletionEntity
    ) {

        viewModelScope.launch {

            abcModelRepository.deleteCompletion(
                completion
            )
        }
    }


    /*
     * ==================================================
     * DELETE ALL ABC COMPLETIONS
     * ==================================================
     */

    fun deleteAllABCModelCompletions() {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            abcModelRepository.deleteAllCompletions(
                userId = currentUserId
            )
        }
    }


    /*
     * ==================================================
     * DELETE SELF-COMPASSION REFLECTION COMPLETION
     * ==================================================
     */

    fun deleteSelfCompassionReflectionCompletion(
        completion:
        SelfCompassionReflectionCompletionEntity
    ) {

        viewModelScope.launch {

            selfCompassionReflectionRepository
                .deleteCompletion(
                    completion
                )
        }
    }


    /*
     * ==================================================
     * DELETE ALL SELF-COMPASSION REFLECTION COMPLETIONS
     * ==================================================
     */

    fun deleteAllSelfCompassionReflectionCompletions() {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            selfCompassionReflectionRepository
                .deleteAllCompletions(
                    userId = currentUserId
                )
        }
    }


    /*
     * ==================================================
     * DELETE ALL DAILY CBT PROGRESS
     * ==================================================
     *
     * Deletes only the daily progress belonging to the
     * currently authenticated Firebase user.
     */

    fun deleteAllDailyProgress() {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            dailyProgressRepository
                .deleteAllDailyProgress(
                    userId = currentUserId
                )
        }
    }
}


/*
 * ======================================================
 * DAILY COMPLETION HELPERS
 * ======================================================
 */

private fun Long.isCompletedToday(): Boolean {

    val completionDate =
        Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

    return completionDate == LocalDate.now()
}


private fun CBTActivityCompletionEntity.isCompletedToday(): Boolean =
    completedAt.isCompletedToday()


private fun FiveMinuteStarterCompletionEntity.isCompletedToday(): Boolean =
    completedAt.isCompletedToday()


private fun MindfulMeditationCompletionEntity.isCompletedToday(): Boolean =
    completedAt.isCompletedToday()


private fun Grounding54321CompletionEntity.isCompletedToday(): Boolean =
    completedAt.isCompletedToday()


private fun ABCModelCompletionEntity.isCompletedToday(): Boolean =
    completedAt.isCompletedToday()


private fun SelfCompassionReflectionCompletionEntity.isCompletedToday(): Boolean =
    completedAt.isCompletedToday()


/*
 * ======================================================
 * ABC COMPLETION CHECK
 * ======================================================
 *
 * A record is considered complete only when all three
 * ABC sections contain a non-blank response.
 */

private fun ABCModelCompletionEntity.isCompleted(): Boolean {

    return activatingEvent.isNotBlank() &&
            beliefs.isNotBlank() &&
            consequences.isNotBlank()
}


/*
 * ======================================================
 * PROGRESS ITEM
 * ======================================================
 *
 * Presentation-layer model used to combine the
 * independent CBT completion systems into one timeline.
 */

sealed class CBTProgressItem {

    data class ActivityCompletion(
        val completion:
        CBTActivityCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() =
                completion.completedAt
    }


    data class FiveMinuteStarterCompletion(
        val completion:
        FiveMinuteStarterCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() =
                completion.completedAt
    }


    data class MindfulMeditationCompletion(
        val completion:
        MindfulMeditationCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() =
                completion.completedAt
    }


    data class Grounding54321Completion(
        val completion:
        Grounding54321CompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() =
                completion.completedAt
    }


    data class ABCModelCompletion(
        val completion:
        ABCModelCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() =
                completion.completedAt
    }


    data class SelfCompassionReflectionCompletion(
        val completion:
        SelfCompassionReflectionCompletionEntity
    ) : CBTProgressItem() {

        override val completedAt: Long
            get() =
                completion.completedAt
    }


    abstract val completedAt: Long
}