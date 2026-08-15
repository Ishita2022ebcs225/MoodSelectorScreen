package com.example.moodselector.presentations.navigation

sealed class Screen(
    val route: String
) {

    /*
     * ----------------------------------------------------------
     * Authentication
     * ----------------------------------------------------------
     */

    data object Login : Screen(
        "login"
    )

    data object Register : Screen(
        "register"
    )


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
     */

    data object MindfulMeditation : Screen(
        "mindful_meditation"
    )


    /*
     * ----------------------------------------------------------
     * 5-4-3-2-1 Grounding
     * ----------------------------------------------------------
     */

    data object Grounding54321 : Screen(
        "grounding_54321"
    )


    /*
     * ----------------------------------------------------------
     * ABC Model
     * ----------------------------------------------------------
     */

    data object ABCModel : Screen(
        "abc_model"
    )


    /*
     * ----------------------------------------------------------
     * Self-Compassion Reflection
     * ----------------------------------------------------------
     */

    data object SelfCompassionReflection : Screen(
        "self_compassion_reflection"
    )
}

