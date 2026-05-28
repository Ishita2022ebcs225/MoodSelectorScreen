package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JournalRepositoryImpl @Inject constructor(
    private val dao: JournalDao
) : JournalRepository {

    override suspend fun insertJournal(journal: JournalEntity) {
        dao.insertJournal(journal)
    }

    override suspend fun deleteJournal(journal: JournalEntity) {
        dao.deleteJournal(journal)
    }

    override fun getAllJournals(): Flow<List<JournalEntity>> {
        return dao.getAllJournals()
    }

    override suspend fun getJournalById(id: Int): JournalEntity? {
        return dao.getJournalById(id)
    }

    // ✅ THIS FIXES YOUR ERROR
    override suspend fun deleteAllJournals() {
        dao.deleteAllJournals()
    }
}