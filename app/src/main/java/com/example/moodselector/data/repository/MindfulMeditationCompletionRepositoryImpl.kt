package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.MindfulMeditationCompletionDao
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import com.example.moodselector.domain.repository.MindfulMeditationCompletionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MindfulMeditationCompletionRepositoryImpl @Inject constructor(
    private val dao: MindfulMeditationCompletionDao
) : MindfulMeditationCompletionRepository {

    override suspend fun saveCompletion(
        completion: MindfulMeditationCompletionEntity
    ) {
        dao.insertCompletion(completion)
    }

    override suspend fun deleteCompletion(
        completion: MindfulMeditationCompletionEntity
    ) {
        dao.deleteCompletion(completion)
    }

    override fun getAllCompletions(
        userId: String
    ):
            Flow<List<MindfulMeditationCompletionEntity>> {

        return dao.getAllCompletions(userId)
    }

    override fun getCompletionCount(
        userId: String
    ):
            Flow<Int> {

        return dao.getCompletionCount(userId)
    }

    override suspend fun deleteAllCompletions(
        userId: String
    ) {
        dao.deleteAllCompletions(userId)
    }
}