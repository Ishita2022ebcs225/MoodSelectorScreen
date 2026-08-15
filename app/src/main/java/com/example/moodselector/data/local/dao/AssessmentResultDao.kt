package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodselector.data.local.entity.AssessmentResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssessmentResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(
        result: AssessmentResultEntity
    )

    @Query(
        """
        SELECT *
        FROM assessment_results
        WHERE userId = :userId
        ORDER BY timestamp DESC
        LIMIT 1
        """
    )
    fun getLatestResult(
        userId: String
    ): Flow<AssessmentResultEntity?>

    @Query(
        """
        SELECT *
        FROM assessment_results
        WHERE userId = :userId
        ORDER BY timestamp DESC
        """
    )
    fun getAllResults(
        userId: String
    ): Flow<List<AssessmentResultEntity>>
}