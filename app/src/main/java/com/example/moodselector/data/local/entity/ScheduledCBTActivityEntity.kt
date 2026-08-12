package com.example.moodselector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "scheduled_cbt_activities"
)
data class ScheduledCBTActivityEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * ID of the CBT activity from the domain definitions.
     */
    val activityId: String,

    /**
     * Stored so the scheduled activity can be displayed
     * without depending on the domain definition at runtime.
     */
    val activityTitle: String,

    val activityDescription: String,

    /**
     * Name/type of the activity as displayed by the scheduling flow.
     */
    val activityName: String,

    /**
     * Pleasure or Mastery for Behavioral Activation activities.
     */
    val activityType: String,

    /**
     * User-selected time/date information.
     */
    val scheduledWhen: String,

    /**
     * User-selected location/context.
     */
    val scheduledWhere: String,

    /**
     * Timestamp indicating when the activity was scheduled.
     */
    val scheduledAt: Long
)

