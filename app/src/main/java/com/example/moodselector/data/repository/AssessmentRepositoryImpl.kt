package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.AssessmentResultDao
import com.example.moodselector.data.local.entity.AssessmentResultEntity
import com.example.moodselector.domain.repository.AssessmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssessmentRepositoryImpl @Inject constructor(
    private val dao: AssessmentResultDao
) : AssessmentRepository {

    override suspend fun saveResult(
        result: AssessmentResultEntity
    ) {
        dao.insertResult(result)
    }

    override fun getLatestResult(
        userId: String
    ): Flow<AssessmentResultEntity?> {
        return dao.getLatestResult(userId)
    }

    override fun getAllResults(
        userId: String
    ): Flow<List<AssessmentResultEntity>> {
        return dao.getAllResults(userId)
    }
}