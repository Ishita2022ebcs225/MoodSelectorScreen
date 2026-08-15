package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "mindful_meditation_completions"
)
data class MindfulMeditationCompletionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Firebase UID of the user who owns this completion.
     */
    val userId: String,

    /**
     * User's reflection after completing
     * the meditation.
     */
    val reflection: String,

    /**
     * Timestamp of meditation completion.
     */
    val completedAt: Long
)

