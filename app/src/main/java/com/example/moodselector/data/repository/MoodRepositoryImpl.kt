package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.MoodDao
import com.example.moodselector.data.local.entity.MoodEntry
import com.example.moodselector.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    private val dao: MoodDao
) : MoodRepository {

    override suspend fun insertMood(
        mood: MoodEntry
    ) {
        dao.insertMood(mood)
    }

    override suspend fun deleteMood(
        mood: MoodEntry
    ) {
        dao.deleteMood(mood)
    }

    override fun getAllMoods(
        userId: String
    ): Flow<List<MoodEntry>> {
        return dao.getAllMoods(userId)
    }

    override suspend fun deleteAllMoods(
        userId: String
    ) {
        dao.deleteAllMoods(userId)
    }
}