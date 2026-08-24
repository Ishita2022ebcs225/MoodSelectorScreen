package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cbt_daily_progress",
    indices = [
        Index(
            value = ["userId", "date"],
            unique = true
        )
    ]
)
data class CBTDailyProgressEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /*
     * Firebase authenticated user's UID.
     *
     * Every daily progress record belongs to
     * exactly one Firebase user.
     */
    val userId: String,

    /*
     * Calendar date represented as:
     *
     * yyyy-MM-dd
     *
     * Example:
     * 2026-08-24
     */
    val date: String,

    /*
     * Number of CBT exercises completed by this
     * user on this particular date.
     *
     * This counts every completion, including
     * repeated completions of the same exercise.
     */
    val completedCount: Int = 0,

    /*
     * Number of DIFFERENT CBT exercises completed
     * by this user on this particular date.
     *
     * Repeating the same exercise on the same day
     * does not increase this value.
     */
    val uniqueCompletedCount: Int = 0
)