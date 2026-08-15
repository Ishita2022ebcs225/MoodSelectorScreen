package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntry(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /*
     * Firebase UID of the user who owns this mood entry.
     */
    val userId: String,

    val mood: String,

    val emoji: String,

    val timestamp: String
)

