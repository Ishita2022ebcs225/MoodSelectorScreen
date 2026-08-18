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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant
        )
    )

    Scaffold(

        containerColor = Color.Transparent

    ) { paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(paddingValues)
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

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            bottomStart = 28.dp,
                            bottomEnd = 28.dp,
                            topStart = 22.dp,
                            topEnd = 22.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.Transparent
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 3.dp
                        )
                ) {

                    Column(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(

                                    Brush.verticalGradient(

                                        listOf(

                                            MaterialTheme
                                                .colorScheme
                                                .primary
                                                .copy(
                                                    alpha = 0.82f
                                                ),

                                            MaterialTheme
                                                .colorScheme
                                                .primary
                                                .copy(
                                                    alpha = 0.58f
                                                ),

                                            MaterialTheme
                                                .colorScheme
                                                .secondary
                                                .copy(
                                                    alpha = 0.42f
                                                )
                                        )
                                    )
                                )
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 20.dp
                                )
                    ) {

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
                                    .headlineSmall,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.White
                        )

                        Spacer(
                            modifier =
                                Modifier.height(5.dp)
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
                                    .bodySmall,

                            color =
                                Color.White.copy(
                                    alpha = 0.88f
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.height(14.dp)
                        )

                        /*
                         * --------------------------------------------------
                         * ENTRY COUNT
                         * --------------------------------------------------
                         */

                        Card(

                            shape =
                                RoundedCornerShape(
                                    16.dp
                                ),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Color.White.copy(
                                            alpha = 0.14f
                                        )
                                )
                        ) {

                            Text(

                                text =
                                    "${moods.size} mood entries recorded",

                                modifier =
                                    Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 8.dp
                                    ),

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelMedium,

                                fontWeight =
                                    FontWeight.SemiBold,

                                color =
                                    Color.White.copy(
                                        alpha = 0.94f
                                    )
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