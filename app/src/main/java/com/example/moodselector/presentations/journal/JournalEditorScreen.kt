package com.example.moodselector.presentations.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEditorScreen(
    viewModel: JournalViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {

    val content by viewModel.content.collectAsState()
    val selectedMood by viewModel.selectedMood.collectAsState()

    val lavender = Color(0xFFD8CFF5)
    val mutedBlue = Color(0xFFDCE8F2)
    val softPurple = Color(0xFFB7A9E6)
    val textDark = Color(0xFF2A2438)

    val moodOptions = listOf("Happy", "Calm", "Neutral", "Sad", "Angry")

    Scaffold(
        containerColor = Color(0xFFF5F1FA),

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New Journal Entry",
                        fontWeight = FontWeight.Bold,
                        color = textDark
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F1FA)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // HEADER (unchanged)
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(listOf(lavender, mutedBlue))
                        )
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoStories,
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Write Freely",
                                fontWeight = FontWeight.Bold,
                                color = textDark
                            )
                            Text(
                                text = "Capture your thoughts and emotions",
                                color = textDark.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // ✨ UPDATED JOURNAL INPUT AREA (PAPER ONLY HERE)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.lavender_lined_paper),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.White.copy(alpha = 0.30f))
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = viewModel::updateContent,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    placeholder = { Text("Write about your day...") },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            // MOOD (unchanged)
            Text(
                text = "Mood",
                fontWeight = FontWeight.Bold,
                color = textDark
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                moodOptions.forEach { mood ->
                    val selected = selectedMood == mood

                    AssistChip(
                        onClick = { viewModel.updateMood(mood) },
                        label = { Text(mood) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (selected) softPurple else Color(0xFFF3DCE4),
                            labelColor = if (selected) Color.White else textDark
                        )
                    )
                }
            }

            // SAVE BUTTON (unchanged)
            Button(
                onClick = { viewModel.saveJournal() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = softPurple),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Save Entry",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}