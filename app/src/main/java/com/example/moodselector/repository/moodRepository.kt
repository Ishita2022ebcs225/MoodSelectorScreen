package com.example.moodselector.repository

import com.example.moodselector.data.MoodDao
import com.example.moodselector.data.MoodEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoodRepository @Inject constructor(
    private val dao: MoodDao
) {

    val allMoods: Flow<List<MoodEntry>> =
        dao.getAllMoods()

    suspend fun insertMood(mood: MoodEntry) {
        dao.insertMood(mood)
    }
}