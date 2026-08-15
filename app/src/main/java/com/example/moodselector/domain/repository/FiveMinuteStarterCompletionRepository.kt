package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import kotlinx.coroutines.flow.Flow

interface FiveMinuteStarterCompletionRepository {

    /*
     * --------------------------------------------------
     * SAVE COMPLETION
     * --------------------------------------------------
     */

    suspend fun saveCompletion(
        completion: FiveMinuteStarterCompletionEntity
    )


    /*
     * --------------------------------------------------
     * GET ALL COMPLETIONS
     * --------------------------------------------------
     */

    fun getAllCompletions(
        userId: String
    ):
            Flow<List<FiveMinuteStarterCompletionEntity>>


    /*
     * --------------------------------------------------
     * GET COMPLETION COUNT
     * --------------------------------------------------
     */

    fun getCompletionCount(
        userId: String
    ):
            Flow<Int>


    /*
     * --------------------------------------------------
     * DELETE ONE COMPLETION
     * --------------------------------------------------
     */

    suspend fun deleteCompletion(
        completion: FiveMinuteStarterCompletionEntity
    )


    /*
     * --------------------------------------------------
     * DELETE ALL COMPLETIONS
     * --------------------------------------------------
     */

    suspend fun deleteAllCompletions(
        userId: String
    )
}