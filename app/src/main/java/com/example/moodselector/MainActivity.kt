package com.example.moodselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.presentations.navigation.AppNavHost
import com.example.moodselector.presentations.navigation.Screen
import com.example.moodselector.presentations.navigation.StartupViewModel
import com.example.moodselector.presentations.theme.MoodselectorTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flowOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @javax.inject.Inject
    lateinit var userPreferencesRepository:
            UserPreferencesRepository

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        enableEdgeToEdge()

        setContent {

            val startupViewModel:
                    StartupViewModel =
                hiltViewModel()


            /*
             * --------------------------------------------------
             * AUTHENTICATION STATE
             * --------------------------------------------------
             */

            val currentUser by
            startupViewModel
                .currentUser
                .collectAsStateWithLifecycle()


            /*
             * --------------------------------------------------
             * ASSESSMENT STATE
             * --------------------------------------------------
             */

            val hasCompletedAssessment by
            startupViewModel
                .hasCompletedAssessment
                .collectAsStateWithLifecycle()


            /*
             * --------------------------------------------------
             * THEME STATE
             * --------------------------------------------------
             */

            val userId =
                currentUser?.uid

            val themeModeFlow =
                remember(userId) {

                    userId?.let {

                        userPreferencesRepository
                            .getThemeMode(it)

                    } ?: flowOf("system")
                }

            val themeMode by
            themeModeFlow
                .collectAsStateWithLifecycle(
                    initialValue = "system"
                )

            val systemDarkTheme =
                isSystemInDarkTheme()

            val darkTheme =
                when (themeMode) {

                    "dark" ->
                        true

                    "light" ->
                        false

                    else ->
                        systemDarkTheme
                }


            MoodselectorTheme(
                darkTheme = darkTheme,
                dynamicColor = false
            ) {

                val navController =
                    rememberNavController()


                /*
                 * --------------------------------------------------
                 * STARTUP
                 * --------------------------------------------------
                 */

                when {

                    /*
                     * ----------------------------------------------
                     * USER NOT SIGNED IN
                     * ----------------------------------------------
                     */

                    currentUser == null -> {

                        AppNavHost(

                            navController =
                                navController,

                            startDestination =
                                Screen.Login.route
                        )
                    }


                    /*
                     * ----------------------------------------------
                     * ASSESSMENT STATE STILL LOADING
                     * ----------------------------------------------
                     *
                     * The authenticated user is known, but
                     * StartupViewModel has not yet completed
                     * cloud synchronization and resolved the
                     * persisted assessment state.
                     */

                    hasCompletedAssessment == null -> {

                        Box(
                            modifier =
                                Modifier.fillMaxSize(),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            CircularProgressIndicator()
                        }
                    }


                    /*
                     * ----------------------------------------------
                     * ASSESSMENT NOT COMPLETED
                     * ----------------------------------------------
                     *
                     * Show the assessment onboarding screen.
                     * The user can either begin the assessment or
                     * choose "Skip for now".
                     */

                    hasCompletedAssessment == false -> {

                        AppNavHost(

                            navController =
                                navController,

                            startDestination =
                                Screen.AssessmentOnboarding.route
                        )
                    }


                    /*
                     * ----------------------------------------------
                     * ASSESSMENT COMPLETED
                     * ----------------------------------------------
                     *
                     * Users who have already completed the
                     * assessment go directly to Home.
                     */

                    else -> {

                        AppNavHost(

                            navController =
                                navController,

                            startDestination =
                                Screen.Insights.route
                        )
                    }
                }
            }
        }
    }
}
