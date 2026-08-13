package com.example.moodselector.presentations.navigation

sealed class Screen(
    val route: String
) {

    /*
     * ----------------------------------------------------------
     * Main / Bottom Navigation
     * ----------------------------------------------------------
     */

    data object Mood : Screen(
        "mood"
    )

    data object Insights : Screen(
        "insights"
    )

    data object History : Screen(
        "history"
    )

    data object Graph : Screen(
        "graph"
    )

    data object Journal : Screen(
        "journal"
    )


    /*
     * ----------------------------------------------------------
     * Journal Editor
     * ----------------------------------------------------------
     */

    data object JournalEditor : Screen(
        "journal_editor"
    )


    /*
     * ----------------------------------------------------------
     * Assessment
     * ----------------------------------------------------------
     */

    data object AssessmentOnboarding : Screen(
        "assessment_onboarding"
    )

    data object AssessmentQuestionnaire : Screen(
        "assessment_questionnaire"
    )

    data object AssessmentResults : Screen(
        "assessment_results"
    )


    /*
     * ----------------------------------------------------------
     * CBT
     * ----------------------------------------------------------
     */

    data object CBTHome : Screen(
        "cbt_home"
    )

    data object CBTProgress : Screen(
        "cbt_progress"
    )


    /*
     * ----------------------------------------------------------
     * Activity Scheduling
     * ----------------------------------------------------------
     */

    data object ActivityScheduling : Screen(
        "activity_scheduling"
    )

    data object ActivitySchedulingEdit : Screen(
        "activity_scheduling_edit/{scheduledActivityId}"
    ) {

        fun createRoute(
            scheduledActivityId: Int
        ): String =
            "activity_scheduling_edit/$scheduledActivityId"
    }


    /*
     * ----------------------------------------------------------
     * Scheduled Activities
     * ----------------------------------------------------------
     */

    data object ScheduledActivities : Screen(
        "scheduled_activities"
    )


    /*
     * ----------------------------------------------------------
     * Scheduled Activity Completion
     * ----------------------------------------------------------
     */

    data object ScheduledActivityCompletion : Screen(
        "scheduled_activity_completion/{scheduledActivityId}"
    ) {

        fun createRoute(
            scheduledActivityId: Int
        ): String =
            "scheduled_activity_completion/$scheduledActivityId"
    }


    /*
     * ----------------------------------------------------------
     * Five-Minute Starter
     * ----------------------------------------------------------
     */

    data object FiveMinuteStarter : Screen(
        "five_minute_starter"
    )


    /*
     * ----------------------------------------------------------
     * Five-Minute Starter Completion
     * ----------------------------------------------------------
     *
     * The task and firstStep are passed through the
     * navigation route so the completion screen knows
     * exactly what the user worked on.
     *
     * Uri encoding is used so spaces and special
     * characters inside the user's text do not break
     * the navigation route.
     * ----------------------------------------------------------
     */

    data object FiveMinuteStarterCompletion : Screen(
        "five_minute_starter_completion?task={task}&firstStep={firstStep}"
    ) {

        fun createRoute(
            task: String,
            firstStep: String
        ): String {

            return "five_minute_starter_completion" +
                    "?task=${android.net.Uri.encode(task)}" +
                    "&firstStep=${android.net.Uri.encode(firstStep)}"
        }
    }


    /*
     * ----------------------------------------------------------
     * Mindful Meditation
     * ----------------------------------------------------------
     *
     * This is only the navigation route.
     *
     * onBackClick and onComplete are callbacks supplied
     * by AppNavHost to MindfulMeditationScreen.
     * They do NOT belong in this route.
     * ----------------------------------------------------------
     */

    data object MindfulMeditation : Screen(
        "mindful_meditation"
    )
}

