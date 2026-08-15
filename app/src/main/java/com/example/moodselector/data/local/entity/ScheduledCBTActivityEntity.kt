package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "scheduled_cbt_activities"
)
data class ScheduledCBTActivityEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /*
     * Firebase UID of the user who owns this scheduled activity.
     */
    val userId: String,

    val activityId: String,

    val activityTitle: String,

    val activityDescription: String,

    val activityName: String,

    val activityType: String,

    val scheduledWhen: String,

    val scheduledWhere: String,

    val createdAt: Long = System.currentTimeMillis()
)

