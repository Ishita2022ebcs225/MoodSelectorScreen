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
        WHERE userId = :userId
        ORDER BY createdAt DESC
        """
    )
    fun getAllScheduledActivities(
        userId: String
    ):
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
        AND userId = :userId
        LIMIT 1
        """
    )
    suspend fun getScheduledActivityById(
        id: Int,
        userId: String
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
        WHERE userId = :userId
        """
    )
    fun getScheduledActivityCount(
        userId: String
    ): Flow<Int>


    /*
     * --------------------------------------------------
     * DELETE BY DATABASE ID
     * --------------------------------------------------
     *
     * Deletes ONLY one scheduled instance
     * belonging to this user.
     */

    @Query(
        """
        DELETE FROM scheduled_cbt_activities
        WHERE id = :id
        AND userId = :userId
        """
    )
    suspend fun deleteScheduledActivityById(
        id: Int,
        userId: String
    )


    /*
     * --------------------------------------------------
     * DELETE BY CBT ACTIVITY ID
     * --------------------------------------------------
     *
     * Deletes ALL schedules for this activityId
     * belonging to this user.
     *
     * This is intentionally different from deleting
     * by database ID.
     */

    @Query(
        """
        DELETE FROM scheduled_cbt_activities
        WHERE activityId = :activityId
        AND userId = :userId
        """
    )
    suspend fun deleteScheduledActivityByActivityId(
        activityId: String,
        userId: String
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
        WHERE userId = :userId
        """
    )
    suspend fun deleteAllScheduledActivities(
        userId: String
    )
}