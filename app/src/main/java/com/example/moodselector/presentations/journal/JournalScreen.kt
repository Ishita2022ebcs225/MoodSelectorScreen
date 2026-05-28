package com.example.moodselector.presentations.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.R
import com.example.moodselector.presentations.journal.components.JournalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    viewModel: JournalViewModel = hiltViewModel(),
    onAddJournalClick: () -> Unit
) {

    val journals by viewModel.journals.collectAsState()

    val pastelPink = Color(0xFFEFD8DD)
    val textDark = Color(0xFF211C2F)
    val softPurple = Color(0xFFB7A9E6)

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌸 BACKGROUND IMAGE
        Image(
            painter = painterResource(id = R.drawable.journal_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.35f))
        )

        Scaffold(
            containerColor = Color.Transparent,

            topBar = {
                TopAppBar(
                    title = {

                        // ✅ ONLY CHANGE: centered heading block
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "📖 Journal",
                                style = TextStyle(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF7A4FA3),
                                            Color(0xFFC06C84),
                                            Color(0xFF8E6BAE)
                                        )
                                    )
                                )
                            )

                            Text(
                                text = "Your reflections",
                                style = MaterialTheme.typography.bodySmall,
                                color = textDark.copy(alpha = 0.6f)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },

            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddJournalClick,
                    containerColor = softPurple
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Add Journal",
                        tint = Color.White
                    )
                }
            }

        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if (journals.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.75f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Book,
                                    contentDescription = null,
                                    tint = softPurple,
                                    modifier = Modifier.size(48.dp)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "No journal entries yet",
                                    fontWeight = FontWeight.Bold,
                                    color = textDark
                                )

                                Text(
                                    text = "Start writing your thoughts",
                                    color = textDark.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }

                items(journals.reversed()) { journal ->
                    JournalCard(
                        journal = journal,
                        pastelPink = pastelPink,
                        textDark = textDark
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}