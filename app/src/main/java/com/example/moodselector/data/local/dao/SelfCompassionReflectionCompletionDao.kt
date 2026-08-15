package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moodselector.data.local.entity.SelfCompassionReflectionCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SelfCompassionReflectionCompletionDao {

    @Insert
    suspend fun insertCompletion(
        completion: SelfCompassionReflectionCompletionEntity
    )

    @Delete
    suspend fun deleteCompletion(
        completion: SelfCompassionReflectionCompletionEntity
    )

    @Query(
        """
        SELECT *
        FROM self_compassion_reflection_completions
        WHERE userId = :userId
        ORDER BY completedAt DESC
        """
    )
    fun getAllCompletions(
        userId: String
    ):
            Flow<List<SelfCompassionReflectionCompletionEntity>>

    @Query(
        """
        SELECT COUNT(*)
        FROM self_compassion_reflection_completions
        WHERE userId = :userId
        """
    )
    fun getCompletionCount(
        userId: String
    ): Flow<Int>

    @Query(
        """
        DELETE FROM self_compassion_reflection_completions
        WHERE userId = :userId
        """
    )
    suspend fun deleteAllCompletions(
        userId: String
    )
}