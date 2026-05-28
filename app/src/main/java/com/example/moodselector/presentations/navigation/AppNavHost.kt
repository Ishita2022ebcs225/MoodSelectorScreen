package com.example.moodselector.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodselector.presentations.journal.JournalEditorScreen
import com.example.moodselector.presentations.journal.JournalScreen
import com.example.moodselector.presentations.mood.MoodScreen

object Routes {

    const val MOOD = "mood"

    const val JOURNAL = "journal"

    const val JOURNAL_EDITOR = "journal_editor"
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Routes.MOOD
    ) {

        // MOOD SCREEN

        composable(Routes.MOOD) {

            MoodScreen(

                onOpenJournal = {

                    navController.navigate(
                        Routes.JOURNAL
                    )
                }
            )
        }

        // JOURNAL FEED SCREEN

        composable(Routes.JOURNAL) {

            JournalScreen(

                onAddJournalClick = {

                    navController.navigate(
                        Routes.JOURNAL_EDITOR
                    )
                }
            )
        }

        // JOURNAL EDITOR SCREEN

        composable(Routes.JOURNAL_EDITOR) {

            JournalEditorScreen(

                onBackClick = {

                    navController.popBackStack()
                }
            )
        }
    }
}