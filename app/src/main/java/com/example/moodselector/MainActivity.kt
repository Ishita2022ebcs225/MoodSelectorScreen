package com.example.moodselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.moodselector.presentations.navigation.AppNavHost
import com.example.moodselector.presentations.navigation.Screen
import com.example.moodselector.presentations.navigation.StartupViewModel
import com.example.moodselector.presentations.theme.MoodselectorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        enableEdgeToEdge()

        setContent {

            MoodselectorTheme {

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


                when {

                    /*
                     * ----------------------------------------------
                     * USER NOT SIGNED IN
                     * ----------------------------------------------
                     */

                    currentUser == null -> {

                        val navController =
                            rememberNavController()

                        AppNavHost(

                            navController =
                                navController,

                            startDestination =
                                Screen.Login.route
                        )
                    }


                    /*
                     * ----------------------------------------------
                     * ASSESSMENT STATE LOADING
                     * ----------------------------------------------
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
                     * SIGNED-IN USER
                     * ----------------------------------------------
                     */

                    else -> {

                        val navController =
                            rememberNavController()

                        val startDestination =
                            if (
                                hasCompletedAssessment == true
                            ) {

                                /*
                                 * Existing user who already
                                 * completed the assessment.
                                 */

                                Screen.Insights.route

                            } else {

                                /*
                                 * New user who has not yet
                                 * completed the assessment.
                                 */

                                Screen.AssessmentOnboarding.route
                            }


                        AppNavHost(

                            navController =
                                navController,

                            startDestination =
                                startDestination
                        )
                    }
                }
            }
        }
    }
}