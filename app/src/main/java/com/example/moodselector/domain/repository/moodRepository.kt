package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.MoodEntry
import kotlinx.coroutines.flow.Flow

interface MoodRepository {

    suspend fun insertMood(mood: MoodEntry)

    suspend fun deleteMood(mood: MoodEntry)

    fun getAllMoods(): Flow<List<MoodEntry>>
}