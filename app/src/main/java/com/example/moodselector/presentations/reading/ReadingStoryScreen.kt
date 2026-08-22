package com.example.moodselector.presentations.reading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ReadingStoryScreen(
    story: ReadingStory,
    onBackClick: () -> Unit
) {

    val isLightTheme =
        MaterialTheme.colorScheme.background.luminance() > 0.5f


    /*
     * ==================================================
     * THEME COLORS
     * ==================================================
     */

    val background =
        if (isLightTheme) {
            Color(0xFFF4E8FA)
        } else {
            Color(0xFF241B32)
        }

    val primaryText =
        if (isLightTheme) {
            Color(0xFF3E294D)
        } else {
            Color(0xFFF6EAFB)
        }

    val secondaryText =
        if (isLightTheme) {
            Color(0xFF765477)
        } else {
            Color(0xFFD8B9DC)
        }

    val accentPinkViolet =
        if (isLightTheme) {
            Color(0xFFB04A91)
        } else {
            Color(0xFFE19ACB)
        }


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
                        accentPinkViolet
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
                    accentPinkViolet
            )

            Spacer(
                modifier =
                    Modifier.weight(1f)
            )

            Spacer(
                modifier =
                    Modifier.size(48.dp)
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
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )


            /*
             * ==================================================
             * BOOK COVER
             * ==================================================
             *
             * The drawable resource is already stored directly
             * inside ReadingStory as coverResId.
             *
             * No Internet connection is required.
             */

            story.coverResId?.let { coverResId ->

                Card(
                    modifier =
                        Modifier
                            .size(
                                width = 180.dp,
                                height = 270.dp
                            ),

                    shape =
                        RoundedCornerShape(
                            18.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                if (isLightTheme) {
                                    Color.White.copy(
                                        alpha = 0.75f
                                    )
                                } else {
                                    Color.White.copy(
                                        alpha = 0.08f
                                    )
                                }
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation =
                                8.dp
                        )
                ) {

                    Image(
                        painter =
                            painterResource(
                                id = coverResId
                            ),

                        contentDescription =
                            "${story.title} book cover",

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .clip(
                                    RoundedCornerShape(
                                        18.dp
                                    )
                                ),

                        contentScale =
                            ContentScale.Crop
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(26.dp)
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
                    accentPinkViolet,

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