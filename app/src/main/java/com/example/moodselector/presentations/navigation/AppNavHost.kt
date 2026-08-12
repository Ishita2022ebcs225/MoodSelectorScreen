package com.example.moodselector.presentations.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moodselector.domain.cbt.definitions.BehavioralActivationExercises
import com.example.moodselector.presentations.assessment.onboarding.AssessmentOnboardingScreen
import com.example.moodselector.presentations.assessment.questionnaire.AssessmentQuestionnaireScreen
import com.example.moodselector.presentations.assessment.results.AssessmentResultsScreen
import com.example.moodselector.presentations.cbt.exercises.ActivitySchedulingScreen
import com.example.moodselector.presentations.cbt.home.CBTHomeScreen
import com.example.moodselector.presentations.cbt.progress.CBTProgressScreen
import com.example.moodselector.presentations.journal.JournalEditorScreen
import com.example.moodselector.presentations.journal.JournalScreen
import com.example.moodselector.presentations.mood.MoodGraphScreen
import com.example.moodselector.presentations.mood.MoodHistoryScreen
import com.example.moodselector.presentations.mood.MoodInsightsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {

    /*
     * --------------------------------------------------
     * Current navigation destination
     * --------------------------------------------------
     */

    val currentBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        currentBackStackEntry?.destination?.route

    /*
     * --------------------------------------------------
     * Bottom navigation items
     * --------------------------------------------------
     *
     * Home
     * CBT
     * History
     * Analytics
     * Journal
     * --------------------------------------------------
     */

    data class BottomNavigationItem(
        val screen: Screen,
        val label: String,
        val icon: ImageVector
    )

    val bottomNavigationItems = listOf(

        BottomNavigationItem(
            screen = Screen.Insights,
            label = "Home",
            icon = Icons.Default.Home
        ),

        BottomNavigationItem(
            screen = Screen.CBTHome,
            label = "CBT",
            icon = Icons.Default.SelfImprovement
        ),

        BottomNavigationItem(
            screen = Screen.History,
            label = "History",
            icon = Icons.Default.History
        ),

        BottomNavigationItem(
            screen = Screen.Graph,
            label = "Analytics",
            icon = Icons.Default.BarChart
        ),

        BottomNavigationItem(
            screen = Screen.Journal,
            label = "Journal",
            icon = Icons.Default.Edit
        )
    )

    /*
     * --------------------------------------------------
     * Bottom navigation visibility
     * --------------------------------------------------
     *
     * The bottom navigation is shown only on the main
     * application screens.
     *
     * Individual CBT exercises and CBT progress
     * intentionally have their own focused experience.
     * --------------------------------------------------
     */

    val showBottomNavigation =
        currentRoute == Screen.Insights.route ||
                currentRoute == Screen.CBTHome.route ||
                currentRoute == Screen.History.route ||
                currentRoute == Screen.Graph.route ||
                currentRoute == Screen.Journal.route

    /*
     * --------------------------------------------------
     * App colors
     * --------------------------------------------------
     */

    val darkPurple = Color(0xFF6C63FF)

    /*
     * --------------------------------------------------
     * Scaffold
     * --------------------------------------------------
     */

    Scaffold(

        containerColor = Color.Transparent,

        bottomBar = {

            if (showBottomNavigation) {

                NavigationBar(

                    containerColor =
                        Color.White.copy(alpha = 0.96f),

                    tonalElevation = 12.dp,

                    modifier = Modifier
                        .navigationBarsPadding()
                        .shadow(
                            elevation = 22.dp,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .clip(
                            RoundedCornerShape(30.dp)
                        )
                ) {

                    bottomNavigationItems.forEach { item ->

                        NavigationBarItem(

                            selected =
                                currentRoute ==
                                        item.screen.route,

                            onClick = {

                                if (
                                    currentRoute !=
                                    item.screen.route
                                ) {

                                    navController.navigate(
                                        item.screen.route
                                    ) {
                                        launchSingleTop = true
                                    }
                                }
                            },

                            icon = {

                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },

                            label = {

                                Text(
                                    text = item.label
                                )
                            },

                            colors =
                                NavigationBarItemDefaults.colors(

                                    selectedIconColor =
                                        Color.White,

                                    selectedTextColor =
                                        darkPurple,

                                    indicatorColor =
                                        darkPurple,

                                    unselectedIconColor =
                                        Color.Gray,

                                    unselectedTextColor =
                                        Color.Gray
                                )
                        )
                    }
                }
            }
        }

    ) { paddingValues ->

        /*
         * --------------------------------------------------
         * Navigation Host
         * --------------------------------------------------
         */

        NavHost(

            navController = navController,

            startDestination = startDestination,

            modifier = Modifier
                .padding(paddingValues)

        ) {

            /*
             * ==================================================
             * ASSESSMENT
             * ==================================================
             */

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
                            _, _, _, _ ->

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
                            Screen.Insights.route
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
             * ==================================================
             * MAIN APPLICATION
             * ==================================================
             */

            /*
             * --------------------------------------------------
             * Home
             * --------------------------------------------------
             */

            composable(
                route = Screen.Insights.route
            ) {

                MoodInsightsScreen()
            }

            /*
             * --------------------------------------------------
             * CBT Home
             * --------------------------------------------------
             */

            composable(
                route = Screen.CBTHome.route
            ) {

                CBTHomeScreen(

                    onActivityClick = { activity ->

                        when (activity.id) {

                            "activity_scheduling" -> {

                                navController.navigate(
                                    Screen.ActivityScheduling.route
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    },

                    onProgressClick = {

                        navController.navigate(
                            Screen.CBTProgress.route
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            /*
             * --------------------------------------------------
             * CBT Progress
             * --------------------------------------------------
             *
             * This is intentionally NOT part of the
             * bottom navigation.
             *
             * It is accessed from CBT Home and provides
             * the user's completed CBT activity timeline.
             * --------------------------------------------------
             */

            composable(
                route = Screen.CBTProgress.route
            ) {

                CBTProgressScreen(

                    onBackClick = {

                        navController.popBackStack()
                    }
                )
            }

            /*
             * --------------------------------------------------
             * Activity Scheduling
             * --------------------------------------------------
             */

            composable(
                route = Screen.ActivityScheduling.route
            ) {

                ActivitySchedulingScreen(

                    activity =
                        BehavioralActivationExercises
                            .activityScheduling,

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onExerciseCompleted = {

                        /*
                         * The activity has already been
                         * persisted by the exercise screen.
                         *
                         * Return to CBT Home after completion.
                         */

                        navController.popBackStack()
                    }
                )
            }

            /*
             * --------------------------------------------------
             * History
             * --------------------------------------------------
             */

            composable(
                route = Screen.History.route
            ) {

                MoodHistoryScreen()
            }

            /*
             * --------------------------------------------------
             * Analytics
             * --------------------------------------------------
             */

            composable(
                route = Screen.Graph.route
            ) {

                MoodGraphScreen()
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
}

