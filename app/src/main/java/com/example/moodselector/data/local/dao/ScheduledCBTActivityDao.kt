package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduledCBTActivityDao {

    /*
     * --------------------------------------------------
     * INSERT / UPDATE
     * --------------------------------------------------
     */

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertScheduledActivity(
        activity: ScheduledCBTActivityEntity
    )


    /*
     * --------------------------------------------------
     * GET ALL SCHEDULED ACTIVITIES
     * --------------------------------------------------
     */

    @Query(
        """
        SELECT *
        FROM scheduled_cbt_activities
        ORDER BY createdAt DESC
        """
    )
    fun getAllScheduledActivities():
            Flow<List<ScheduledCBTActivityEntity>>


    /*
     * --------------------------------------------------
     * GET ONE BY DATABASE ID
     * --------------------------------------------------
     */

    @Query(
        """
        SELECT *
        FROM scheduled_cbt_activities
        WHERE id = :id
        LIMIT 1
        """
    )
    suspend fun getScheduledActivityById(
        id: Int
    ): ScheduledCBTActivityEntity?


    /*
     * --------------------------------------------------
     * COUNT
     * --------------------------------------------------
     */

    @Query(
        """
        SELECT COUNT(*)
        FROM scheduled_cbt_activities
        """
    )
    fun getScheduledActivityCount(): Flow<Int>


    /*
     * --------------------------------------------------
     * DELETE BY DATABASE ID
     * --------------------------------------------------
     *
     * Deletes ONLY one scheduled instance.
     */

    @Query(
        """
        DELETE FROM scheduled_cbt_activities
        WHERE id = :id
        """
    )
    suspend fun deleteScheduledActivityById(
        id: Int
    )


    /*
     * --------------------------------------------------
     * DELETE BY CBT ACTIVITY ID
     * --------------------------------------------------
     *
     * Deletes ALL schedules for this activityId.
     *
     * This is intentionally different from deleting
     * by database ID.
     */

    @Query(
        """
        DELETE FROM scheduled_cbt_activities
        WHERE activityId = :activityId
        """
    )
    suspend fun deleteScheduledActivityByActivityId(
        activityId: String
    )


    /*
     * --------------------------------------------------
     * DELETE ENTITY
     * --------------------------------------------------
     */

    @Delete
    suspend fun deleteScheduledActivity(
        activity: ScheduledCBTActivityEntity
    )


    /*
     * --------------------------------------------------
     * DELETE ALL
     * --------------------------------------------------
     */

    @Query(
        """
        DELETE FROM scheduled_cbt_activities
        """
    )
    suspend fun deleteAllScheduledActivities()
}