package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodselector.data.local.entity.CBTDailyProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CBTDailyProgressDao {

    /*
     * ==========================================================
     * GET DAILY PROGRESS
     * ==========================================================
     *
     * Returns the progress record for one Firebase user
     * and one specific calendar date.
     */

    @Query(
        """
        SELECT *
        FROM cbt_daily_progress
        WHERE userId = :userId
        AND date = :date
        LIMIT 1
        """
    )
    suspend fun getDailyProgress(
        userId: String,
        date: String
    ): CBTDailyProgressEntity?


    /*
     * ==========================================================
     * OBSERVE DAILY PROGRESS
     * ==========================================================
     *
     * Used by the UI/ViewModel so changes are reflected
     * automatically.
     */

    @Query(
        """
        SELECT *
        FROM cbt_daily_progress
        WHERE userId = :userId
        AND date = :date
        LIMIT 1
        """
    )
    fun observeDailyProgress(
        userId: String,
        date: String
    ): Flow<CBTDailyProgressEntity?>


    /*
     * ==========================================================
     * GET ALL DAILY PROGRESS FOR USER
     * ==========================================================
     *
     * Returns every daily CBT progress record belonging
     * to the specified Firebase user.
     *
     * Used by Firestore backup/restore synchronization.
     */

    @Query(
        """
        SELECT *
        FROM cbt_daily_progress
        WHERE userId = :userId
        ORDER BY date DESC
        """
    )
    fun getAllForUser(
        userId: String
    ): Flow<List<CBTDailyProgressEntity>>


    /*
     * ==========================================================
     * INSERT / UPDATE DAILY PROGRESS
     * ==========================================================
     *
     * userId + date is unique, so inserting an existing
     * date updates that day's record.
     */

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertOrUpdate(
        progress: CBTDailyProgressEntity
    )


    /*
     * ==========================================================
     * INCREMENT COMPLETION COUNT
     * ==========================================================
     *
     * Increments the total completion count for one
     * user and one date.
     */

    @Query(
        """
        UPDATE cbt_daily_progress
        SET completedCount = completedCount + 1
        WHERE userId = :userId
        AND date = :date
        """
    )
    suspend fun incrementCompletionCount(
        userId: String,
        date: String
    ): Int


    /*
     * ==========================================================
     * UPDATE UNIQUE COMPLETION COUNT
     * ==========================================================
     *
     * Stores the number of DIFFERENT CBT exercises
     * completed by the user on this date.
     *
     * Repeating an exercise does not increase this
     * value.
     */

    @Query(
        """
        UPDATE cbt_daily_progress
        SET uniqueCompletedCount = :count
        WHERE userId = :userId
        AND date = :date
        """
    )
    suspend fun updateUniqueCompletedCount(
        userId: String,
        date: String,
        count: Int
    ): Int


    /*
     * ==========================================================
     * DELETE DAILY PROGRESS
     * ==========================================================
     */

    @Delete
    suspend fun delete(
        progress: CBTDailyProgressEntity
    )


    /*
     * ==========================================================
     * DELETE USER'S DAILY PROGRESS
     * ==========================================================
     *
     * Used when all CBT daily progress belonging to a
     * Firebase user needs to be removed.
     */

    @Query(
        """
        DELETE FROM cbt_daily_progress
        WHERE userId = :userId
        """
    )
    suspend fun deleteAllForUser(
        userId: String
    )


    /*
     * ==========================================================
     * DELETE ONE USER/DATE RECORD
     * ==========================================================
     */

    @Query(
        """
        DELETE FROM cbt_daily_progress
        WHERE userId = :userId
        AND date = :date
        """
    )
    suspend fun deleteForDate(
        userId: String,
        date: String
    )
}