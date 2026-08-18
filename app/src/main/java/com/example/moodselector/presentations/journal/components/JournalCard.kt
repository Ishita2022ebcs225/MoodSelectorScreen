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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    textDark: Color,
    onEditClick: (JournalEntity) -> Unit,
    onDeleteClick: (JournalEntity) -> Unit
) {

    val configuration =
        LocalConfiguration.current

    val currentLocale =
        configuration.locales[0]

    /*
     * ==========================================================
     * THEME COLORS
     * ==========================================================
     */

    val colorScheme =
        MaterialTheme.colorScheme

    val cardBackground =
        colorScheme.surface

    val primaryText =
        colorScheme.onSurface

    val secondaryText =
        colorScheme.onSurfaceVariant

    val iconColor =
        colorScheme.onSurfaceVariant

    val contentBackground =
        colorScheme.surfaceVariant

    val moodEmoji =
        when (journal.mood) {

            "Happy" -> "☀️"
            "Calm" -> "🌿"
            "Neutral" -> "☁️"
            "Sad" -> "🌧️"
            "Angry" -> "🔥"

            else -> "✨"
        }

    /*
     * ==========================================================
     * MOOD GRADIENT
     * ==========================================================
     *
     * These remain intentionally colorful because they identify
     * the selected mood visually.
     */

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
                pastelPink.copy(
                    alpha = 0.7f
                )
            )
        }

    val formattedTime =
        remember(
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

    /*
     * ==========================================================
     * JOURNAL CARD
     * ==========================================================
     */

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
                containerColor =
                    cardBackground
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
        ) {

            /*
             * ==================================================
             * HEADER
             * ==================================================
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(

                    modifier =
                        Modifier
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
                        text =
                            moodEmoji,

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge
                    )
                }

                Spacer(
                    modifier =
                        Modifier.size(14.dp)
                )

                Column(

                    modifier =
                        Modifier.weight(1f),

                    verticalArrangement =
                        Arrangement.spacedBy(4.dp)
                ) {

                    /*
                     * Mood title
                     *
                     * Uses onSurface so it remains readable
                     * against the card surface in both themes.
                     */

                    Text(

                        text =
                            journal.mood,

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            primaryText
                    )

                    /*
                     * Timestamp
                     *
                     * Uses onSurfaceVariant rather than a fixed
                     * gray so it maintains contrast in Dark Mode.
                     */

                    Text(

                        text =
                            formattedTime,

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            secondaryText
                    )
                }

                /*
                 * ==================================================
                 * EDIT
                 * ==================================================
                 */

                IconButton(

                    onClick = {
                        onEditClick(journal)
                    }
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Edit,

                        contentDescription =
                            "Edit journal entry",

                        tint =
                            iconColor
                    )
                }

                /*
                 * ==================================================
                 * DELETE
                 * ==================================================
                 */

                IconButton(

                    onClick = {
                        onDeleteClick(journal)
                    }
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Delete,

                        contentDescription =
                            "Delete journal entry",

                        tint =
                            iconColor
                    )
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(18.dp)
        )

        /*
         * ==========================================================
         * JOURNAL CONTENT
         * ==========================================================
         *
         * The old version used a white gradient here, which would
         * create a bright block in Dark Mode.
         *
         * surfaceVariant is used instead so the content area
         * automatically follows the active theme.
         */

        Box(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(24.dp)
                    )
                    .background(
                        contentBackground
                    )
                    .padding(18.dp)
        ) {

            Text(

                text =
                    journal.content,

                style =
                    MaterialTheme
                        .typography
                        .bodyLarge,

                /*
                 * Important:
                 *
                 * onSurfaceVariant gives the journal text enough
                 * contrast without forcing a black/white color.
                 */

                color =
                    colorScheme.onSurfaceVariant,

                lineHeight =
                    MaterialTheme
                        .typography
                        .bodyLarge
                        .lineHeight * 1.15,

                maxLines =
                    8,

                overflow =
                    TextOverflow.Ellipsis
            )
        }
    }
}