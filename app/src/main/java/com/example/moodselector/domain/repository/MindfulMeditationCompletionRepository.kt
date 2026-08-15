package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import kotlinx.coroutines.flow.Flow

interface MindfulMeditationCompletionRepository {

    suspend fun saveCompletion(
        completion: MindfulMeditationCompletionEntity
    )

    suspend fun deleteCompletion(
        completion: MindfulMeditationCompletionEntity
    )

    fun getAllCompletions(
        userId: String
    ):
            Flow<List<MindfulMeditationCompletionEntity>>

    fun getCompletionCount(
        userId: String
    ):
            Flow<Int>

    suspend fun deleteAllCompletions(
        userId: String
    )
}