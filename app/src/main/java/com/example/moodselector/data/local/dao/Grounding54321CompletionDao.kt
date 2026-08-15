package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moodselector.data.local.entity.Grounding54321CompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Grounding54321CompletionDao {

    /*
     * --------------------------------------------------
     * SAVE COMPLETION
     * --------------------------------------------------
     */

    @Insert
    suspend fun insertCompletion(
        completion: Grounding54321CompletionEntity
    )


    /*
     * --------------------------------------------------
     * DELETE ONE COMPLETION
     * --------------------------------------------------
     */

    @Delete
    suspend fun deleteCompletion(
        completion: Grounding54321CompletionEntity
    )


    /*
     * --------------------------------------------------
     * GET ALL COMPLETIONS
     * --------------------------------------------------
     */

    @Query(
        """
        SELECT *
        FROM grounding_54321_completions
        WHERE userId = :userId
        ORDER BY completedAt DESC
        """
    )
    fun getAllCompletions(
        userId: String
    ):
            Flow<List<Grounding54321CompletionEntity>>


    /*
     * --------------------------------------------------
     * COMPLETION COUNT
     * --------------------------------------------------
     */

    @Query(
        """
        SELECT COUNT(*)
        FROM grounding_54321_completions
        WHERE userId = :userId
        """
    )
    fun getCompletionCount(
        userId: String
    ):
            Flow<Int>


    /*
     * --------------------------------------------------
     * DELETE ALL COMPLETIONS
     * --------------------------------------------------
     */

    @Query(
        """
        DELETE FROM grounding_54321_completions
        WHERE userId = :userId
        """
    )
    suspend fun deleteAllCompletions(
        userId: String
    )
}