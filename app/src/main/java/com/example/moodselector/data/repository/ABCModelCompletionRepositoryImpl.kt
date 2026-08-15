package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.ABCModelCompletionDao
import com.example.moodselector.data.local.entity.ABCModelCompletionEntity
import com.example.moodselector.domain.repository.ABCModelCompletionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ABCModelCompletionRepositoryImpl @Inject constructor(
    private val dao: ABCModelCompletionDao
) : ABCModelCompletionRepository {

    override suspend fun saveCompletion(
        completion: ABCModelCompletionEntity
    ) {
        dao.insertCompletion(completion)
    }

    override fun getAllCompletions(
        userId: String
    ):
            Flow<List<ABCModelCompletionEntity>> {

        return dao.getAllCompletions(userId)
    }

    override suspend fun getCompletionById(
        id: Int,
        userId: String
    ): ABCModelCompletionEntity? {

        return dao.getCompletionById(
            id,
            userId
        )
    }

    override suspend fun deleteCompletion(
        completion: ABCModelCompletionEntity
    ) {
        dao.deleteCompletion(completion)
    }

    override suspend fun deleteAllCompletions(
        userId: String
    ) {
        dao.deleteAllCompletions(userId)
    }
}