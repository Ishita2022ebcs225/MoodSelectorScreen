package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.ActivityStep
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.domain.cbt.model.RecommendationTarget

/**
 * Collection of Behavioral Activation activities.
 *
 * These activities are inspired by evidence-based Behavioral Activation
 * techniques commonly used in Cognitive Behavioral Therapy (CBT).
 *
 * The metadata defined here describes each activity, while the
 * interactive worksheet experience is implemented in the presentation layer.
 */
object BehavioralActivationExercises {

    /**
     * All currently available Behavioral Activation activities.
     */
    val activities: List<CBTActivity>
        get() = listOf(
            activityScheduling,
            fiveMinuteStarter
        )

    /**
     * Helps users intentionally schedule meaningful activities,
     * reducing avoidance and encouraging healthier daily routines.
     */
    val activityScheduling = CBTActivity(
        id = "activity_scheduling",
        title = "Activity Scheduling",
        description = "Plan meaningful activities to gradually rebuild healthy routines and improve mood.",
        category = CBTCategory.BEHAVIORAL,
        steps = listOf(
            ActivityStep(
                id = "activity_schedule_step_1",
                instruction = "Choose one meaningful activity you would like to complete."
            ),
            ActivityStep(
                id = "activity_schedule_step_2",
                instruction = "Decide when and where you will do it."
            ),
            ActivityStep(
                id = "activity_schedule_step_3",
                instruction = "Identify any obstacles that might prevent you from completing it."
            ),
            ActivityStep(
                id = "activity_schedule_step_4",
                instruction = "Plan how you will overcome those obstacles."
            ),
            ActivityStep(
                id = "activity_schedule_step_5",
                instruction = "Complete the activity."
            ),
            ActivityStep(
                id = "activity_schedule_step_6",
                instruction = "Reflect on how you felt before and after completing it."
            )
        ),
        benefits = listOf(
            "Reduces avoidance",
            "Improves motivation",
            "Builds healthy routines",
            "Increases confidence through small achievements"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_DEPRESSION,
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.LOW_MOTIVATION
        )
    )

    /**
     * Based on the Five-Minute Starter technique.
     *
     * Helps users overcome procrastination and task avoidance by
     * committing to only five minutes of focused effort.
     */
    val fiveMinuteStarter = CBTActivity(
        id = "five_minute_starter",
        title = "Five-Minute Starter",
        description = "Take the first small step toward a task by committing just five minutes of focused effort.",
        category = CBTCategory.BEHAVIORAL,
        steps = listOf(
            ActivityStep(
                id = "starter_step_1",
                instruction = "Choose one task you have been avoiding."
            ),
            ActivityStep(
                id = "starter_step_2",
                instruction = "Break it down into the smallest possible first step."
            ),
            ActivityStep(
                id = "starter_step_3",
                instruction = "Commit to working on it for just five minutes."
            ),
            ActivityStep(
                id = "starter_step_4",
                instruction = "When the timer ends, decide whether to continue or stop."
            ),
            ActivityStep(
                id = "starter_step_5",
                instruction = "Reflect on how getting started affected your motivation."
            )
        ),
        benefits = listOf(
            "Reduces procrastination",
            "Makes overwhelming tasks feel manageable",
            "Builds momentum",
            "Improves confidence"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.LOW_MOTIVATION,
            RecommendationTarget.PROCRASTINATION,
            RecommendationTarget.OVERWHELM
        )
    )
}