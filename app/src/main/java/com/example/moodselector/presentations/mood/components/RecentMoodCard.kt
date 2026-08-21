package com.example.moodselector.presentations.mood.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodselector.data.local.entity.MoodEntry

@Composable
fun RecentMoodCard(
    mood: MoodEntry
) {

    val pastelPurple =
        MaterialTheme.colorScheme.secondaryContainer

    val isDarkTheme =
        MaterialTheme.colorScheme.background.luminance() < 0.5f

    val textPrimary =
        if (isDarkTheme) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    val textSecondary =
        MaterialTheme.colorScheme.onSurfaceVariant

    val emoji =
        when (mood.emoji) {

            "Happy" -> "😊"

            "Calm" -> "😌"

            "Neutral" -> "😐"

            "Sad" -> "😔"

            "Angry" -> "😠"

            else -> "🌸"
        }

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
    ) {

        Row(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp,
                        vertical = 14.dp
                    ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            /*
             * --------------------------------------------------
             * MOOD EMOJI
             * --------------------------------------------------
             */

            Box(

                modifier =
                    Modifier
                        .size(52.dp)
                        .background(
                            color = pastelPurple,
                            shape = CircleShape
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(

                    text =
                        emoji,

                    fontSize =
                        28.sp
                )
            }


            Spacer(
                modifier =
                    Modifier.width(14.dp)
            )


            /*
             * --------------------------------------------------
             * MOOD INFORMATION
             * --------------------------------------------------
             */

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(

                    text =
                        mood.mood,

                    fontSize =
                        16.sp,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        textPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(

                    text =
                        mood.timestamp,

                    fontSize =
                        12.sp,

                    color =
                        textSecondary
                )

                /*
                 * --------------------------------------------------
                 * TRIGGER
                 * --------------------------------------------------
                 */

                if (mood.trigger.isNotBlank()) {

                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )

                    Text(

                        text =
                            "Trigger: ${mood.trigger}",

                        fontSize =
                            12.sp,

                        color =
                            textSecondary
                    )
                }
            }
        }
    }
}