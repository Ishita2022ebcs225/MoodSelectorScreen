package com.example.moodselector.presentations.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ReadingScreen(
    onBackClick: () -> Unit,
    onStoryClick: (ReadingStory) -> Unit = {}
) {

    val isLightTheme =
        MaterialTheme.colorScheme.background.luminance() > 0.5f

    val background =
        MaterialTheme.colorScheme.background

    val cardColor =
        if (isLightTheme) {
            Color(0xFFF8F4FC)
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

    val accentColor =
        Color(0xFF6E63A8)

    val primaryText =
        MaterialTheme.colorScheme.onSurface

    val secondaryText =
        MaterialTheme.colorScheme.onSurfaceVariant


    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(background)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 18.dp
                )
    ) {

        /*
         * ==================================================
         * HEADER
         * ==================================================
         */

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 12.dp
                    ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,

                    contentDescription =
                        "Back",

                    tint =
                        primaryText
                )
            }

            Spacer(
                modifier =
                    Modifier.weight(1f)
            )

            Icon(
                imageVector =
                    Icons.Default.MenuBook,

                contentDescription =
                    null,

                tint =
                    accentColor
            )

            Spacer(
                modifier =
                    Modifier.padding(
                        horizontal = 4.dp
                    )
            )

            Text(
                text =
                    "Reading",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                fontWeight =
                    FontWeight.Bold,

                color =
                    primaryText
            )

            Spacer(
                modifier =
                    Modifier.weight(1f)
            )

            Spacer(
                modifier =
                    Modifier.padding(
                        horizontal = 24.dp
                    )
            )
        }


        /*
         * ==================================================
         * INTRODUCTION
         * ==================================================
         */

        Spacer(
            modifier =
                Modifier.height(10.dp)
        )

        Text(
            text =
                "Stories for quiet moments",

            style =
                MaterialTheme
                    .typography
                    .headlineMedium,

            fontWeight =
                FontWeight.SemiBold,

            color =
                primaryText,

            textAlign =
                TextAlign.Center,

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(
            text =
                "Take a moment to slow down and read a story. " +
                        "These gentle stories explore everyday experiences " +
                        "like anxiety, self-doubt, and finding your way forward.",

            style =
                MaterialTheme
                    .typography
                    .bodyMedium,

            color =
                secondaryText,

            textAlign =
                TextAlign.Center,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp
                    )
        )


        Spacer(
            modifier =
                Modifier.height(28.dp)
        )


        /*
         * ==================================================
         * STORY LIST
         * ==================================================
         */

        Column(
            modifier =
                Modifier.fillMaxWidth(),

            verticalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )
        ) {

            ReadingStories.stories.forEach { story ->

                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onStoryClick(story)
                            },

                    shape =
                        RoundedCornerShape(
                            24.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                cardColor
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation =
                                1.dp
                        )
                ) {

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    20.dp
                                ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        /*
                         * BOOK ICON
                         */

                        Box(
                            modifier =
                                Modifier
                                    .background(
                                        accentColor.copy(
                                            alpha =
                                                if (isLightTheme) {
                                                    0.12f
                                                } else {
                                                    0.22f
                                                }
                                        ),

                                        shape =
                                            RoundedCornerShape(
                                                16.dp
                                            )
                                    )
                                    .padding(
                                        14.dp
                                    )
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.MenuBook,

                                contentDescription =
                                    null,

                                tint =
                                    accentColor
                            )
                        }


                        Spacer(
                            modifier =
                                Modifier.padding(
                                    horizontal = 14.dp
                                )
                        )


                        /*
                         * STORY INFORMATION
                         */

                        Column(
                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    story.title,

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    primaryText
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(5.dp)
                            )

                            Text(
                                text =
                                    story.subtitle,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    secondaryText
                            )
                        }
                    }
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(32.dp)
        )
    }
}

