package com.example.moodselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.moodselector.presentations.navigation.AppNavHost
import com.example.moodselector.presentations.theme.MoodselectorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MoodselectorTheme {

                // ✅ SINGLE GLOBAL NAV CONTROLLER
                val navController = rememberNavController()

                // ✅ ONLY ENTRY POINT TO NAVIGATION
                AppNavHost(navController = navController)
            }
        }
    }
}