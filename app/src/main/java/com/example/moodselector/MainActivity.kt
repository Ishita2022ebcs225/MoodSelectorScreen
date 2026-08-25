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

            val authenticatedUser =
                currentUser


            /*
             * --------------------------------------------------
             * STARTUP READINESS
             * --------------------------------------------------
             *
             * Signed-in users must wait until their cloud
             * synchronization has completed before the
             * startup destination is selected.
             */

            val isReady by
            startupViewModel
                .isReady
                .collectAsStateWithLifecycle()


            /*
             * --------------------------------------------------
             * NEW USER STATE
             * --------------------------------------------------
             */

            val isNewUser by
            startupViewModel
                .isNewUser
                .collectAsStateWithLifecycle()


            /*
             * --------------------------------------------------
             * NEW USER RESOLUTION
             * --------------------------------------------------
             *
             * Prevents the NavHost from being created with
             * MoodInsights while isNewUser is still being
             * resolved.
             */

            val isNewUserResolved by
            startupViewModel
                .isNewUserResolved
                .collectAsStateWithLifecycle()


            /*
             * --------------------------------------------------
             * THEME STATE
             * --------------------------------------------------
             */

            val userId =
                authenticatedUser?.uid

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
                 *
                 * Signed-out users:
                 *      Login
                 *
                 * Signed-in users:
                 *      Wait until BOTH cloud synchronization
                 *      and new-user resolution are complete.
                 *
                 * Newly registered users:
                 *      AssessmentOnboarding
                 *
                 * Existing authenticated users:
                 *      MoodInsights
                 *
                 * This prevents MoodInsights from briefly
                 * appearing for a newly registered user.
                 */

                if (authenticatedUser == null) {

                    AppNavHost(

                        navController =
                            navController,

                        startDestination =
                            Screen.Login.route
                    )

                } else if (
                    !isReady ||
                    !isNewUserResolved
                ) {

                    Box(
                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        CircularProgressIndicator()
                    }

                } else {

                    AppNavHost(

                        navController =
                            navController,

                        startDestination =
                            if (isNewUser) {

                                Screen.AssessmentOnboarding.route

                            } else {

                                Screen.Insights.route
                            }
                    )
                }
            }
        }
    }
}
