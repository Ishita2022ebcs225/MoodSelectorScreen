package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import kotlinx.coroutines.flow.Flow

interface CBTProgressRepository {

    suspend fun saveCompletion(
        completion: CBTActivityCompletionEntity
    )

    suspend fun deleteCompletion(
        completion: CBTActivityCompletionEntity
    )

    fun getAllCompletions(
        userId: String
    ):
            Flow<List<CBTActivityCompletionEntity>>

    fun getCompletionCount(
        userId: String
    ):
            Flow<Int>

    suspend fun deleteAllCompletions(
        userId: String
    )
}