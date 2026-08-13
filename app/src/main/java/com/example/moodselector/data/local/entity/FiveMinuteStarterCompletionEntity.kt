package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "five_minute_starter_completions"
)
data class FiveMinuteStarterCompletionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /*
     * The larger task the user had been avoiding.
     */
    val task: String,

    /*
     * The smallest possible first step
     * the user chose to begin with.
     */
    val firstStep: String,

    /*
     * What happened after the five-minute timer.
     *
     * Examples:
     * "Stopped after five minutes"
     * "Continued beyond five minutes"
     */
    val outcome: String,

    /*
     * User's reflection after completing
     * the exercise.
     */
    val reflection: String,

    /*
     * Timestamp of exercise completion.
     */
    val completedAt: Long
)