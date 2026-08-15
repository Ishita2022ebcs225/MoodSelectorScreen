package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "abc_model_completions"
)
data class ABCModelCompletionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /*
     * Firebase UID of the user who owns this ABC completion.
     */
    val userId: String,

    val activatingEvent: String,

    val beliefs: String,

    val consequences: String,

    val completedAt: Long
)

