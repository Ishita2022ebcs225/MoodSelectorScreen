package com.example.moodselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PremiumMoodUI()
            }
        }
    }
}

data class MoodItem(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val color: Color
)

private const val UI_SCALE = 0.85f
private fun s(dp: Int): Dp = (dp * UI_SCALE).dp
private fun fs(sp: Int): TextUnit = (sp * UI_SCALE).sp

@Composable
fun PremiumMoodUI() {

    val moods = listOf(
        MoodItem("😊", "Happy", "Joyful", Color(0xFFFFC46B)),
        MoodItem("😌", "Calm", "Relaxed", Color(0xFF7CC7FF)),
        MoodItem("🌿", "Peaceful", "Balanced", Color(0xFF7ED6A7)),
        MoodItem("😢", "Sad", "Low mood", Color(0xFFD5A6FF)),
        MoodItem("😰", "Anxious", "Overthinking", Color(0xFFFFA07A)),
        MoodItem("😴", "Tired", "Low energy", Color(0xFFB39DDB))
    )

    var selectedMood by remember { mutableStateOf(moods[0]) }

    val animatedButtonColor by animateColorAsState(
        targetValue = selectedMood.color,
        animationSpec = tween(600),
        label = ""
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val floatingScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        ),
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
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = s(22), vertical = s(34))
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "☰", fontSize = fs(26), color = Color.White)

                Surface(
                    modifier = Modifier.size(s(56)),
                    shape = RoundedCornerShape(s(20)),
                    color = Color(0x33FFFFFF)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🌿", fontSize = fs(26))
                    }
                }
            }

            Spacer(modifier = Modifier.height(s(24)))

            Text("Good Morning ✨", fontSize = fs(18), color = Color(0xFF32254D))

            Spacer(modifier = Modifier.height(s(8)))

            Text(
                "How are you\nfeeling today?",
                fontSize = fs(36),
                fontWeight = FontWeight.Bold,
                lineHeight = fs(44),
                color = Color(0xFF1D1438)
            )

            Spacer(modifier = Modifier.height(s(10)))

            Text(
                "Take a gentle moment to check in with yourself",
                fontSize = fs(15),
                color = Color(0xFF55506B)
            )

            Spacer(modifier = Modifier.height(s(22)))

            // MOOD BOX
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(s(34)),
                colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF)),
                elevation = CardDefaults.cardElevation(s(6))
            ) {

                Column(
                    modifier = Modifier.padding(s(16)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "💜",
                        fontSize = fs(32),
                        modifier = Modifier.scale(floatingScale)
                    )

                    Spacer(modifier = Modifier.height(s(6)))

                    Text(
                        text = "Select your mood",
                        fontSize = fs(20),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF241A42)
                    )

                    Spacer(modifier = Modifier.height(s(14)))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.height(s(280)),
                        verticalArrangement = Arrangement.spacedBy(s(10)),
                        horizontalArrangement = Arrangement.spacedBy(s(10))
                    ) {
                        items(moods) { mood ->

                            val isSelected = selectedMood.title == mood.title

                            val scale by animateFloatAsState(
                                targetValue = if (isSelected) 1.02f else 1f,
                                animationSpec = tween(200),
                                label = ""
                            )

                            Card(
                                modifier = Modifier
                                    .height(s(120))
                                    .scale(scale)
                                    .clickable { selectedMood = mood },
                                shape = RoundedCornerShape(s(24)),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected)
                                        mood.color.copy(alpha = 0.18f)
                                    else
                                        Color(0x22FFFFFF)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(s(12)),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(mood.emoji, fontSize = fs(26))

                                    Spacer(modifier = Modifier.height(s(6)))

                                    Text(
                                        mood.title,
                                        fontSize = fs(16),
                                        fontWeight = FontWeight.Bold,
                                        color = mood.color
                                    )

                                    Text(
                                        mood.subtitle,
                                        fontSize = fs(11),
                                        color = Color(0xFF666666),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(s(14)))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(s(52)),
                        shape = RoundedCornerShape(s(22)),
                        colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor)
                    ) {
                        Text("✨ Save Mood", fontSize = fs(16), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // BOTTOM NAV
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(s(68)),
                shape = RoundedCornerShape(s(24)),
                colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomNavItem("🏠", "Home", true)
                    BottomNavItem("📊", "History", false)
                    BottomNavItem("🌱", "Insights", false)
                    BottomNavItem("👤", "Profile", false)
                }
            }
        }
    }
}

@Composable
fun BottomNavItem(emoji: String, title: String, selected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.alpha(if (selected) 1f else 0.55f)
    ) {
        Text(emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            title,
            fontSize = 11.sp,
            color = if (selected) Color(0xFF7B61FF) else Color(0xFF5F5F5F)
        )
    }
}