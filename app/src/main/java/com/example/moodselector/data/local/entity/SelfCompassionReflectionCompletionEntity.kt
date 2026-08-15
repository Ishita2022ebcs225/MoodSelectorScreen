package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "self_compassion_reflection_completions"
)
data class SelfCompassionReflectionCompletionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Firebase UID of the user who owns this completion.
     */
    val userId: String,

    /**
     * The situation the user is struggling with.
     */
    val situation: String,

    /**
     * What the user would say to a close friend
     * experiencing the same situation.
     */
    val friendResponse: String,

    /**
     * The compassionate response the user
     * would like to offer themselves.
     */
    val selfCompassionResponse: String,

    /**
     * Time the exercise was completed.
     */
    val completedAt: Long
)

