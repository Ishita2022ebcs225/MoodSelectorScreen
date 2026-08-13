package com.example.moodselector.domain.repository

import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import kotlinx.coroutines.flow.Flow

interface ScheduledCBTActivityRepository {

    /*
     * --------------------------------------------------
     * GET ALL SCHEDULED ACTIVITIES
     * --------------------------------------------------
     */

    fun getAllScheduledActivities():
            Flow<List<ScheduledCBTActivityEntity>>


    /*
     * --------------------------------------------------
     * GET SCHEDULED ACTIVITY COUNT
     * --------------------------------------------------
     */

    fun getScheduledActivityCount():
            Flow<Int>


    /*
     * --------------------------------------------------
     * GET ONE SCHEDULED ACTIVITY BY DATABASE ID
     * --------------------------------------------------
     */

    suspend fun getScheduledActivityById(
        id: Int
    ): ScheduledCBTActivityEntity?


    /*
     * --------------------------------------------------
     * SAVE SCHEDULED ACTIVITY
     * --------------------------------------------------
     *
     * id = 0:
     *     creates a new record.
     *
     * id != 0:
     *     updates that specific record.
     */

    suspend fun saveScheduledActivity(
        activity: ScheduledCBTActivityEntity
    )


    /*
     * --------------------------------------------------
     * COMPLETE SCHEDULED ACTIVITY
     * --------------------------------------------------
     *
     * Completing a scheduled activity should:
     *
     * 1. Create a CBT activity completion record.
     * 2. Remove the scheduled activity from the
     *    scheduled activities list.
     *
     * The completion record is what appears in
     * CBT Progress.
     */

    suspend fun completeScheduledActivity(
        activity: ScheduledCBTActivityEntity
    )


    /*
     * --------------------------------------------------
     * DELETE BY DATABASE ID
     * --------------------------------------------------
     *
     * Deletes ONLY one scheduled instance.
     */

    suspend fun deleteScheduledActivityById(
        id: Int
    )


    /*
     * --------------------------------------------------
     * DELETE BY CBT ACTIVITY ID
     * --------------------------------------------------
     *
     * Deletes ALL scheduled instances belonging to
     * the specified CBT activity.
     *
     * This is NOT used when completing one schedule.
     */

    suspend fun deleteScheduledActivityByActivityId(
        activityId: String
    )


    /*
     * --------------------------------------------------
     * DELETE ENTITY
     * --------------------------------------------------
     */

    suspend fun deleteScheduledActivity(
        activity: ScheduledCBTActivityEntity
    )


    /*
     * --------------------------------------------------
     * DELETE ALL
     * --------------------------------------------------
     */

    suspend fun deleteAllScheduledActivities()
}