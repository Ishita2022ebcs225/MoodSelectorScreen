package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MindfulMeditationCompletionDao {

    @Insert
    suspend fun insertCompletion(
        completion: MindfulMeditationCompletionEntity
    )

    @Delete
    suspend fun deleteCompletion(
        completion: MindfulMeditationCompletionEntity
    )

    @Query(
        """
        SELECT *
        FROM mindful_meditation_completions
        WHERE userId = :userId
        ORDER BY completedAt DESC
        """
    )
    fun getAllCompletions(
        userId: String
    ):
            Flow<List<MindfulMeditationCompletionEntity>>

    @Query(
        """
        SELECT COUNT(*)
        FROM mindful_meditation_completions
        WHERE userId = :userId
        """
    )
    fun getCompletionCount(
        userId: String
    ):
            Flow<Int>

    @Query(
        """
        DELETE FROM mindful_meditation_completions
        WHERE userId = :userId
        """
    )
    suspend fun deleteAllCompletions(
        userId: String
    )
}