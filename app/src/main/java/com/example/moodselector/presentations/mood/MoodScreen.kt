package com.example.moodselector.presentations.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun MoodScreen() {

    val background = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF8F5FF),
            Color(0xFFEDE7FF),
            Color(0xFFFDFBFF)
        )
    )

    Scaffold(
        containerColor = Color.Transparent
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(paddingValues)
                .statusBarsPadding()
        ) {

            MoodInsightsScreen()
        }
    }
}
