package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.Grounding54321CompletionDao
import com.example.moodselector.data.local.entity.Grounding54321CompletionEntity
import com.example.moodselector.domain.repository.Grounding54321CompletionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Grounding54321CompletionRepositoryImpl @Inject constructor(
    private val dao: Grounding54321CompletionDao
) : Grounding54321CompletionRepository {

    override suspend fun saveCompletion(
        completion: Grounding54321CompletionEntity
    ) {
        dao.insertCompletion(completion)
    }

    override suspend fun deleteCompletion(
        completion: Grounding54321CompletionEntity
    ) {
        dao.deleteCompletion(completion)
    }

    override fun getAllCompletions(
        userId: String
    ):
            Flow<List<Grounding54321CompletionEntity>> {

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