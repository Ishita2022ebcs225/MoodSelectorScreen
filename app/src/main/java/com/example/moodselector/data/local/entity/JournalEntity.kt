package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /*
     * Firebase UID of the user who owns this journal entry.
     */
    val userId: String,

    val content: String,

    val mood: String,

    val timestamp: Long
)

