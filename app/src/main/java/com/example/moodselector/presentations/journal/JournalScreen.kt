package com.example.moodselector.presentations.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.R
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.presentations.journal.components.JournalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    viewModel: JournalViewModel = hiltViewModel(),
    onAddJournalClick: () -> Unit,
    onEditJournalClick: (Int) -> Unit
) {

    val journals by
    viewModel.journals.collectAsState()

    var journalToDelete by
    remember {
        mutableStateOf<JournalEntity?>(null)
    }

    val pastelPink =
        MaterialTheme.colorScheme.tertiaryContainer

    val textDark =
        MaterialTheme.colorScheme.onBackground

    val softPurple =
        MaterialTheme.colorScheme.primary

    val isDarkTheme =
        MaterialTheme.colorScheme.background.luminance() < 0.5f

    Box(
        modifier =
            Modifier.fillMaxSize()
    ) {

        Image(
            painter =
                painterResource(
                    id = R.drawable.journal_background
                ),

            contentDescription = null,

            contentScale =
                ContentScale.Crop,

            modifier =
                Modifier.fillMaxSize()
        )

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            if (isDarkTheme) {
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            MaterialTheme.colorScheme.background.copy(
                                                alpha = 0.78f
                                            ),
                                            MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.28f
                                            ),
                                            MaterialTheme.colorScheme.background.copy(
                                                alpha = 0.84f
                                            )
                                        )
                                )
                            } else {
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            Color.White.copy(
                                                alpha = 0.35f
                                            ),
                                            Color.White.copy(
                                                alpha = 0.35f
                                            )
                                        )
                                )
                            }
                    )
        )

        Scaffold(
            containerColor =
                Color.Transparent,

            topBar = {

                TopAppBar(
                    title = {

                        Column(
                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "📖 Journal",

                                style =
                                    TextStyle(
                                        fontWeight =
                                            FontWeight.ExtraBold,

                                        fontSize =
                                            MaterialTheme
                                                .typography
                                                .headlineMedium
                                                .fontSize,

                                        brush =
                                            Brush.linearGradient(
                                                colors =
                                                    listOf(
                                                        MaterialTheme.colorScheme.primary,
                                                        MaterialTheme.colorScheme.tertiary,
                                                        MaterialTheme.colorScheme.secondary
                                                    )
                                            )
                                    )
                            )

                            Text(
                                text =
                                    "Your reflections",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    textDark.copy(
                                        alpha = 0.75f
                                    )
                            )
                        }
                    },

                    colors =
                        TopAppBarDefaults
                            .topAppBarColors(
                                containerColor =
                                    Color.Transparent
                            )
                )
            },

            floatingActionButton = {

                FloatingActionButton(
                    onClick =
                        onAddJournalClick,

                    containerColor =
                        softPurple
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Edit,

                        contentDescription =
                            "Add Journal",

                        tint =
                            MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        ) { padding ->

            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),

                contentPadding =
                    PaddingValues(16.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                if (journals.isEmpty()) {

                    item {

                        Card(
                            modifier =
                                Modifier.fillMaxWidth(),

                            colors =
                                CardDefaults
                                    .cardColors(
                                        containerColor =
                                            MaterialTheme
                                                .colorScheme
                                                .surfaceContainer
                                                .copy(
                                                    alpha = 0.90f
                                                )
                                    )
                        ) {

                            Column(
                                modifier =
                                    Modifier.padding(24.dp),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Book,

                                    contentDescription =
                                        null,

                                    tint =
                                        softPurple,

                                    modifier =
                                        Modifier.size(48.dp)
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(12.dp)
                                )

                                Text(
                                    text =
                                        "No journal entries yet",

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        textDark
                                )

                                Text(
                                    text =
                                        "Start writing your thoughts",

                                    color =
                                        textDark.copy(
                                            alpha = 0.70f
                                        )
                                )
                            }
                        }
                    }
                }

                items(
                    journals.reversed(),
                    key = { it.id }
                ) { journal ->

                    JournalCard(
                        journal =
                            journal,

                        pastelPink =
                            pastelPink,

                        textDark =
                            textDark,

                        onEditClick = {

                            onEditJournalClick(
                                it.id
                            )
                        },

                        onDeleteClick = {

                            journalToDelete =
                                it
                        }
                    )
                }

                item {

                    Spacer(
                        modifier =
                            Modifier
                                .height(80.dp)
                                .navigationBarsPadding()
                    )
                }
            }
        }
    }

    journalToDelete?.let { journal ->

        AlertDialog(

            onDismissRequest = {
                journalToDelete = null
            },

            title = {
                Text(
                    text =
                        "Delete journal entry?"
                )
            },

            text = {
                Text(
                    text =
                        "Are you sure you want to delete this journal entry? This action cannot be undone."
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        viewModel.deleteJournal(
                            journal
                        )

                        journalToDelete =
                            null
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                MaterialTheme.colorScheme.error
                        )
                ) {

                    Text(
                        text = "Delete",
                        color =
                            MaterialTheme.colorScheme.onError
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        journalToDelete = null
                    }
                ) {

                    Text(
                        text = "Cancel",
                        color = textDark
                    )
                }
            }
        )
    }
}