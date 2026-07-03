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
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.AssessmentOnboarding.route
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

                onAssessmentCompleted = {
                        phq9Score,
                        phq9Severity,
                        gad7Score,
                        gad7Severity ->

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("phq9Score", phq9Score)

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("phq9Severity", phq9Severity)

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("gad7Score", gad7Score)

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("gad7Severity", gad7Severity)

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

            val savedStateHandle =
                navController.previousBackStackEntry
                    ?.savedStateHandle

            val phq9Score =
                savedStateHandle?.get<Int>("phq9Score") ?: 0

            val phq9Severity =
                savedStateHandle?.get<String>("phq9Severity").orEmpty()

            val gad7Score =
                savedStateHandle?.get<Int>("gad7Score") ?: 0

            val gad7Severity =
                savedStateHandle?.get<String>("gad7Severity").orEmpty()

            AssessmentResultsScreen(

                phq9Score = phq9Score,

                phq9Severity = phq9Severity,

                gad7Score = gad7Score,

                gad7Severity = gad7Severity,

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