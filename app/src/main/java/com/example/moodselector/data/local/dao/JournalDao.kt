package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moodselector.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(
        journal: JournalEntity
    )

    @Update
    suspend fun updateJournal(
        journal: JournalEntity
    )

    @Delete
    suspend fun deleteJournal(
        journal: JournalEntity
    )

    @Query(
        """
        DELETE FROM journal_entries
        WHERE id = :journalId
        AND userId = :userId
        """
    )
    suspend fun deleteJournal(
        journalId: Int,
        userId: String
    )

    @Query(
        "DELETE FROM journal_entries WHERE userId = :userId"
    )
    suspend fun deleteAllJournals(
        userId: String
    )

    @Query(
        """
        SELECT *
        FROM journal_entries
        WHERE userId = :userId
        ORDER BY timestamp DESC
        """
    )
    fun getAllJournals(
        userId: String
    ): Flow<List<JournalEntity>>

    @Query(
        """
        SELECT *
        FROM journal_entries
        WHERE id = :id
        AND userId = :userId
        """
    )
    suspend fun getJournalById(
        id: Int,
        userId: String
    ): JournalEntity?
}