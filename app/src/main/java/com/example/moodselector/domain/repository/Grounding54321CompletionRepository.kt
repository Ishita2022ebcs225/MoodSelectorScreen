package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.Grounding54321CompletionEntity
import kotlinx.coroutines.flow.Flow

interface Grounding54321CompletionRepository {

    suspend fun saveCompletion(
        completion: Grounding54321CompletionEntity
    )

    suspend fun deleteCompletion(
        completion: Grounding54321CompletionEntity
    )

    fun getAllCompletions(
        userId: String
    ):
            Flow<List<Grounding54321CompletionEntity>>

    fun getCompletionCount(
        userId: String
    ):
            Flow<Int>

    suspend fun deleteAllCompletions(
        userId: String
    )
}