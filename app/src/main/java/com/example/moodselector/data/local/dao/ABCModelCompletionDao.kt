package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moodselector.data.local.entity.ABCModelCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ABCModelCompletionDao {

    @Insert
    suspend fun insertCompletion(
        completion: ABCModelCompletionEntity
    )

    @Query(
        "SELECT * FROM abc_model_completions " +
                "WHERE userId = :userId " +
                "ORDER BY completedAt DESC"
    )
    fun getAllCompletions(
        userId: String
    ): Flow<List<ABCModelCompletionEntity>>

    @Query(
        "SELECT * FROM abc_model_completions " +
                "WHERE id = :id " +
                "AND userId = :userId " +
                "LIMIT 1"
    )
    suspend fun getCompletionById(
        id: Int,
        userId: String
    ): ABCModelCompletionEntity?

    @Delete
    suspend fun deleteCompletion(
        completion: ABCModelCompletionEntity
    )

    @Query(
        "DELETE FROM abc_model_completions " +
                "WHERE userId = :userId"
    )
    suspend fun deleteAllCompletions(
        userId: String
    )
}