package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.MoodEntry
import kotlinx.coroutines.flow.Flow

interface MoodRepository {

    suspend fun insertMood(
        mood: MoodEntry
    )

    suspend fun deleteMood(
        mood: MoodEntry
    )

    fun getAllMoods(
        userId: String
    ): Flow<List<MoodEntry>>
}