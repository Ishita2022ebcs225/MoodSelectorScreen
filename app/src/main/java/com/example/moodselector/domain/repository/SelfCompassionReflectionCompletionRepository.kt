package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.SelfCompassionReflectionCompletionEntity
import kotlinx.coroutines.flow.Flow

interface SelfCompassionReflectionCompletionRepository {

    fun getAllCompletions(
        userId: String
    ):
            Flow<List<SelfCompassionReflectionCompletionEntity>>

    fun getCompletionCount(
        userId: String
    ):
            Flow<Int>

    suspend fun saveCompletion(
        completion: SelfCompassionReflectionCompletionEntity
    )

    suspend fun deleteCompletion(
        completion: SelfCompassionReflectionCompletionEntity
    )

    suspend fun deleteAllCompletions(
        userId: String
    )
}