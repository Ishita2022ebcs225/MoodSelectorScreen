package com.example.moodselector.data.repository

import com.example.moodselector.data.local.dao.CBTActivityCompletionDao
import com.example.moodselector.data.local.dao.ScheduledCBTActivityDao
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.domain.repository.CBTProgressRepository
import com.example.moodselector.domain.repository.ScheduledCBTActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduledCBTActivityRepositoryImpl @Inject constructor(
    private val dao: ScheduledCBTActivityDao,
    private val cbtProgressRepository: CBTProgressRepository
) : ScheduledCBTActivityRepository {

    /*
     * --------------------------------------------------
     * GET ALL SCHEDULED ACTIVITIES
     * --------------------------------------------------
     */

    override fun getAllScheduledActivities(
        userId: String
    ):
            Flow<List<ScheduledCBTActivityEntity>> {

        return dao.getAllScheduledActivities(
            userId
        )
    }


    /*
     * --------------------------------------------------
     * GET SCHEDULED ACTIVITY COUNT
     * --------------------------------------------------
     */

    override fun getScheduledActivityCount(
        userId: String
    ):
            Flow<Int> {

        return dao.getScheduledActivityCount(
            userId
        )
    }


    /*
     * --------------------------------------------------
     * GET ONE SCHEDULED ACTIVITY
     * --------------------------------------------------
     */

    override suspend fun getScheduledActivityById(
        id: Int,
        userId: String
    ): ScheduledCBTActivityEntity? {

        return dao.getScheduledActivityById(
            id,
            userId
        )
    }


    /*
     * --------------------------------------------------
     * SAVE SCHEDULED ACTIVITY
     * --------------------------------------------------
     */

    override suspend fun saveScheduledActivity(
        activity: ScheduledCBTActivityEntity
    ) {

        dao.insertScheduledActivity(
            activity
        )
    }


    /*
     * --------------------------------------------------
     * COMPLETE ONE SCHEDULED ACTIVITY
     * --------------------------------------------------
     *
     * The selected scheduled record is converted into
     * a CBTActivityCompletionEntity.
     *
     * The completion is then saved to CBT Progress.
     *
     * Finally, ONLY this scheduled database record is
     * deleted using its unique database ID and userId.
     *
     * Other scheduled instances of the same CBT
     * activity remain untouched.
     */

    override suspend fun completeScheduledActivity(
        activity: ScheduledCBTActivityEntity
    ) {

        val completion =
            CBTActivityCompletionEntity(

                userId =
                    activity.userId,

                activityId =
                    activity.activityId,

                activityTitle =
                    activity.activityTitle,

                activityDescription =
                    activity.activityDescription,

                activityName =
                    activity.activityName,

                activityType =
                    activity.activityType,

                scheduledWhen =
                    activity.scheduledWhen,

                scheduledWhere =
                    activity.scheduledWhere,

                /*
                 * The current Scheduled Activities page
                 * does not collect a separate reflection.
                 *
                 * Therefore completion from this page
                 * stores an empty reflection.
                 *
                 * Reflection can still be added later
                 * through a dedicated completion/reflection
                 * screen if desired.
                 */
                reflection = "",

                completedAt =
                    System.currentTimeMillis()
            )


        /*
         * Save the completed activity to CBT Progress.
         */

        cbtProgressRepository.saveCompletion(
            completion
        )


        /*
         * Remove ONLY this scheduled instance.
         */

        dao.deleteScheduledActivityById(
            activity.id,
            activity.userId
        )
    }


    /*
     * --------------------------------------------------
     * DELETE BY DATABASE ID
     * --------------------------------------------------
     */

    override suspend fun deleteScheduledActivityById(
        id: Int,
        userId: String
    ) {

        dao.deleteScheduledActivityById(
            id,
            userId
        )
    }


    /*
     * --------------------------------------------------
     * DELETE BY CBT ACTIVITY ID
     * --------------------------------------------------
     */

    override suspend fun deleteScheduledActivityByActivityId(
        activityId: String,
        userId: String
    ) {

        dao.deleteScheduledActivityByActivityId(
            activityId,
            userId
        )
    }


    /*
     * --------------------------------------------------
     * DELETE ENTITY
     * --------------------------------------------------
     */

    override suspend fun deleteScheduledActivity(
        activity: ScheduledCBTActivityEntity
    ) {

        dao.deleteScheduledActivity(
            activity
        )
    }


    /*
     * --------------------------------------------------
     * DELETE ALL
     * --------------------------------------------------
     */

    override suspend fun deleteAllScheduledActivities(
        userId: String
    ) {

        dao.deleteAllScheduledActivities(
            userId
        )
    }
}