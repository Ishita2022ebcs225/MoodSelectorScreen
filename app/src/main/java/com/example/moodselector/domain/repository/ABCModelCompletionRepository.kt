package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.ABCModelCompletionEntity
import kotlinx.coroutines.flow.Flow

interface ABCModelCompletionRepository {

    suspend fun saveCompletion(
        completion: ABCModelCompletionEntity
    )

    fun getAllCompletions(
        userId: String
    ):
            Flow<List<ABCModelCompletionEntity>>

    suspend fun getCompletionById(
        id: Int,
        userId: String
    ): ABCModelCompletionEntity?

    suspend fun deleteCompletion(
        completion: ABCModelCompletionEntity
    )

    suspend fun deleteAllCompletions(
        userId: String
    )
}