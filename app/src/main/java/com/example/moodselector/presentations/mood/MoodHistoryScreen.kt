package com.example.moodselector.presentations.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.presentations.mood.components.EmptyMoodState
import com.example.moodselector.presentations.mood.components.RecentMoodCard
import com.example.moodselector.presentations.mood.MoodViewModel

@Composable
fun MoodHistoryScreen(
    viewModel: MoodViewModel = hiltViewModel()
) {

    val moods by viewModel.moodList.collectAsState()

    val backgroundGradient = Brush.verticalGradient(

        colors = listOf(

            Color(0xFFF6F0FF),
            Color(0xFFFDFBFF),
            Color(0xFFEFE7FF)
        )
    )

    val darkPurple = Color(0xFF6C63FF)

    val primaryPurple = Color(0xFF8E7CFF)

    Scaffold(

        containerColor = Color.Transparent

    ) { paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(paddingValues)
                .statusBarsPadding()
                .navigationBarsPadding(),

            contentPadding = PaddingValues(
                bottom = 120.dp
            ),

            verticalArrangement =
                Arrangement.spacedBy(18.dp)
        ) {

            // HEADER
            item {

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(

                            RoundedCornerShape(
                                bottomStart = 42.dp,
                                bottomEnd = 42.dp
                            )
                        )
                        .background(

                            brush = Brush.verticalGradient(

                                listOf(
                                    darkPurple,
                                    primaryPurple,
                                    Color(0xFFB39DFF)
                                )
                            )
                        )
                ) {

                    // Decorative floating circles

                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .padding(
                                top = 20.dp,
                                start = 220.dp
                            )
                            .clip(CircleShape)
                            .background(
                                Color.White.copy(alpha = 0.08f)
                            )
                    )

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(
                                top = 150.dp,
                                start = 20.dp
                            )
                            .clip(CircleShape)
                            .background(
                                Color.White.copy(alpha = 0.06f)
                            )
                    )

                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = "Mood History 🌸",

                            style = MaterialTheme
                                .typography
                                .headlineLarge,

                            fontWeight = FontWeight.ExtraBold,

                            color = Color.White
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        Text(
                            text =
                                "Review your emotional journey and understand how your moods evolve over time.",

                            style = MaterialTheme
                                .typography
                                .bodyLarge,

                            color =
                                Color.White.copy(alpha = 0.92f)
                        )

                        Spacer(
                            modifier = Modifier.height(30.dp)
                        )

                        Card(

                            shape = RoundedCornerShape(26.dp),

                            colors = CardDefaults.cardColors(
                                containerColor =
                                    Color.White.copy(alpha = 0.18f)
                            )
                        ) {

                            Text(

                                text =
                                    "${moods.size} mood entries recorded",

                                modifier = Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 14.dp
                                ),

                                color = Color.White,

                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // EMPTY STATE
            if (moods.isEmpty()) {

                item {

                    Box(
                        modifier = Modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {

                        EmptyMoodState()
                    }
                }

            } else {

                // MOOD ENTRIES
                items(moods.reversed()) { mood ->

                    Box(
                        modifier = Modifier.padding(
                            horizontal = 20.dp
                        )
                    ) {

                        RecentMoodCard(
                            mood = mood
                        )
                    }
                }
            }

            item {

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}