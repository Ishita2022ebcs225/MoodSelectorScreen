package com.example.moodselector.presentations.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.moodselector.presentations.navigation.Screen

@Composable
fun MoodScreen(
    onOpenJournal: () -> Unit
) {

    val background = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF8F5FF),
            Color(0xFFEDE7FF),
            Color(0xFFFDFBFF)
        )
    )

    val darkPurple = Color(0xFF6C63FF)

    data class BottomNavigationItem(
        val screen: Screen,
        val label: String,
        val icon: androidx.compose.ui.graphics.vector.ImageVector
    )

    val items = listOf(
        BottomNavigationItem(
            screen = Screen.Insights,
            label = "Home",
            icon = Icons.Default.Home
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

    var selectedScreen by remember {
        mutableStateOf<Screen>(Screen.Insights)
    }

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

                items.forEach { item ->

                    NavigationBarItem(

                        selected = selectedScreen == item.screen,

                        onClick = {

                            if (item.screen == Screen.Journal) {

                                onOpenJournal()

                            } else {

                                selectedScreen = item.screen
                            }
                        },

                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },

                        label = {
                            Text(item.label)
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(paddingValues)
        ) {

            when (selectedScreen) {

                Screen.Insights -> MoodInsightsScreen()

                Screen.History -> MoodHistoryScreen()

                Screen.Graph -> MoodGraphScreen()

                // Journal is opened through navigation,
                // so nothing is displayed here.
                Screen.Journal -> Unit

                else -> Unit
            }
        }
    }
}