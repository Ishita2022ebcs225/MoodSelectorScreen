package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    suspend fun insertJournal(journal: JournalEntity)

    suspend fun deleteJournal(journal: JournalEntity)

    fun getAllJournals(): Flow<List<JournalEntity>>

    suspend fun getJournalById(id: Int): JournalEntity?

    suspend fun deleteAllJournals()   // ✅ REQUIRED
}