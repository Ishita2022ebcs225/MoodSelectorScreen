package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.JournalDao
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JournalRepositoryImpl @Inject constructor(
    private val dao: JournalDao
) : JournalRepository {

    override suspend fun insertJournal(
        journal: JournalEntity
    ) {
        dao.insertJournal(
            journal
        )
    }

    override suspend fun updateJournal(
        journal: JournalEntity
    ) {
        dao.updateJournal(
            journal
        )
    }

    override suspend fun deleteJournal(
        journalId: Int,
        userId: String
    ) {
        dao.deleteJournal(
            journalId = journalId,
            userId = userId
        )
    }

    override fun getAllJournals(
        userId: String
    ): Flow<List<JournalEntity>> {

        return dao.getAllJournals(
            userId
        )
    }

    override suspend fun getJournalById(
        id: Int,
        userId: String
    ): JournalEntity? {

        return dao.getJournalById(
            id,
            userId
        )
    }

    override suspend fun deleteAllJournals(
        userId: String
    ) {

        dao.deleteAllJournals(
            userId
        )
    }
}