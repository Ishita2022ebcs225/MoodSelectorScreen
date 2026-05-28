package com.example.moodselector.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodselector.presentations.journal.JournalEditorScreen
import com.example.moodselector.presentations.journal.JournalScreen
import com.example.moodselector.presentations.mood.MoodGraphScreen
import com.example.moodselector.presentations.mood.MoodHistoryScreen
import com.example.moodselector.presentations.mood.MoodInsightsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Insights.route,
        modifier = modifier
    ) {

        // Mood Insights Screen
        composable(
            route = Screen.Insights.route
        ) {

            MoodInsightsScreen()
        }

        // Mood History Screen
        composable(
            route = Screen.History.route
        ) {

            MoodHistoryScreen()
        }

        // Mood Graph Screen
        composable(
            route = Screen.Graph.route
        ) {

            MoodGraphScreen()
        }

        // Journal Home Screen
        composable(
            route = Screen.Journal.route
        ) {

            JournalScreen(

                onAddJournalClick = {

                    navController.navigate(
                        "journal_editor"
                    )
                }
            )
        }

        // Journal Editor Screen
        composable("journal_editor") {

            JournalEditorScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}