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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.moodselector.domain.cbt.definitions.BehavioralActivationExercises
import com.example.moodselector.presentations.assessment.onboarding.AssessmentOnboardingScreen
import com.example.moodselector.presentations.assessment.questionnaire.AssessmentQuestionnaireScreen
import com.example.moodselector.presentations.assessment.results.AssessmentResultsScreen
import com.example.moodselector.presentations.auth.AuthViewModel
import com.example.moodselector.presentations.auth.LoginScreen
import com.example.moodselector.presentations.auth.RegisterScreen
import com.example.moodselector.presentations.cbt.exercises.ABCModelScreen
import com.example.moodselector.presentations.cbt.exercises.ActivitySchedulingScreen
import com.example.moodselector.presentations.cbt.exercises.FiveMinuteStarterCompletionScreen
import com.example.moodselector.presentations.cbt.exercises.FiveMinuteStarterScreen
import com.example.moodselector.presentations.cbt.exercises.Grounding54321Screen
import com.example.moodselector.presentations.cbt.exercises.MindfulMeditationScreen
import com.example.moodselector.presentations.cbt.exercises.ScheduledActivitiesScreen
import com.example.moodselector.presentations.cbt.exercises.ScheduledActivityCompletionScreen
import com.example.moodselector.presentations.cbt.exercises.ScheduledCBTActivityViewModel
import com.example.moodselector.presentations.cbt.exercises.SelfCompassionReflectionScreen
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
     * ==================================================
     * AUTH VIEWMODEL
     * ==================================================
     */

    val authViewModel: AuthViewModel =
        hiltViewModel()


    /*
     * ==================================================
     * CURRENT NAVIGATION DESTINATION
     * ==================================================
     */

    val currentBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        currentBackStackEntry?.destination?.route


    /*
     * ==================================================
     * BOTTOM NAVIGATION ITEMS
     * ==================================================
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
     * ==================================================
     * BOTTOM NAVIGATION VISIBILITY
     * ==================================================
     */

    val showBottomNavigation =
        currentRoute == Screen.Insights.route ||
                currentRoute == Screen.CBTHome.route ||
                currentRoute == Screen.History.route ||
                currentRoute == Screen.Graph.route ||
                currentRoute == Screen.Journal.route


    /*
     * ==================================================
     * APP COLORS
     * ==================================================
     */

    val darkPurple =
        Color(0xFF6C63FF)


    /*
     * ==================================================
     * SCAFFOLD
     * ==================================================
     */

    Scaffold(

        containerColor =
            Color.Transparent,

        bottomBar = {

            if (showBottomNavigation) {

                NavigationBar(

                    containerColor =
                        Color.White.copy(
                            alpha = 0.96f
                        ),

                    tonalElevation =
                        12.dp,

                    modifier =
                        Modifier
                            .navigationBarsPadding()
                            .shadow(
                                elevation = 22.dp,
                                shape =
                                    RoundedCornerShape(
                                        30.dp
                                    )
                            )
                            .clip(
                                RoundedCornerShape(
                                    30.dp
                                )
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

                                        launchSingleTop =
                                            true
                                    }
                                }
                            },

                            icon = {

                                Icon(
                                    imageVector =
                                        item.icon,

                                    contentDescription =
                                        item.label,

                                    tint =
                                        Color.Unspecified
                                )
                            },

                            label = {

                                Text(
                                    text =
                                        item.label
                                )
                            },

                            colors =
                                NavigationBarItemDefaults
                                    .colors(

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

        NavHost(

            navController =
                navController,

            startDestination =
                startDestination,

            modifier =
                Modifier.padding(
                    paddingValues
                )

        ) {

            /*
             * ==================================================
             * LOGIN
             * ==================================================
             *
             * Authentication success is intentionally not
             * handled by navigation here.
             *
             * MainActivity observes Firebase authentication
             * state through StartupViewModel and recreates
             * the appropriate navigation flow.
             */

            composable(
                route =
                    Screen.Login.route
            ) {

                LoginScreen(

                    onLoginSuccess = {
                        /*
                         * No direct navigation.
                         *
                         * StartupViewModel observes the Firebase
                         * authentication state and determines the
                         * correct startup destination after cloud
                         * synchronization.
                         */
                    },

                    onRegisterClick = {

                        navController.navigate(
                            Screen.Register.route
                        ) {

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * REGISTER
             * ==================================================
             *
             * Registration success is intentionally not
             * navigated directly to assessment.
             *
             * MainActivity observes the newly authenticated
             * Firebase user through StartupViewModel.
             *
             * A new user will therefore reach the assessment
             * onboarding after synchronization.
             */

            composable(
                route =
                    Screen.Register.route
            ) {

                RegisterScreen(

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onRegistrationSuccess = {
                        /*
                         * No direct navigation.
                         *
                         * StartupViewModel observes the Firebase
                         * authentication state and determines the
                         * correct startup destination.
                         */
                    },

                    onLoginClick = {

                        navController.navigate(
                            Screen.Login.route
                        ) {

                            popUpTo(
                                Screen.Register.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * ASSESSMENT ONBOARDING
             * ==================================================
             */

            composable(
                route =
                    Screen.AssessmentOnboarding.route
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
             * ==================================================
             * ASSESSMENT QUESTIONNAIRE
             * ==================================================
             */

            composable(
                route =
                    Screen.AssessmentQuestionnaire.route
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
             * ==================================================
             * ASSESSMENT RESULTS
             * ==================================================
             */

            composable(
                route =
                    Screen.AssessmentResults.route
            ) {

                AssessmentResultsScreen(

                    onContinueClicked = {

                        navController.navigate(
                            Screen.Insights.route
                        ) {

                            popUpTo(
                                Screen.AssessmentOnboarding.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * HOME
             * ==================================================
             */

            composable(
                route =
                    Screen.Insights.route
            ) {

                MoodInsightsScreen(

                    onLogout = {

                        /*
                         * Sign out from Firebase first.
                         *
                         * Room data is NOT deleted.
                         * It remains associated with the
                         * user's Firebase UID.
                         */

                        authViewModel.signOut()

                        /*
                         * Return to Login and clear the
                         * authenticated navigation stack.
                         */

                        navController.navigate(
                            Screen.Login.route
                        ) {

                            popUpTo(0) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * CBT HOME
             * ==================================================
             */

            composable(
                route =
                    Screen.CBTHome.route
            ) {

                CBTHomeScreen(

                    onActivityClick = { activity ->

                        when (activity.id) {

                            "activity_scheduling" -> {

                                navController.navigate(
                                    Screen.ActivityScheduling.route
                                ) {

                                    launchSingleTop =
                                        true
                                }
                            }

                            "five_minute_starter" -> {

                                navController.navigate(
                                    Screen.FiveMinuteStarter.route
                                ) {

                                    launchSingleTop =
                                        true
                                }
                            }

                            "mindful_meditation" -> {

                                navController.navigate(
                                    Screen.MindfulMeditation.route
                                ) {

                                    launchSingleTop =
                                        true
                                }
                            }

                            "grounding_54321" -> {

                                navController.navigate(
                                    Screen.Grounding54321.route
                                ) {

                                    launchSingleTop =
                                        true
                                }
                            }

                            "abc_model" -> {

                                navController.navigate(
                                    Screen.ABCModel.route
                                ) {

                                    launchSingleTop =
                                        true
                                }
                            }

                            "self_compassion_reflection" -> {

                                navController.navigate(
                                    Screen.SelfCompassionReflection.route
                                ) {

                                    launchSingleTop =
                                        true
                                }
                            }
                        }
                    },

                    onProgressClick = {

                        navController.navigate(
                            Screen.CBTProgress.route
                        ) {

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * CBT PROGRESS
             * ==================================================
             */

            composable(
                route =
                    Screen.CBTProgress.route
            ) {

                CBTProgressScreen(

                    onBackClick = {

                        navController.popBackStack()
                    }
                )
            }


            /*
             * ==================================================
             * ABC MODEL
             * ==================================================
             */

            composable(
                route =
                    Screen.ABCModel.route
            ) {

                ABCModelScreen(

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onComplete = {

                        navController.navigate(
                            Screen.CBTHome.route
                        ) {

                            popUpTo(
                                Screen.ABCModel.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * SELF-COMPASSION REFLECTION
             * ==================================================
             */

            composable(
                route =
                    Screen.SelfCompassionReflection.route
            ) {

                SelfCompassionReflectionScreen(

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onCompleted = {

                        navController.navigate(
                            Screen.CBTHome.route
                        ) {

                            popUpTo(
                                Screen.SelfCompassionReflection.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * ACTIVITY SCHEDULING
             * ==================================================
             */

            composable(
                route =
                    Screen.ActivityScheduling.route
            ) {

                ActivitySchedulingScreen(

                    activity =
                        BehavioralActivationExercises
                            .activityScheduling,

                    scheduledActivityId =
                        null,

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onExerciseCompleted = {

                        navController.navigate(
                            Screen.ActivityScheduling.route
                        ) {

                            popUpTo(
                                Screen.ActivityScheduling.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    },

                    onViewScheduledActivities = {

                        navController.navigate(
                            Screen.ScheduledActivities.route
                        ) {

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * ACTIVITY SCHEDULING — EDIT
             * ==================================================
             */

            composable(

                route =
                    Screen.ActivitySchedulingEdit.route,

                arguments =
                    listOf(

                        navArgument(
                            "scheduledActivityId"
                        ) {

                            type =
                                NavType.IntType
                        }
                    )

            ) { backStackEntry ->

                val scheduledActivityId =
                    backStackEntry.arguments?.getInt(
                        "scheduledActivityId"
                    )

                ActivitySchedulingScreen(

                    activity =
                        BehavioralActivationExercises
                            .activityScheduling,

                    scheduledActivityId =
                        scheduledActivityId,

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onExerciseCompleted = {

                        navController.navigate(
                            Screen.ActivityScheduling.route
                        ) {

                            popUpTo(
                                Screen.ActivitySchedulingEdit.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    },

                    onViewScheduledActivities = {

                        navController.navigate(
                            Screen.ScheduledActivities.route
                        ) {

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * SCHEDULED ACTIVITIES
             * ==================================================
             */

            composable(
                route =
                    Screen.ScheduledActivities.route
            ) {

                ScheduledActivitiesScreen(

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onScheduleActivityClick = {

                        navController.navigate(
                            Screen.ActivityScheduling.route
                        ) {

                            launchSingleTop =
                                true
                        }
                    },

                    onEditActivity = {
                            scheduledActivity ->

                        navController.navigate(

                            Screen.ActivitySchedulingEdit
                                .createRoute(
                                    scheduledActivity.id
                                )

                        ) {

                            launchSingleTop =
                                true
                        }
                    },

                    onCompleteActivity = {
                            scheduledActivity ->

                        navController.navigate(

                            Screen.ScheduledActivityCompletion
                                .createRoute(
                                    scheduledActivity.id
                                )

                        ) {

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * SCHEDULED ACTIVITY COMPLETION
             * ==================================================
             */

            composable(

                route =
                    Screen.ScheduledActivityCompletion.route,

                arguments =
                    listOf(

                        navArgument(
                            "scheduledActivityId"
                        ) {

                            type =
                                NavType.IntType
                        }
                    )

            ) { backStackEntry ->

                val scheduledActivityId =
                    backStackEntry.arguments?.getInt(
                        "scheduledActivityId"
                    )

                val scheduledViewModel:
                        ScheduledCBTActivityViewModel =
                    hiltViewModel()

                val scheduledActivities by
                scheduledViewModel
                    .scheduledActivities
                    .collectAsStateWithLifecycle(
                        initialValue =
                            emptyList()
                    )

                val scheduledActivity =
                    scheduledActivities.firstOrNull {

                        it.id ==
                                scheduledActivityId
                    }

                if (scheduledActivity != null) {

                    ScheduledActivityCompletionScreen(

                        scheduledActivity =
                            scheduledActivity,

                        onBackClick = {

                            navController.popBackStack()
                        },

                        onActivityCompleted = {

                            navController.popBackStack()
                        }
                    )
                }
            }


            /*
             * ==================================================
             * FIVE-MINUTE STARTER
             * ==================================================
             */

            composable(
                route =
                    Screen.FiveMinuteStarter.route
            ) {

                FiveMinuteStarterScreen(

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onExerciseCompleted = {
                            task,
                            firstStep ->

                        navController.navigate(

                            Screen.FiveMinuteStarterCompletion
                                .createRoute(
                                    task = task,
                                    firstStep = firstStep
                                )

                        ) {

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * FIVE-MINUTE STARTER — COMPLETION
             * ==================================================
             */

            composable(

                route =
                    Screen.FiveMinuteStarterCompletion.route,

                arguments =
                    listOf(

                        navArgument("task") {

                            type =
                                NavType.StringType

                            defaultValue =
                                ""
                        },

                        navArgument("firstStep") {

                            type =
                                NavType.StringType

                            defaultValue =
                                ""
                        }
                    )

            ) { backStackEntry ->

                val task =
                    backStackEntry.arguments
                        ?.getString("task")
                        .orEmpty()

                val firstStep =
                    backStackEntry.arguments
                        ?.getString("firstStep")
                        .orEmpty()

                FiveMinuteStarterCompletionScreen(

                    task =
                        task,

                    firstStep =
                        firstStep,

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onCompleted = {

                        navController.navigate(
                            Screen.CBTHome.route
                        ) {

                            popUpTo(
                                Screen.FiveMinuteStarter.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * MINDFUL MEDITATION
             * ==================================================
             */

            composable(
                route =
                    Screen.MindfulMeditation.route
            ) {

                MindfulMeditationScreen(

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onComplete = {

                        navController.navigate(
                            Screen.CBTHome.route
                        ) {

                            popUpTo(
                                Screen.MindfulMeditation.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * 5-4-3-2-1 GROUNDING
             * ==================================================
             */

            composable(
                route =
                    Screen.Grounding54321.route
            ) {

                Grounding54321Screen(

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onComplete = {

                        navController.navigate(
                            Screen.CBTHome.route
                        ) {

                            popUpTo(
                                Screen.Grounding54321.route
                            ) {

                                inclusive =
                                    true
                            }

                            launchSingleTop =
                                true
                        }
                    }
                )
            }


            /*
             * ==================================================
             * HISTORY
             * ==================================================
             */

            composable(
                route =
                    Screen.History.route
            ) {

                MoodHistoryScreen()
            }


            /*
             * ==================================================
             * ANALYTICS
             * ==================================================
             */

            composable(
                route =
                    Screen.Graph.route
            ) {

                MoodGraphScreen()
            }


            /*
             * ==================================================
             * JOURNAL
             * ==================================================
             */

            composable(
                route =
                    Screen.Journal.route
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
             * ==================================================
             * JOURNAL EDITOR
             * ==================================================
             */

            composable(
                route =
                    Screen.JournalEditor.route
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

