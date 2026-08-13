package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FiveMinuteStarterCompletionDao {

    /*
     * --------------------------------------------------
     * INSERT COMPLETION
     * --------------------------------------------------
     */

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertCompletion(
        completion: FiveMinuteStarterCompletionEntity
    )


    /*
     * --------------------------------------------------
     * GET ALL COMPLETIONS
     * --------------------------------------------------
     *
     * Most recent completion first.
     */

    @Query(
        """
        SELECT *
        FROM five_minute_starter_completions
        ORDER BY completedAt DESC
        """
    )
    fun getAllCompletions():
            Flow<List<FiveMinuteStarterCompletionEntity>>


    /*
     * --------------------------------------------------
     * GET COMPLETION COUNT
     * --------------------------------------------------
     */

    @Query(
        """
        SELECT COUNT(*)
        FROM five_minute_starter_completions
        """
    )
    fun getCompletionCount():
            Flow<Int>


    /*
     * --------------------------------------------------
     * DELETE ONE COMPLETION
     * --------------------------------------------------
     */

    @Delete
    suspend fun deleteCompletion(
        completion: FiveMinuteStarterCompletionEntity
    )


    /*
     * --------------------------------------------------
     * DELETE ALL COMPLETIONS
     * --------------------------------------------------
     */

    @Query(
        """
        DELETE FROM five_minute_starter_completions
        """
    )
    suspend fun deleteAllCompletions()
}