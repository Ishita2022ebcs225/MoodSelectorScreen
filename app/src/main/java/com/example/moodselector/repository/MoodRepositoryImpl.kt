package com.example.moodselector.data.repository

import com.example.helloworldapp.data.local.MoodDao
import com.example.helloworldapp.domain.repository.MoodRepository
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    private val dao: MoodDao
) : MoodRepository {

    override suspend fun insertMood(mood: MoodEntity) {
        dao.insertMood(mood)
    }

    override fun getAllMoods() = dao.getAllMoods()
}