package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "grounding_54321_completions"
)
data class Grounding54321CompletionEntity(

    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int = 0,

    /*
     * Firebase UID of the user who owns this completion.
     */
    val userId: String,

    /*
     * Optional reflection written after
     * completing the grounding exercise.
     */
    val reflection: String = "",

    /*
     * Time at which the user explicitly
     * completed the exercise.
     */
    val completedAt: Long
)

