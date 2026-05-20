package com.example.moodselector.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodselector.viewmodel.MoodViewModel

data class MoodItem(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val color: Color
)

@Composable
fun MoodScreen(moodViewModel: MoodViewModel) {

    val moods = listOf(
        MoodItem("😊", "Happy", "Joyful", Color(0xFFFFC46B)),
        MoodItem("😌", "Calm", "Relaxed", Color(0xFF7CC7FF)),
        MoodItem("🌿", "Peaceful", "Balanced", Color(0xFF7ED6A7)),
        MoodItem("😢", "Sad", "Low mood", Color(0xFFD5A6FF)),
        MoodItem("😰", "Anxious", "Overthinking", Color(0xFFFFA07A)),
        MoodItem("😴", "Tired", "Low energy", Color(0xFFB39DDB))
    )

    var selectedMood by remember { mutableStateOf(moods[0]) }

    val buttonColor by animateColorAsState(
        targetValue = selectedMood.color,
        animationSpec = tween(500),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFBEA7FF),
                        Color(0xFFF6CFE1),
                        Color(0xFFCAE7FF)
                    )
                )
            )
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "How are you feeling today?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D1438)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Select your mood",
                fontSize = 16.sp,
                color = Color(0xFF55506B)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mood Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {

                items(moods) { mood ->

                    val isSelected = selectedMood.title == mood.title

                    val scale by animateFloatAsState(
                        targetValue = if (isSelected) 1.05f else 1f,
                        animationSpec = tween(200),
                        label = ""
                    )

                    Card(
                        modifier = Modifier
                            .scale(scale)
                            .clickable { selectedMood = mood },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (isSelected) mood.color.copy(alpha = 0.2f)
                                else Color(0x22FFFFFF)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isSelected) 8.dp else 2.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = mood.emoji,
                                fontSize = 32.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = mood.title,
                                fontWeight = FontWeight.Bold,
                                color = mood.color
                            )

                            Text(
                                text = mood.subtitle,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Save Mood",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


