package com.example.moodselector.presentations.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ReadingStoryScreen(
    story: ReadingStory,
    onBackClick: () -> Unit
) {

    val background =
        MaterialTheme.colorScheme.background

    val primaryText =
        MaterialTheme.colorScheme.onSurface

    val secondaryText =
        MaterialTheme.colorScheme.onSurfaceVariant

    val accentPurple =
        Color(0xFF6E63A8)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(background)
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
                        horizontal = 12.dp,
                        vertical = 8.dp
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

            Text(
                text =
                    "Reading",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    secondaryText
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
         * STORY CONTENT
         * ==================================================
         */

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 24.dp
                    )
        ) {

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )


            /*
             * ==================================================
             * STORY TITLE
             * ==================================================
             */

            Text(
                text =
                    story.title,

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                fontWeight =
                    FontWeight.Bold,

                color =
                    primaryText,

                modifier =
                    Modifier.fillMaxWidth(),

                textAlign =
                    TextAlign.Center
            )


            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )


            /*
             * ==================================================
             * STORY SUBTITLE
             * ==================================================
             */

            Text(
                text =
                    story.subtitle,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                fontWeight =
                    FontWeight.Medium,

                color =
                    accentPurple,

                modifier =
                    Modifier.fillMaxWidth(),

                textAlign =
                    TextAlign.Center
            )


            Spacer(
                modifier =
                    Modifier.height(28.dp)
            )


            /*
             * ==================================================
             * STORY
             * ==================================================
             */

            Text(
                text =
                    story.story,

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge,

                color =
                    primaryText,

                modifier =
                    Modifier.fillMaxWidth()
            )


            Spacer(
                modifier =
                    Modifier.height(40.dp)
            )
        }
    }
}

