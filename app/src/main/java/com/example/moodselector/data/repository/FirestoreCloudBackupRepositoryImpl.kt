package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.ABCModelCompletionDao
import com.example.moodselector.data.local.dao.AssessmentResultDao
import com.example.moodselector.data.local.dao.CBTActivityCompletionDao
import com.example.moodselector.data.local.dao.CBTDailyProgressDao
import com.example.moodselector.data.local.dao.FiveMinuteStarterCompletionDao
import com.example.moodselector.data.local.dao.Grounding54321CompletionDao
import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.dao.MindfulMeditationCompletionDao
import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.dao.ScheduledCBTActivityDao
import com.example.moodselector.data.local.dao.SelfCompassionReflectionCompletionDao
import com.example.moodselector.data.local.entity.ABCModelCompletionEntity
import com.example.moodselector.data.local.entity.AssessmentResultEntity
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.data.local.entity.CBTDailyProgressEntity
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.data.local.entity.Grounding54321CompletionEntity
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import com.example.moodselector.data.local.entity.MoodEntry
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.data.local.entity.SelfCompassionReflectionCompletionEntity
import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.domain.assessment.model.AssessmentSeverity
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreCloudBackupRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val moodDao: MoodDao,
    private val journalDao: JournalDao,
    private val assessmentResultDao: AssessmentResultDao,
    private val cbtActivityCompletionDao: CBTActivityCompletionDao,
    private val scheduledCBTActivityDao: ScheduledCBTActivityDao,
    private val fiveMinuteStarterCompletionDao:
    FiveMinuteStarterCompletionDao,
    private val mindfulMeditationCompletionDao:
    MindfulMeditationCompletionDao,
    private val grounding54321CompletionDao:
    Grounding54321CompletionDao,
    private val abcModelCompletionDao:
    ABCModelCompletionDao,
    private val selfCompassionReflectionCompletionDao:
    SelfCompassionReflectionCompletionDao,
    private val cbtDailyProgressDao:
    CBTDailyProgressDao,
    private val userPreferencesRepository:
    UserPreferencesRepository
) : CloudBackupRepository {


    /*
     * --------------------------------------------------
     * FIRESTORE STRUCTURE
     * --------------------------------------------------
     *
     * users/{userId}/moods/{localRoomId}
     * users/{userId}/journals/{localRoomId}
     * users/{userId}/assessment_results/{localRoomId}
     * users/{userId}/cbt_activity_completions/{localRoomId}
     * users/{userId}/scheduled_cbt_activities/{localRoomId}
     * users/{userId}/five_minute_starter_completions/{localRoomId}
     * users/{userId}/mindful_meditation_completions/{localRoomId}
     * users/{userId}/grounding_54321_completions/{localRoomId}
     * users/{userId}/abc_model_completions/{localRoomId}
     * users/{userId}/self_compassion_reflection_completions/{localRoomId}
     * users/{userId}/cbt_daily_progress/{localRoomId}
     *
     * Room remains the local data source.
     *
     * Firestore is used as cloud backup and restoration.
     *
     * The Room primary key is used as the Firestore
     * document ID.
     */


    /*
     * --------------------------------------------------
     * BACKUP USER DATA
     * --------------------------------------------------
     */

    override suspend fun backupUserData(
        userId: String
    ): Result<Unit> {

        return try {

            if (userId.isBlank()) {
                return Result.failure(
                    IllegalArgumentException(
                        "A valid Firebase user ID is required."
                    )
                )
            }

            backupMoods(userId)
            backupJournals(userId)
            backupAssessmentResults(userId)
            backupCBTActivityCompletions(userId)
            backupScheduledCBTActivities(userId)
            backupFiveMinuteStarterCompletions(userId)
            backupMindfulMeditationCompletions(userId)
            backupGrounding54321Completions(userId)
            backupABCModelCompletions(userId)
            backupSelfCompassionReflectionCompletions(userId)
            backupCBTDailyProgress(userId)

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)
        }
    }


    /*
     * --------------------------------------------------
     * RESTORE USER DATA
     * --------------------------------------------------
     */

    override suspend fun restoreUserData(
        userId: String
    ): Result<Unit> {

        return try {

            if (userId.isBlank()) {
                return Result.failure(
                    IllegalArgumentException(
                        "A valid Firebase user ID is required."
                    )
                )
            }

            restoreMoods(userId)
            restoreJournals(userId)
            restoreAssessmentResults(userId)
            restoreCBTActivityCompletions(userId)
            restoreScheduledCBTActivities(userId)
            restoreFiveMinuteStarterCompletions(userId)
            restoreMindfulMeditationCompletions(userId)
            restoreGrounding54321Completions(userId)
            restoreABCModelCompletions(userId)
            restoreSelfCompassionReflectionCompletions(userId)
            restoreCBTDailyProgress(userId)

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ALL CLOUD USER DATA
     * --------------------------------------------------
     */

    override suspend fun deleteUserData(
        userId: String
    ): Result<Unit> {

        return try {

            if (userId.isBlank()) {
                return Result.failure(
                    IllegalArgumentException(
                        "A valid Firebase user ID is required."
                    )
                )
            }

            val userDocument =
                firestore
                    .collection("users")
                    .document(userId)

            deleteCollectionDocuments(
                userDocument.collection("moods")
            )

            deleteCollectionDocuments(
                userDocument.collection("journals")
            )

            deleteCollectionDocuments(
                userDocument.collection("assessment_results")
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "cbt_activity_completions"
                )
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "scheduled_cbt_activities"
                )
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "five_minute_starter_completions"
                )
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "mindful_meditation_completions"
                )
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "grounding_54321_completions"
                )
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "abc_model_completions"
                )
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "self_compassion_reflection_completions"
                )
            )

            deleteCollectionDocuments(
                userDocument.collection(
                    "cbt_daily_progress"
                )
            )

            userDocument
                .delete()
                .await()

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)
        }
    }


    /*
     * --------------------------------------------------
     * DELETE FIRESTORE COLLECTION DOCUMENTS
     * --------------------------------------------------
     *
     * Firestore batches support a maximum of 500 writes.
     * Documents are therefore deleted in batches of
     * at most 500 until the collection is empty.
     */

    private suspend fun deleteCollectionDocuments(
        collection: CollectionReference
    ) {

        while (true) {

            val snapshot =
                collection
                    .limit(500)
                    .get()
                    .await()

            if (snapshot.isEmpty) {
                break
            }

            val batch =
                firestore.batch()

            snapshot.documents.forEach { document ->

                batch.delete(
                    document.reference
                )
            }

            batch.commit().await()
        }
    }


    /*
     * --------------------------------------------------
     * SYNC USER DATA
     * --------------------------------------------------
     */

    override suspend fun syncUserData(
        userId: String
    ): Result<Unit> {

        return try {

            if (userId.isBlank()) {
                return Result.failure(
                    IllegalArgumentException(
                        "A valid Firebase user ID is required."
                    )
                )
            }

            val hasLocalData =
                hasLocalData(userId)

            if (!hasLocalData) {

                val restoreResult =
                    restoreUserData(userId)

                if (restoreResult.isFailure) {
                    return restoreResult
                }

                return backupUserData(userId)
            }

            val backupResult =
                backupUserData(userId)

            if (backupResult.isFailure) {
                return backupResult
            }

            restoreUserData(userId)

        } catch (exception: Exception) {

            Result.failure(exception)
        }
    }


    /*
     * --------------------------------------------------
     * CHECK WHETHER ROOM CONTAINS LOCAL USER DATA
     * --------------------------------------------------
     */

    private suspend fun hasLocalData(
        userId: String
    ): Boolean {

        if (
            moodDao
                .getAllMoods(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            journalDao
                .getAllJournals(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            assessmentResultDao
                .getAllResults(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            cbtActivityCompletionDao
                .getAllCompletions(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            scheduledCBTActivityDao
                .getAllScheduledActivities(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            fiveMinuteStarterCompletionDao
                .getAllCompletions(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            mindfulMeditationCompletionDao
                .getAllCompletions(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            grounding54321CompletionDao
                .getAllCompletions(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            abcModelCompletionDao
                .getAllCompletions(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        if (
            selfCompassionReflectionCompletionDao
                .getAllCompletions(userId)
                .first()
                .isNotEmpty()
        ) {
            return true
        }

        return cbtDailyProgressDao
            .observeDailyProgress(
                userId = userId,
                date = ""
            )
            .first() != null
    }


    /*
     * ==================================================
     * MOODS
     * ==================================================
     */

    private suspend fun backupMoods(
        userId: String
    ) {

        val moods =
            moodDao
                .getAllMoods(userId)
                .first()

        val batch =
            firestore.batch()

        moods.forEach { mood ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("moods")
                    .document(mood.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to mood.id,
                    "userId" to mood.userId,
                    "mood" to mood.mood,
                    "emoji" to mood.emoji,
                    "trigger" to mood.trigger,
                    "timestamp" to mood.timestamp
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreMoods(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("moods")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val mood =
                MoodEntry(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    mood =
                        document.getString("mood")
                            ?: "",

                    emoji =
                        document.getString("emoji")
                            ?: "",

                    trigger =
                        document.getString("trigger")
                            ?: "",

                    timestamp =
                        document.getString("timestamp")
                            ?: ""
                )

            moodDao.insertMood(mood)
        }
    }


    /*
     * ==================================================
     * JOURNALS
     * ==================================================
     */

    private suspend fun backupJournals(
        userId: String
    ) {

        val journals =
            journalDao
                .getAllJournals(userId)
                .first()

        val journalsCollection =
            firestore
                .collection("users")
                .document(userId)
                .collection("journals")

        val existingSnapshot =
            journalsCollection
                .get()
                .await()

        val localJournalIds =
            journals
                .map { it.id.toString() }
                .toSet()

        val batch =
            firestore.batch()

        existingSnapshot.documents.forEach { document ->

            if (document.id !in localJournalIds) {
                batch.delete(document.reference)
            }
        }

        journals.forEach { journal ->

            val document =
                journalsCollection
                    .document(journal.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to journal.id,
                    "userId" to journal.userId,
                    "content" to journal.content,
                    "mood" to journal.mood,
                    "timestamp" to journal.timestamp
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreJournals(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("journals")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val journal =
                JournalEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    content =
                        document.getString("content")
                            ?: "",

                    mood =
                        document.getString("mood")
                            ?: "",

                    timestamp =
                        document.getLong("timestamp")
                            ?: 0L
                )

            journalDao.insertJournal(journal)
        }
    }


    /*
     * ==================================================
     * ASSESSMENT RESULTS
     * ==================================================
     */

    private suspend fun backupAssessmentResults(
        userId: String
    ) {

        val results =
            assessmentResultDao
                .getAllResults(userId)
                .first()

        val batch =
            firestore.batch()

        results.forEach { result ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("assessment_results")
                    .document(result.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to result.id,
                    "userId" to result.userId,
                    "timestamp" to result.timestamp,
                    "phq9Score" to result.phq9Score,
                    "phq9Severity" to result.phq9Severity.name,
                    "gad7Score" to result.gad7Score,
                    "gad7Severity" to result.gad7Severity.name,
                    "diagnosisSummary" to result.diagnosisSummary
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreAssessmentResults(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("assessment_results")
                .get()
                .await()

        if (snapshot.documents.isNotEmpty()) {

            userPreferencesRepository
                .setAssessmentCompleted(
                    userId = userId,
                    completed = true
                )
        }

        snapshot.documents.forEach { document ->

            val phq9Severity =
                document
                    .getString("phq9Severity")
                    ?.let {
                        runCatching {
                            AssessmentSeverity.valueOf(it)
                        }.getOrNull()
                    }
                    ?: AssessmentSeverity.MINIMAL

            val gad7Severity =
                document
                    .getString("gad7Severity")
                    ?.let {
                        runCatching {
                            AssessmentSeverity.valueOf(it)
                        }.getOrNull()
                    }
                    ?: AssessmentSeverity.MINIMAL

            val result =
                AssessmentResultEntity(
                    id =
                        document.getLong("id")
                            ?: document.id.toLong(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    timestamp =
                        document.getLong("timestamp")
                            ?: 0L,

                    phq9Score =
                        document.getLong("phq9Score")
                            ?.toInt()
                            ?: 0,

                    phq9Severity =
                        phq9Severity,

                    gad7Score =
                        document.getLong("gad7Score")
                            ?.toInt()
                            ?: 0,

                    gad7Severity =
                        gad7Severity,

                    diagnosisSummary =
                        document.getString("diagnosisSummary")
                            ?: ""
                )

            assessmentResultDao.insertResult(result)
        }
    }


    /*
     * ==================================================
     * CBT ACTIVITY COMPLETIONS
     * ==================================================
     */

    private suspend fun backupCBTActivityCompletions(
        userId: String
    ) {

        val completions =
            cbtActivityCompletionDao
                .getAllCompletions(userId)
                .first()

        val batch =
            firestore.batch()

        completions.forEach { completion ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("cbt_activity_completions")
                    .document(completion.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to completion.id,
                    "userId" to completion.userId,
                    "activityId" to completion.activityId,
                    "activityTitle" to completion.activityTitle,
                    "activityDescription" to completion.activityDescription,
                    "activityName" to completion.activityName,
                    "activityType" to completion.activityType,
                    "scheduledWhen" to completion.scheduledWhen,
                    "scheduledWhere" to completion.scheduledWhere,
                    "reflection" to completion.reflection,
                    "completedAt" to completion.completedAt
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreCBTActivityCompletions(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("cbt_activity_completions")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val completion =
                CBTActivityCompletionEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    activityId =
                        document.getString("activityId")
                            ?: "",

                    activityTitle =
                        document.getString("activityTitle")
                            ?: "",

                    activityDescription =
                        document.getString("activityDescription")
                            ?: "",

                    activityName =
                        document.getString("activityName")
                            ?: "",

                    activityType =
                        document.getString("activityType")
                            ?: "",

                    scheduledWhen =
                        document.getString("scheduledWhen")
                            ?: "",

                    scheduledWhere =
                        document.getString("scheduledWhere")
                            ?: "",

                    reflection =
                        document.getString("reflection")
                            ?: "",

                    completedAt =
                        document.getLong("completedAt")
                            ?: 0L
                )

            cbtActivityCompletionDao.insertCompletion(completion)
        }
    }


    /*
     * ==================================================
     * SCHEDULED CBT ACTIVITIES
     * ==================================================
     */

    private suspend fun backupScheduledCBTActivities(
        userId: String
    ) {

        val activities =
            scheduledCBTActivityDao
                .getAllScheduledActivities(userId)
                .first()

        val batch =
            firestore.batch()

        activities.forEach { activity ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("scheduled_cbt_activities")
                    .document(activity.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to activity.id,
                    "userId" to activity.userId,
                    "activityId" to activity.activityId,
                    "activityTitle" to activity.activityTitle,
                    "activityDescription" to activity.activityDescription,
                    "activityName" to activity.activityName,
                    "activityType" to activity.activityType,
                    "scheduledWhen" to activity.scheduledWhen,
                    "scheduledWhere" to activity.scheduledWhere,
                    "createdAt" to activity.createdAt
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreScheduledCBTActivities(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("scheduled_cbt_activities")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val activity =
                ScheduledCBTActivityEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    activityId =
                        document.getString("activityId")
                            ?: "",

                    activityTitle =
                        document.getString("activityTitle")
                            ?: "",

                    activityDescription =
                        document.getString("activityDescription")
                            ?: "",

                    activityName =
                        document.getString("activityName")
                            ?: "",

                    activityType =
                        document.getString("activityType")
                            ?: "",

                    scheduledWhen =
                        document.getString("scheduledWhen")
                            ?: "",

                    scheduledWhere =
                        document.getString("scheduledWhere")
                            ?: "",

                    createdAt =
                        document.getLong("createdAt")
                            ?: System.currentTimeMillis()
                )

            scheduledCBTActivityDao.insertScheduledActivity(activity)
        }
    }


    /*
     * ==================================================
     * FIVE-MINUTE STARTER
     * ==================================================
     */

    private suspend fun backupFiveMinuteStarterCompletions(
        userId: String
    ) {

        val completions =
            fiveMinuteStarterCompletionDao
                .getAllCompletions(userId)
                .first()

        val batch =
            firestore.batch()

        completions.forEach { completion ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("five_minute_starter_completions")
                    .document(completion.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to completion.id,
                    "userId" to completion.userId,
                    "task" to completion.task,
                    "firstStep" to completion.firstStep,
                    "outcome" to completion.outcome,
                    "reflection" to completion.reflection,
                    "completedAt" to completion.completedAt
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreFiveMinuteStarterCompletions(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("five_minute_starter_completions")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val completion =
                FiveMinuteStarterCompletionEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    task =
                        document.getString("task")
                            ?: "",

                    firstStep =
                        document.getString("firstStep")
                            ?: "",

                    outcome =
                        document.getString("outcome")
                            ?: "",

                    reflection =
                        document.getString("reflection")
                            ?: "",

                    completedAt =
                        document.getLong("completedAt")
                            ?: 0L
                )

            fiveMinuteStarterCompletionDao.insertCompletion(completion)
        }
    }


    /*
     * ==================================================
     * MINDFUL MEDITATION
     * ==================================================
     */

    private suspend fun backupMindfulMeditationCompletions(
        userId: String
    ) {

        val completions =
            mindfulMeditationCompletionDao
                .getAllCompletions(userId)
                .first()

        val batch =
            firestore.batch()

        completions.forEach { completion ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("mindful_meditation_completions")
                    .document(completion.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to completion.id,
                    "userId" to completion.userId,
                    "reflection" to completion.reflection,
                    "completedAt" to completion.completedAt
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreMindfulMeditationCompletions(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("mindful_meditation_completions")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val completion =
                MindfulMeditationCompletionEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    reflection =
                        document.getString("reflection")
                            ?: "",

                    completedAt =
                        document.getLong("completedAt")
                            ?: 0L
                )

            mindfulMeditationCompletionDao.insertCompletion(completion)
        }
    }


    /*
     * ==================================================
     * 5-4-3-2-1 GROUNDING
     * ==================================================
     */

    private suspend fun backupGrounding54321Completions(
        userId: String
    ) {

        val completions =
            grounding54321CompletionDao
                .getAllCompletions(userId)
                .first()

        val batch =
            firestore.batch()

        completions.forEach { completion ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("grounding_54321_completions")
                    .document(completion.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to completion.id,
                    "userId" to completion.userId,
                    "reflection" to completion.reflection,
                    "completedAt" to completion.completedAt
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreGrounding54321Completions(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("grounding_54321_completions")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val completion =
                Grounding54321CompletionEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    reflection =
                        document.getString("reflection")
                            ?: "",

                    completedAt =
                        document.getLong("completedAt")
                            ?: 0L
                )

            grounding54321CompletionDao.insertCompletion(completion)
        }
    }


    /*
     * ==================================================
     * ABC MODEL
     * ==================================================
     */

    private suspend fun backupABCModelCompletions(
        userId: String
    ) {

        val completions =
            abcModelCompletionDao
                .getAllCompletions(userId)
                .first()

        val batch =
            firestore.batch()

        completions.forEach { completion ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection("abc_model_completions")
                    .document(completion.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to completion.id,
                    "userId" to completion.userId,
                    "activatingEvent" to completion.activatingEvent,
                    "beliefs" to completion.beliefs,
                    "consequences" to completion.consequences,
                    "completedAt" to completion.completedAt
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreABCModelCompletions(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("abc_model_completions")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val completion =
                ABCModelCompletionEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    activatingEvent =
                        document.getString("activatingEvent")
                            ?: "",

                    beliefs =
                        document.getString("beliefs")
                            ?: "",

                    consequences =
                        document.getString("consequences")
                            ?: "",

                    completedAt =
                        document.getLong("completedAt")
                            ?: 0L
                )

            abcModelCompletionDao.insertCompletion(completion)
        }
    }


    /*
     * ==================================================
     * SELF-COMPASSION REFLECTION
     * ==================================================
     */

    private suspend fun backupSelfCompassionReflectionCompletions(
        userId: String
    ) {

        val completions =
            selfCompassionReflectionCompletionDao
                .getAllCompletions(userId)
                .first()

        val batch =
            firestore.batch()

        completions.forEach { completion ->

            val document =
                firestore
                    .collection("users")
                    .document(userId)
                    .collection(
                        "self_compassion_reflection_completions"
                    )
                    .document(completion.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to completion.id,
                    "userId" to completion.userId,
                    "situation" to completion.situation,
                    "friendResponse" to completion.friendResponse,
                    "selfCompassionResponse" to
                            completion.selfCompassionResponse,
                    "completedAt" to completion.completedAt
                )
            )
        }

        batch.commit().await()
    }

    private suspend fun restoreSelfCompassionReflectionCompletions(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection(
                    "self_compassion_reflection_completions"
                )
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val completion =
                SelfCompassionReflectionCompletionEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    situation =
                        document.getString("situation")
                            ?: "",

                    friendResponse =
                        document.getString("friendResponse")
                            ?: "",

                    selfCompassionResponse =
                        document.getString(
                            "selfCompassionResponse"
                        ) ?: "",

                    completedAt =
                        document.getLong("completedAt")
                            ?: 0L
                )

            selfCompassionReflectionCompletionDao
                .insertCompletion(completion)
        }
    }


    /*
     * ==================================================
     * DAILY CBT PROGRESS
     * ==================================================
     *
     * Each record represents the total number of CBT
     * exercises explicitly completed by one user on one
     * calendar date.
     *
     * Firestore structure:
     *
     * users/{userId}/cbt_daily_progress/{localRoomId}
     *
     * The Room primary key is used as the document ID.
     */

    private suspend fun backupCBTDailyProgress(
        userId: String
    ) {

        val progressRecords =
            cbtDailyProgressDao
                .getAllForUser(userId)
                .first()

        val progressCollection =
            firestore
                .collection("users")
                .document(userId)
                .collection("cbt_daily_progress")

        val existingSnapshot =
            progressCollection
                .get()
                .await()

        val localProgressIds =
            progressRecords
                .map { it.id.toString() }
                .toSet()

        val batch =
            firestore.batch()

        existingSnapshot.documents.forEach { document ->

            if (document.id !in localProgressIds) {
                batch.delete(document.reference)
            }
        }

        progressRecords.forEach { progress ->

            val document =
                progressCollection
                    .document(progress.id.toString())

            batch.set(
                document,
                mapOf(
                    "id" to progress.id,
                    "userId" to progress.userId,
                    "date" to progress.date,
                    "completedCount" to progress.completedCount
                )
            )
        }

        batch.commit().await()
    }


    private suspend fun restoreCBTDailyProgress(
        userId: String
    ) {

        val snapshot =
            firestore
                .collection("users")
                .document(userId)
                .collection("cbt_daily_progress")
                .get()
                .await()

        snapshot.documents.forEach { document ->

            val progress =
                CBTDailyProgressEntity(
                    id =
                        document.getLong("id")
                            ?.toInt()
                            ?: document.id.toInt(),

                    userId =
                        document.getString("userId")
                            ?: userId,

                    date =
                        document.getString("date")
                            ?: "",

                    completedCount =
                        document.getLong("completedCount")
                            ?.toInt()
                            ?: 0
                )

            cbtDailyProgressDao.insertOrUpdate(
                progress
            )
        }
    }


    /*
     * --------------------------------------------------
     * FLOW HELPER
     * --------------------------------------------------
     */

    private suspend fun <T> Flow<T>.firstValue(): T {
        return first()
    }
}