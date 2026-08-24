package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.CBTDailyProgressEntity
import kotlinx.coroutines.flow.Flow

interface CBTDailyProgressRepository {

    /*
     * ==========================================================
     * GET DAILY PROGRESS
     * ==========================================================
     */

    suspend fun getDailyProgress(
        userId: String,
        date: String
    ): CBTDailyProgressEntity?


    /*
     * ==========================================================
     * OBSERVE DAILY PROGRESS
     * ==========================================================
     */

    fun observeDailyProgress(
        userId: String,
        date: String
    ): Flow<CBTDailyProgressEntity?>


    /*
     * ==========================================================
     * INCREMENT DAILY COMPLETION
     * ==========================================================
     *
     * If the user has no record for the date yet, a new
     * record is created with completedCount = 1.
     *
     * If a record already exists, its count is incremented.
     */

    suspend fun incrementDailyCompletion(
        userId: String,
        date: String
    )


    /*
     * ==========================================================
     * UPDATE UNIQUE DAILY COMPLETION COUNT
     * ==========================================================
     *
     * Stores the number of different CBT exercises
     * completed on a particular date.
     */

    suspend fun updateUniqueCompletedCount(
        userId: String,
        date: String,
        count: Int
    )


    /*
     * ==========================================================
     * DELETE ONE DAY
     * ==========================================================
     */

    suspend fun deleteDailyProgress(
        userId: String,
        date: String
    )


    /*
     * ==========================================================
     * DELETE ALL USER PROGRESS
     * ==========================================================
     */

    suspend fun deleteAllDailyProgress(
        userId: String
    )
}