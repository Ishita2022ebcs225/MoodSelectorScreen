package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    suspend fun insertJournal(
        journal: JournalEntity
    )

    suspend fun updateJournal(
        journal: JournalEntity
    )

    suspend fun deleteJournal(
        journalId: Int,
        userId: String
    )

    fun getAllJournals(
        userId: String
    ): Flow<List<JournalEntity>>

    suspend fun getJournalById(
        id: Int,
        userId: String
    ): JournalEntity?

    suspend fun deleteAllJournals(
        userId: String
    )
}