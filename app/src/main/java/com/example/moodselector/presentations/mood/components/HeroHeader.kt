package com.example.moodselector.presentations.mood.components

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
@Composable
fun HeroHeader(
    averageMood: Double
) {

    val darkPurple = Color(0xFF6C63FF)
    val primaryPurple = Color(0xFF8E7CFF)

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(36.dp)
            )
            .background(

                brush = Brush.linearGradient(
                    listOf(
                        darkPurple,
                        primaryPurple,
                        Color(0xFFB39DFF)
                    )
                )
            )
            .padding(26.dp)
    ) {

        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Hello 🌸",

                        style = MaterialTheme
                            .typography
                            .headlineMedium,

                        fontWeight = FontWeight.Bold,

                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "How are you feeling today?",

                        color =
                            Color.White.copy(alpha = 0.9f)
                    )
                }

                Surface(
                    shape = CircleShape,

                    color =
                        Color.White.copy(alpha = 0.16f)
                ) {

                    Box(
                        modifier = Modifier.size(48.dp),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Notifications,

                            contentDescription = null,

                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Card(

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        Color.White.copy(alpha = 0.14f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Mood consistency",

                        color =
                            Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text =
                            "${String.format("%.1f", averageMood)}/5",

                        style = MaterialTheme
                            .typography
                            .headlineLarge,

                        fontWeight = FontWeight.Bold,

                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "Your emotional wellness is improving steadily.",

                        color =
                            Color.White.copy(alpha = 0.88f)
                    )
                }
            }
        }
    }
}