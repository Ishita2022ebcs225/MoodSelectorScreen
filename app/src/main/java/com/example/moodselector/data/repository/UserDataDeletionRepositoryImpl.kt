package com.example.moodselector.data.repository

import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.domain.repository.ABCModelCompletionRepository
import com.example.moodselector.domain.repository.AssessmentRepository
import com.example.moodselector.domain.repository.CBTProgressRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.FiveMinuteStarterCompletionRepository
import com.example.moodselector.domain.repository.Grounding54321CompletionRepository
import com.example.moodselector.domain.repository.JournalRepository
import com.example.moodselector.domain.repository.MindfulMeditationCompletionRepository
import com.example.moodselector.domain.repository.MoodRepository
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import com.example.moodselector.domain.repository.SelfCompassionReflectionCompletionRepository
import com.example.moodselector.domain.repository.UserDataDeletionRepository
import javax.inject.Inject

class UserDataDeletionRepositoryImpl @Inject constructor(
    private val moodRepository: MoodRepository,
    private val journalRepository: JournalRepository,
    private val assessmentRepository: AssessmentRepository,
    private val cbtProgressRepository: CBTProgressRepository,
    private val scheduledCBTActivityRepository:
    ScheduledCBTActivityRepository,
    private val fiveMinuteStarterCompletionRepository:
    FiveMinuteStarterCompletionRepository,
    private val mindfulMeditationCompletionRepository:
    MindfulMeditationCompletionRepository,
    private val grounding54321CompletionRepository:
    Grounding54321CompletionRepository,
    private val abcModelCompletionRepository:
    ABCModelCompletionRepository,
    private val selfCompassionReflectionCompletionRepository:
    SelfCompassionReflectionCompletionRepository,
    private val userPreferencesRepository:
    UserPreferencesRepository,
    private val cloudBackupRepository:
    CloudBackupRepository
) : UserDataDeletionRepository {

    override suspend fun deleteAllUserData(
        userId: String
    ) {

        /*
         * --------------------------------------------------
         * DELETE LOCAL ROOM DATA
         * --------------------------------------------------
         *
         * Local application data is deleted first so that
         * a cloud deletion failure cannot prevent the user's
         * local data from being removed.
         */

        moodRepository.deleteAllMoods(
            userId
        )

        journalRepository.deleteAllJournals(
            userId
        )

        assessmentRepository.deleteAllResults(
            userId
        )

        cbtProgressRepository.deleteAllCompletions(
            userId
        )

        scheduledCBTActivityRepository
            .deleteAllScheduledActivities(
                userId
            )

        fiveMinuteStarterCompletionRepository
            .deleteAllCompletions(
                userId
            )

        mindfulMeditationCompletionRepository
            .deleteAllCompletions(
                userId
            )

        grounding54321CompletionRepository
            .deleteAllCompletions(
                userId
            )

        abcModelCompletionRepository
            .deleteAllCompletions(
                userId
            )

        selfCompassionReflectionCompletionRepository
            .deleteAllCompletions(
                userId
            )


        /*
         * --------------------------------------------------
         * DELETE USER PREFERENCES
         * --------------------------------------------------
         *
         * Remove all user-specific DataStore preferences.
         */

        userPreferencesRepository
            .clearUserPreferences(
                userId
            )


        /*
         * --------------------------------------------------
         * DELETE FIRESTORE CLOUD DATA
         * --------------------------------------------------
         *
         * Cloud deletion is performed after local deletion.
         *
         * getOrThrow() intentionally remains here.
         *
         * If Firestore deletion fails, the exception propagates
         * to the caller. This prevents AuthViewModel from
         * deleting the Firebase Authentication account while
         * cloud backup data may still exist.
         */

        cloudBackupRepository
            .deleteUserData(
                userId
            )
            .getOrThrow()
    }
}

