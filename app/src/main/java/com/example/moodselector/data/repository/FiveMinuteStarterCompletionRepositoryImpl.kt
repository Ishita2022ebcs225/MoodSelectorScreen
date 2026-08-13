package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.FiveMinuteStarterCompletionDao
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.domain.repository.FiveMinuteStarterCompletionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FiveMinuteStarterCompletionRepositoryImpl @Inject constructor(
    private val dao: FiveMinuteStarterCompletionDao
) : FiveMinuteStarterCompletionRepository {

    /*
     * --------------------------------------------------
     * SAVE COMPLETION
     * --------------------------------------------------
     */

    override suspend fun saveCompletion(
        completion: FiveMinuteStarterCompletionEntity
    ) {

        dao.insertCompletion(
            completion
        )
    }


    /*
     * --------------------------------------------------
     * GET ALL COMPLETIONS
     * --------------------------------------------------
     */

    override fun getAllCompletions():
            Flow<List<FiveMinuteStarterCompletionEntity>> {

        return dao.getAllCompletions()
    }


    /*
     * --------------------------------------------------
     * GET COMPLETION COUNT
     * --------------------------------------------------
     */

    override fun getCompletionCount():
            Flow<Int> {

        return dao.getCompletionCount()
    }


    /*
     * --------------------------------------------------
     * DELETE ONE COMPLETION
     * --------------------------------------------------
     */

    override suspend fun deleteCompletion(
        completion: FiveMinuteStarterCompletionEntity
    ) {

        dao.deleteCompletion(
            completion
        )
    }


    /*
     * --------------------------------------------------
     * DELETE ALL COMPLETIONS
     * --------------------------------------------------
     */

    override suspend fun deleteAllCompletions() {

        dao.deleteAllCompletions()
    }
}