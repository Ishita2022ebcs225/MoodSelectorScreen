package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodselector.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(
        journal: JournalEntity
    )

    @Delete
    suspend fun deleteJournal(
        journal: JournalEntity
    )

    @Query("DELETE FROM journal_entries")
    suspend fun deleteAllJournals()

    @Query(
        "SELECT * FROM journal_entries ORDER BY timestamp DESC"
    )
    fun getAllJournals():
            Flow<List<JournalEntity>>

    // ✅ MISSING FUNCTION
    @Query(
        "SELECT * FROM journal_entries WHERE id = :id"
    )
    suspend fun getJournalById(
        id: Int
    ): JournalEntity?
}