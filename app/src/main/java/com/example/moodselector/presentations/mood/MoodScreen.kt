package com.example.moodselector.presentations.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.example.moodselector.presentations.mood.MoodInsightsScreen
import com.example.moodselector.presentations.mood.MoodHistoryScreen
import com.example.moodselector.presentations.mood.MoodGraphScreen

@Composable
fun MoodScreen(
    onOpenJournal: () -> Unit
) {

    // 🌿 Calm-inspired soft gradient background
    val background = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF8F5FF),
            Color(0xFFEDE7FF),
            Color(0xFFFDFBFF)
        )
    )

    val darkPurple = Color(0xFF6C63FF)

    val items = listOf(
        "insights",
        "history",
        "graph",
        "journal"
    )

    var selectedTab by remember { mutableStateOf("insights") }

    Scaffold(
        containerColor = Color.Transparent,

        bottomBar = {

            NavigationBar(
                containerColor = Color.White.copy(alpha = 0.96f),
                tonalElevation = 12.dp,
                modifier = Modifier
                    .navigationBarsPadding()
                    .shadow(
                        elevation = 22.dp,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(RoundedCornerShape(30.dp))
            ) {

                items.forEach { screen ->

                    val icon = when (screen) {
                        "insights" -> Icons.Default.Home
                        "history" -> Icons.Default.History
                        "graph" -> Icons.Default.BarChart
                        "journal" -> Icons.Default.Edit
                        else -> Icons.Default.Home
                    }

                    val label = when (screen) {
                        "insights" -> "Home"
                        "history" -> "History"
                        "graph" -> "Analytics"
                        "journal" -> "Journal"
                        else -> screen
                    }

                    NavigationBarItem(
                        selected = selectedTab == screen,

                        onClick = {
                            selectedTab = screen
                            if (screen == "journal") {
                                onOpenJournal()
                            }
                        },

                        icon = {
                            Icon(icon, contentDescription = label)
                        },

                        label = {
                            Text(label)
                        },

                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = darkPurple,
                            indicatorColor = darkPurple,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }

    ) { paddingValues ->

        // 🌿 SAME LAYOUT — ONLY BACKGROUND IMPROVED
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(paddingValues)
        ) {

            when (selectedTab) {

                "insights" -> MoodInsightsScreen()

                "history" -> MoodHistoryScreen()

                "graph" -> MoodGraphScreen()

                "journal" -> MoodInsightsScreen()
            }
        }
    }
}