package com.example.moodselector.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduledCBTActivityDao {

    @Insert
    suspend fun insertScheduledActivity(
        activity: ScheduledCBTActivityEntity
    )

    @Delete
    suspend fun deleteScheduledActivity(
        activity: ScheduledCBTActivityEntity
    )

    @Query(
        """
        SELECT *
        FROM scheduled_cbt_activities
        ORDER BY scheduledAt DESC
        """
    )
    fun getAllScheduledActivities(): Flow<List<ScheduledCBTActivityEntity>>

    @Query(
        """
        SELECT COUNT(*)
        FROM scheduled_cbt_activities
        """
    )
    fun getScheduledActivityCount(): Flow<Int>

    @Query(
        """
        DELETE FROM scheduled_cbt_activities
        """
    )
    suspend fun deleteAllScheduledActivities()
}

