package com.example.moodselector.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: MoodEntry)

    @Delete
    suspend fun deleteMood(mood: MoodEntry)

    @Query("SELECT * FROM mood_entries ORDER BY id DESC")
    fun getAllMoods(): Flow<List<MoodEntry>>
}