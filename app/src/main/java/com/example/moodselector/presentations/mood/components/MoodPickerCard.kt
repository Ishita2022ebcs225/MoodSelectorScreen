package com.example.moodselector.presentations.mood.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MoodPickerCard(
    moodText: String,
    selectedEmoji: String,
    onMoodTextChange: (String) -> Unit,
    onEmojiSelected: (String) -> Unit,
    onSaveMood: () -> Unit
) {

    val darkPurple = Color(0xFF6C63FF)
    val primaryPurple = Color(0xFF8E7CFF)
    val pastelPurple = Color(0xFFEDE7FF)

    Card(

        shape = RoundedCornerShape(32.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        // FIXED PADDING
        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Text(
                text = "Log Your Mood",

                style = MaterialTheme
                    .typography
                    .titleLarge,

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                val emojis = listOf(
                    "😊",
                    "😌",
                    "😐",
                    "😔",
                    "😡"
                )

                emojis.forEach { emoji ->

                    val isSelected =
                        selectedEmoji == emoji

                    Box(

                        modifier = Modifier
                            .size(
                                if (isSelected)
                                    70.dp
                                else
                                    58.dp
                            )
                            .background(

                                brush =
                                    if (isSelected)
                                        Brush.linearGradient(
                                            listOf(
                                                darkPurple,
                                                primaryPurple
                                            )
                                        )

                                    else
                                        Brush.linearGradient(
                                            listOf(
                                                pastelPurple,
                                                pastelPurple
                                            )
                                        ),

                                shape = CircleShape
                            )
                            .clickable {
                                onEmojiSelected(emoji)
                            },

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text = emoji,

                            style = MaterialTheme
                                .typography
                                .headlineSmall
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            OutlinedTextField(

                value = moodText,

                onValueChange =
                    onMoodTextChange,

                modifier =
                    Modifier.fillMaxWidth(),

                label = {
                    Text("Describe your feelings")
                },

                shape =
                    RoundedCornerShape(20.dp),

                colors =
                    TextFieldDefaults.colors(

                        focusedContainerColor =
                            pastelPurple.copy(
                                alpha = 0.35f
                            ),

                        unfocusedContainerColor =
                            pastelPurple.copy(
                                alpha = 0.2f
                            ),

                        focusedIndicatorColor =
                            Color.Transparent,

                        unfocusedIndicatorColor =
                            Color.Transparent
                    )
            )

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Button(

                onClick = onSaveMood,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape =
                    RoundedCornerShape(22.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = darkPurple
                    )
            ) {

                Text(
                    text = "Save Mood",

                    style = MaterialTheme
                        .typography
                        .titleMedium,

                    color = Color.White
                )
            }
        }
    }
}