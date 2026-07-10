package com.example.moodselector.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodselector.presentations.assessment.onboarding.AssessmentOnboardingScreen
import com.example.moodselector.presentations.assessment.questionnaire.AssessmentQuestionnaireScreen
import com.example.moodselector.presentations.assessment.results.AssessmentResultsScreen
import com.example.moodselector.presentations.journal.JournalEditorScreen
import com.example.moodselector.presentations.journal.JournalScreen
import com.example.moodselector.presentations.mood.MoodScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        /*
         * --------------------------------------------------
         * Assessment Onboarding
         * --------------------------------------------------
         */

        composable(
            route = Screen.AssessmentOnboarding.route
        ) {

            AssessmentOnboardingScreen(

                onStartAssessment = {

                    navController.navigate(
                        Screen.AssessmentQuestionnaire.route
                    )
                }
            )
        }

        /*
         * --------------------------------------------------
         * Assessment Questionnaire
         * --------------------------------------------------
         */

        composable(
            route = Screen.AssessmentQuestionnaire.route
        ) {

            AssessmentQuestionnaireScreen(

                onAssessmentCompleted = { _, _, _, _ ->

                    navController.navigate(
                        Screen.AssessmentResults.route
                    )
                }
            )
        }

        /*
         * --------------------------------------------------
         * Assessment Results
         * --------------------------------------------------
         */

        composable(
            route = Screen.AssessmentResults.route
        ) {

            AssessmentResultsScreen(

                onContinueClicked = {

                    navController.navigate(
                        Screen.Mood.route
                    ) {

                        popUpTo(
                            Screen.AssessmentOnboarding.route
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        /*
         * --------------------------------------------------
         * Mood
         * --------------------------------------------------
         */

        composable(
            route = Screen.Mood.route
        ) {

            MoodScreen(

                onOpenJournal = {

                    navController.navigate(
                        Screen.Journal.route
                    )
                }
            )
        }

        /*
         * --------------------------------------------------
         * Journal
         * --------------------------------------------------
         */

        composable(
            route = Screen.Journal.route
        ) {

            JournalScreen(

                onAddJournalClick = {

                    navController.navigate(
                        Screen.JournalEditor.route
                    )
                }
            )
        }

        /*
         * --------------------------------------------------
         * Journal Editor
         * --------------------------------------------------
         */

        composable(
            route = Screen.JournalEditor.route
        ) {

            JournalEditorScreen(

                onBackClick = {

                    navController.popBackStack()
                }
            )
        }
    }
}