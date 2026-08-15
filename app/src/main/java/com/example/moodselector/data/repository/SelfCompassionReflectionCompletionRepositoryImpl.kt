package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.SelfCompassionReflectionCompletionDao
import com.example.moodselector.data.local.entity.SelfCompassionReflectionCompletionEntity
import com.example.moodselector.domain.repository.SelfCompassionReflectionCompletionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SelfCompassionReflectionCompletionRepositoryImpl @Inject constructor(
    private val dao: SelfCompassionReflectionCompletionDao
) : SelfCompassionReflectionCompletionRepository {

    override fun getAllCompletions(
        userId: String
    ):
            Flow<List<SelfCompassionReflectionCompletionEntity>> =
        dao.getAllCompletions(userId)

    override fun getCompletionCount(
        userId: String
    ):
            Flow<Int> =
        dao.getCompletionCount(userId)

    override suspend fun saveCompletion(
        completion: SelfCompassionReflectionCompletionEntity
    ) {
        dao.insertCompletion(completion)
    }

    override suspend fun deleteCompletion(
        completion: SelfCompassionReflectionCompletionEntity
    ) {
        dao.deleteCompletion(completion)
    }

    override suspend fun deleteAllCompletions(
        userId: String
    ) {
        dao.deleteAllCompletions(userId)
    }
}