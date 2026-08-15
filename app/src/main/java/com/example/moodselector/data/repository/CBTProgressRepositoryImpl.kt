package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.CBTActivityCompletionDao
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.domain.repository.CBTProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CBTProgressRepositoryImpl @Inject constructor(
    private val cbtActivityCompletionDao: CBTActivityCompletionDao
) : CBTProgressRepository {

    override suspend fun saveCompletion(
        completion: CBTActivityCompletionEntity
    ) {
        cbtActivityCompletionDao.insertCompletion(
            completion
        )
    }

    override suspend fun deleteCompletion(
        completion: CBTActivityCompletionEntity
    ) {
        cbtActivityCompletionDao.deleteCompletion(
            completion
        )
    }

    override fun getAllCompletions(
        userId: String
    ):
            Flow<List<CBTActivityCompletionEntity>> {

        return cbtActivityCompletionDao
            .getAllCompletions(userId)
    }

    override fun getCompletionCount(
        userId: String
    ):
            Flow<Int> {

        return cbtActivityCompletionDao
            .getCompletionCount(userId)
    }

    override suspend fun deleteAllCompletions(
        userId: String
    ) {

        cbtActivityCompletionDao
            .deleteAllCompletions(userId)
    }
}