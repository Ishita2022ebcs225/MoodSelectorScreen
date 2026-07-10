package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.AssessmentResultEntity
import kotlinx.coroutines.flow.Flow

interface AssessmentRepository {

    suspend fun saveResult(
        result: AssessmentResultEntity
    )

    fun getLatestResult(): Flow<AssessmentResultEntity?>

    fun getAllResults(): Flow<List<AssessmentResultEntity>>
}