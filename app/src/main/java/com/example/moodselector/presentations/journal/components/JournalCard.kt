package com.example.moodselector.presentations.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moodselector.data.local.entity.JournalEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalCard(
    journal: JournalEntity,
    pastelPink: Color,
    textDark: Color
) {

    // ✅ Observable locale
    val configuration = LocalConfiguration.current
    val currentLocale = configuration.locales[0]

    val moodEmoji =
        when (journal.mood) {

            "Happy" -> "☀️"
            "Calm" -> "🌿"
            "Neutral" -> "☁️"
            "Sad" -> "🌧️"
            "Angry" -> "🔥"

            else -> "✨"
        }

    val gradientColors =
        when (journal.mood) {

            "Happy" -> listOf(
                Color(0xFFFFE29F),
                Color(0xFFFFC78A)
            )

            "Calm" -> listOf(
                Color(0xFFCFE9E1),
                Color(0xFFAED9C8)
            )

            "Neutral" -> listOf(
                Color(0xFFE4E7EC),
                Color(0xFFD6DAE1)
            )

            "Sad" -> listOf(
                Color(0xFFD6E4FF),
                Color(0xFFB7CDFC)
            )

            "Angry" -> listOf(
                Color(0xFFFFD6D6),
                Color(0xFFFFB3B3)
            )

            else -> listOf(
                pastelPink,
                pastelPink.copy(alpha = 0.7f)
            )
        }

    val formattedTime = remember(
        journal.timestamp,
        currentLocale
    ) {

        try {

            SimpleDateFormat(
                "dd MMM yyyy • hh:mm a",
                currentLocale
            ).format(
                Date(journal.timestamp)
            )

        } catch (_: Exception) {

            ""
        }
    }

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(30.dp),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor = Color.White.copy(
                    alpha = 0.96f
                )
            )
    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            // TOP SECTION

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                // MOOD ORB

                Box(

                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                gradientColors
                            )
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = moodEmoji,
                        style =
                            MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(
                    modifier =
                        Modifier.size(14.dp)
                )

                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(4.dp)
                ) {

                    Text(

                        text = journal.mood,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold,

                        color = textDark
                    )

                    Text(

                        text = formattedTime,

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            textDark.copy(
                                alpha = 0.55f
                            )
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            // JOURNAL CONTENT

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(24.dp)
                    )
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                pastelPink.copy(alpha = 0.16f),
                                Color.White
                            )
                        )
                    )
                    .padding(18.dp)
            ) {

                Text(

                    text = journal.content,

                    style =
                        MaterialTheme
                            .typography
                            .bodyLarge,

                    color =
                        textDark.copy(
                            alpha = 0.92f
                        ),

                    lineHeight =
                        MaterialTheme
                            .typography
                            .bodyLarge
                            .lineHeight * 1.15,

                    maxLines = 8,

                    overflow =
                        TextOverflow.Ellipsis
                )
            }
        }
    }
}