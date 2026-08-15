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
                bottom = 110.dp
            ),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            /*
             * ==================================================
             * HEADER
             * ==================================================
             */

            item {

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(215.dp)
                        .clip(

                            RoundedCornerShape(
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp
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

                    /*
                     * --------------------------------------------------
                     * DECORATIVE CIRCLES
                     * --------------------------------------------------
                     */

                    Box(
                        modifier = Modifier
                            .size(125.dp)
                            .padding(
                                top = 18.dp,
                                start = 245.dp
                            )
                            .clip(CircleShape)
                            .background(
                                Color.White.copy(
                                    alpha = 0.08f
                                )
                            )
                    )

                    Box(
                        modifier = Modifier
                            .size(85.dp)
                            .padding(
                                top = 135.dp,
                                start = 24.dp
                            )
                            .clip(CircleShape)
                            .background(
                                Color.White.copy(
                                    alpha = 0.06f
                                )
                            )
                    )

                    Column(

                        modifier =
                            Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 18.dp
                            )
                    ) {

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        /*
                         * --------------------------------------------------
                         * TITLE
                         * --------------------------------------------------
                         */

                        Text(

                            text =
                                "Mood History 🌸",

                            style =
                                MaterialTheme
                                    .typography
                                    .headlineMedium,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.White
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        /*
                         * --------------------------------------------------
                         * DESCRIPTION
                         * --------------------------------------------------
                         */

                        Text(

                            text =
                                "Review your emotional journey and understand how your moods evolve over time.",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            color =
                                Color.White.copy(
                                    alpha = 0.92f
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.height(18.dp)
                        )

                        /*
                         * --------------------------------------------------
                         * ENTRY COUNT
                         * --------------------------------------------------
                         */

                        Card(

                            shape =
                                RoundedCornerShape(
                                    20.dp
                                ),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Color.White.copy(
                                            alpha = 0.18f
                                        )
                                )
                        ) {

                            Text(

                                text =
                                    "${moods.size} mood entries recorded",

                                modifier =
                                    Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 10.dp
                                    ),

                                color =
                                    Color.White,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            /*
             * ==================================================
             * EMPTY STATE
             * ==================================================
             */

            if (moods.isEmpty()) {

                item {

                    Box(
                        modifier =
                            Modifier.padding(
                                horizontal = 18.dp
                            )
                    ) {

                        EmptyMoodState()
                    }
                }

            } else {

                /*
                 * ==================================================
                 * MOOD ENTRIES
                 * ==================================================
                 */

                items(
                    moods.reversed()
                ) { mood ->

                    Box(
                        modifier =
                            Modifier.padding(
                                horizontal = 18.dp
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
                    modifier =
                        Modifier.height(20.dp)
                )
            }
        }
    }
}

