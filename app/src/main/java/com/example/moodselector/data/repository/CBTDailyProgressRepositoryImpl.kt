package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.CBTDailyProgressDao
import com.example.moodselector.data.local.entity.CBTDailyProgressEntity
import com.example.moodselector.domain.repository.CBTDailyProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CBTDailyProgressRepositoryImpl @Inject constructor(
    private val dao: CBTDailyProgressDao
) : CBTDailyProgressRepository {

    /*
     * ==========================================================
     * GET DAILY PROGRESS
     * ==========================================================
     */

    override suspend fun getDailyProgress(
        userId: String,
        date: String
    ): CBTDailyProgressEntity? {

        return dao.getDailyProgress(
            userId = userId,
            date = date
        )
    }


    /*
     * ==========================================================
     * OBSERVE DAILY PROGRESS
     * ==========================================================
     */

    override fun observeDailyProgress(
        userId: String,
        date: String
    ): Flow<CBTDailyProgressEntity?> {

        return dao.observeDailyProgress(
            userId = userId,
            date = date
        )
    }


    /*
     * ==========================================================
     * INCREMENT DAILY COMPLETION
     * ==========================================================
     */

    override suspend fun incrementDailyCompletion(
        userId: String,
        date: String
    ) {

        val rowsUpdated =
            dao.incrementCompletionCount(
                userId = userId,
                date = date
            )

        if (rowsUpdated == 0) {

            dao.insertOrUpdate(
                CBTDailyProgressEntity(
                    userId = userId,
                    date = date,
                    completedCount = 1
                )
            )
        }
    }


    /*
     * ==========================================================
     * UPDATE UNIQUE COMPLETED COUNT
     * ==========================================================
     *
     * Stores the number of DIFFERENT CBT exercises completed
     * on this particular date.
     *
     * Repeating the same exercise does not increase this value.
     */

    override suspend fun updateUniqueCompletedCount(
        userId: String,
        date: String,
        count: Int
    ) {

        val existingProgress =
            dao.getDailyProgress(
                userId = userId,
                date = date
            )

        dao.insertOrUpdate(
            CBTDailyProgressEntity(
                id = existingProgress?.id ?: 0,
                userId = userId,
                date = date,
                completedCount = count
            )
        )
    }


    /*
     * ==========================================================
     * DELETE ONE DAY
     * ==========================================================
     */

    override suspend fun deleteDailyProgress(
        userId: String,
        date: String
    ) {

        dao.deleteForDate(
            userId = userId,
            date = date
        )
    }


    /*
     * ==========================================================
     * DELETE ALL USER PROGRESS
     * ==========================================================
     */

    override suspend fun deleteAllDailyProgress(
        userId: String
    ) {

        dao.deleteAllForUser(
            userId = userId
        )
    }
}