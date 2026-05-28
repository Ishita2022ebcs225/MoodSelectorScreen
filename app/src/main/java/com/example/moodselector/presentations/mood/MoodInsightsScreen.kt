package com.example.moodselector.presentations.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.R
import androidx.compose.ui.graphics.vector.ImageVector

data class MoodOption(
    val label: String,
    val icon: ImageVector
)

@Composable
fun MoodInsightsScreen(
    viewModel: MoodViewModel = hiltViewModel()
) {

    val moods by viewModel.moodList.collectAsState()

    var moodText by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf("Happy") }

    val moodOptions = listOf(
        MoodOption("Happy", Icons.Default.SentimentVerySatisfied),
        MoodOption("Calm", Icons.Default.SelfImprovement),
        MoodOption("Neutral", Icons.Default.SentimentNeutral),
        MoodOption("Sad", Icons.Default.SentimentDissatisfied),
        MoodOption("Angry", Icons.Default.MoodBad)
    )

    val darkPurple = Color(0xFF6E63A8)
    val primaryPurple = Color(0xFF8F84C7)

    val softLavender = Color(0xFFE8E1F5)
    val mutedRose = Color(0xFFF1E2E8)
    val mistBlue = Color(0xFFE2EBF2)
    val warmGlass = Color(0xCCF4EFFA)

    val textDark = Color(0xFF1F1C24)

    val scores = moods.map {
        when (it.emoji) {
            "Happy" -> 5
            "Calm" -> 4
            "Neutral" -> 3
            "Sad" -> 2
            "Angry" -> 1
            else -> 3
        }
    }

    val averageMood =
        if (scores.isNotEmpty()) scores.average() else 0.0

    val insight = when {
        averageMood >= 4 -> "Your emotional wellness is improving beautifully ✨"
        averageMood >= 3 -> "Your emotional state has remained balanced 🌸"
        else -> "You've been emotionally overwhelmed lately 💙"
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ✅ SINGLE BACKGROUND IMAGE (REPLACES ANIMATION)
        Image(
            painter = painterResource(id = R.drawable.lavender_scenery),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🌫 overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0x44000000),
                            Color(0x33000000),
                            Color(0x66000000)
                        )
                    )
                )
        )

        Scaffold(containerColor = Color.Transparent) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                contentPadding = PaddingValues(bottom = 110.dp)
            ) {

                // 🌅 HERO SECTION
                item {
                    Box {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(285.dp)
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = 38.dp,
                                        bottomEnd = 38.dp
                                    )
                                )
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            darkPurple.copy(alpha = 0.78f),
                                            primaryPurple.copy(alpha = 0.60f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        ) {

                            Column(
                                modifier = Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 18.dp
                                )
                            ) {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Column {

                                        Text(
                                            text = "Hello 🌸",
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = "Track your emotional wellbeing",
                                            color = Color.White.copy(alpha = 0.9f)
                                        )
                                    }

                                    Surface(
                                        shape = CircleShape,
                                        color = Color.White.copy(alpha = 0.16f)
                                    ) {
                                        Box(
                                            modifier = Modifier.size(48.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Notifications,
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(34.dp))

                                Text(
                                    text = "${String.format("%.1f", averageMood)}",
                                    style = MaterialTheme.typography.displayMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Current emotional score",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White.copy(alpha = 0.92f)
                                )
                            }
                        }

                        // 🌸 FLOATING CARD
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp)
                                .offset(y = 210.dp)
                                .shadow(12.dp, RoundedCornerShape(28.dp)),
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = warmGlass
                            )
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column {
                                    Text("Average Mood", color = Color.Gray)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = String.format("%.1f", averageMood),
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = textDark
                                    )
                                }

                                Column {
                                    Text("Trend", color = Color.Gray)
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = when {
                                            averageMood >= 4 -> "Positive"
                                            averageMood >= 3 -> "Stable"
                                            else -> "Low"
                                        },
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = darkPurple
                                    )
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(130.dp)) }

                // 🌸 MOOD PICKER
                item {
                    Card(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = softLavender.copy(alpha = 0.82f)
                        )
                    ) {

                        Column(modifier = Modifier.padding(20.dp)) {

                            Text(
                                text = "How do you feel today?",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = textDark
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                moodOptions.forEach { mood ->

                                    val selected = selectedMood == mood.label

                                    Box(
                                        modifier = Modifier
                                            .size(if (selected) 64.dp else 54.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (selected)
                                                    Brush.linearGradient(
                                                        listOf(darkPurple, primaryPurple)
                                                    )
                                                else
                                                    Brush.linearGradient(
                                                        listOf(
                                                            Color(0xFFF1ECFA),
                                                            Color(0xFFE5DEF4)
                                                        )
                                                    )
                                            )
                                            .clickable { selectedMood = mood.label },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = mood.icon,
                                            contentDescription = mood.label,
                                            tint = if (selected) Color.White else darkPurple,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(22.dp))

                            OutlinedTextField(
                                value = moodText,
                                onValueChange = { moodText = it },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Describe your emotions") },
                                shape = RoundedCornerShape(20.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White.copy(alpha = 0.35f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.18f),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )

                            Spacer(modifier = Modifier.height(22.dp))

                            Button(
                                onClick = {
                                    if (moodText.isNotBlank()) {
                                        viewModel.addMood(
                                            mood = moodText,
                                            emoji = selectedMood
                                        )
                                        moodText = ""
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = darkPurple
                                )
                            ) {
                                Text("Save Mood", color = Color.White)
                            }
                        }
                    }
                }

                // 🌸 INSIGHT
                item {
                    Card(
                        modifier = Modifier
                            .padding(18.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = mistBlue.copy(alpha = 0.78f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {

                            Text(
                                text = "AI Insight ✨",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(text = insight)
                        }
                    }
                }

                // 🌸 RECENT ENTRIES
                item {
                    Text(
                        text = "Recent Entries",
                        modifier = Modifier.padding(horizontal = 18.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                items(moods.takeLast(5).reversed()) { mood ->

                    Card(
                        modifier = Modifier
                            .padding(horizontal = 18.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = mutedRose.copy(alpha = 0.80f)
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.35f)),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = when (mood.emoji) {
                                        "Happy" -> "✨"
                                        "Calm" -> "🌿"
                                        "Neutral" -> "☁️"
                                        "Sad" -> "💙"
                                        "Angry" -> "🔥"
                                        else -> "🌸"
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Text(
                                    text = mood.mood,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = mood.timestamp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}