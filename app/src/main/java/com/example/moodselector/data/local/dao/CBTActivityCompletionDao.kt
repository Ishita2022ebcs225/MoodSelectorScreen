package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CBTActivityCompletionDao {

    @Insert
    suspend fun insertCompletion(
        completion: CBTActivityCompletionEntity
    )

    @Delete
    suspend fun deleteCompletion(
        completion: CBTActivityCompletionEntity
    )

    @Query(
        """
        SELECT * 
        FROM cbt_activity_completions
        ORDER BY completedAt DESC
        """
    )
    fun getAllCompletions(): Flow<List<CBTActivityCompletionEntity>>

    @Query(
        """
        SELECT COUNT(*) 
        FROM cbt_activity_completions
        """
    )
    fun getCompletionCount(): Flow<Int>

    @Query(
        """
        DELETE FROM cbt_activity_completions
        """
    )
    suspend fun deleteAllCompletions()
}