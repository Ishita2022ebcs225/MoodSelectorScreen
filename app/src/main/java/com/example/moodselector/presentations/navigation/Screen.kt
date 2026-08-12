package com.example.moodselector.presentations.navigation

sealed class Screen(
    val route: String
) {

    /*
     * -------------------------
     * Assessment
     * -------------------------
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
     * -------------------------
     * CBT
     * -------------------------
     */

    data object CBTHome : Screen(
        "cbt_home"
    )

    data object CBTProgress : Screen(
        "cbt_progress"
    )

    data object ActivityScheduling : Screen(
        "activity_scheduling"
    )

    /*
     * -------------------------
     * Main
     * -------------------------
     */

    data object Insights : Screen(
        "insights"
    )

    data object History : Screen(
        "history"
    )

    data object Graph : Screen(
        "graph"
    )

    /*
     * -------------------------
     * Journal
     * -------------------------
     */

    data object Journal : Screen(
        "journal"
    )

    data object JournalEditor : Screen(
        "journal_editor"
    )
}