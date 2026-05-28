package com.example.moodselector.presentations.navigation

sealed class Screen(
    val route: String
) {

    object Insights : Screen(
        "insights"
    )

    object History : Screen(
        "history"
    )

    object Graph : Screen(
        "graph"
    )

    // Journal Home Screen
    object Journal : Screen(
        "journal"
    )

    // Journal Editor Screen
    object JournalEditor : Screen(
        "journal_editor"
    )
}