package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodselector.data.local.entity.MoodEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(
        mood: MoodEntry
    )

    @Delete
    suspend fun deleteMood(
        mood: MoodEntry
    )

    @Query(
        """
        SELECT *
        FROM mood_entries
        WHERE userId = :userId
        ORDER BY id DESC
        """
    )
    fun getAllMoods(
        userId: String
    ): Flow<List<MoodEntry>>

    @Query(
        """
        DELETE FROM mood_entries
        WHERE userId = :userId
        """
    )
    suspend fun deleteAllMoods(
        userId: String
    )
}