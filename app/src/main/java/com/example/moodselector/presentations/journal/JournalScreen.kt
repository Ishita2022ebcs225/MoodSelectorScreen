package com.example.moodselector.presentations.journal

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.presentations.journal.components.JournalCard

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

    /*
     * ==========================================================
     * SCREEN
     * ==========================================================
     *
     * Uses the same background style as the rest of the app.
     *
     * The previous journal image background has been removed.
     */

    val backgroundGradient =
        Brush.verticalGradient(

            colors =
                listOf(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.colorScheme.surfaceVariant
                )
        )

    Scaffold(

        containerColor =
            Color.Transparent,

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
                        MaterialTheme
                            .colorScheme
                            .onPrimary
                )
            }
        }

    ) { paddingValues ->

        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        backgroundGradient
                    )
                    .padding(
                        paddingValues
                    )
                    .navigationBarsPadding(),

            contentPadding =
                PaddingValues(
                    bottom = 110.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    12.dp
                )
        ) {

            /*
             * ==================================================
             * HEADER
             * ==================================================
             */

            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            bottomStart = 28.dp,
                            bottomEnd = 28.dp,
                            topStart = 22.dp,
                            topEnd = 22.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.Transparent
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 3.dp
                        )
                ) {

                    Column(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(

                                    Brush.verticalGradient(

                                        listOf(

                                            MaterialTheme
                                                .colorScheme
                                                .primary
                                                .copy(
                                                    alpha = 0.82f
                                                ),

                                            MaterialTheme
                                                .colorScheme
                                                .primary
                                                .copy(
                                                    alpha = 0.58f
                                                ),

                                            MaterialTheme
                                                .colorScheme
                                                .secondary
                                                .copy(
                                                    alpha = 0.42f
                                                )
                                        )
                                    )
                                )
                                .padding(
                                    horizontal = 20.dp,
                                    vertical = 20.dp
                                )
                    ) {

                        /*
                         * --------------------------------------------------
                         * TITLE
                         * --------------------------------------------------
                         */

                        Text(

                            text =
                                "Journal 📖",

                            style =
                                MaterialTheme
                                    .typography
                                    .headlineSmall,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.White
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    5.dp
                                )
                        )

                        /*
                         * --------------------------------------------------
                         * DESCRIPTION
                         * --------------------------------------------------
                         */

                        Text(

                            text =
                                "Capture your thoughts, feelings, and reflections as you move through your days.",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                Color.White.copy(
                                    alpha = 0.88f
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    14.dp
                                )
                        )

                        /*
                         * --------------------------------------------------
                         * ENTRY COUNT
                         * --------------------------------------------------
                         */

                        Card(

                            shape =
                                RoundedCornerShape(
                                    16.dp
                                ),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Color.White.copy(
                                            alpha = 0.14f
                                        )
                                )
                        ) {

                            Text(

                                text =
                                    "${journals.size} journal entries recorded",

                                modifier =
                                    Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 8.dp
                                    ),

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelMedium,

                                fontWeight =
                                    FontWeight.SemiBold,

                                color =
                                    Color.White.copy(
                                        alpha = 0.94f
                                    )
                            )
                        }
                    }
                }
            }

            /*
             * ==================================================
             * EMPTY STATE
             * ==================================================
             */

            if (journals.isEmpty()) {

                item {

                    Box(

                        modifier =
                            Modifier.padding(
                                horizontal = 18.dp
                            )
                    ) {

                        Card(

                            modifier =
                                Modifier.fillMaxWidth(),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        MaterialTheme
                                            .colorScheme
                                            .surfaceContainer
                                )
                        ) {

                            Column(

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            24.dp
                                        ),

                                horizontalAlignment =
                                    androidx.compose.ui.Alignment.CenterHorizontally
                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Book,

                                    contentDescription =
                                        null,

                                    tint =
                                        softPurple,

                                    modifier =
                                        Modifier.size(
                                            48.dp
                                        )
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            12.dp
                                        )
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

            } else {

                /*
                 * ==================================================
                 * JOURNAL ENTRIES
                 * ==================================================
                 */

                items(

                    journals.reversed(),

                    key = {
                        it.id
                    }

                ) { journal ->

                    Box(

                        modifier =
                            Modifier.padding(
                                horizontal = 18.dp
                            )
                    ) {

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
                }
            }

            item {

                Spacer(
                    modifier =
                        Modifier.height(
                            20.dp
                        )
                )
            }
        }
    }

    /*
     * ==========================================================
     * DELETE CONFIRMATION
     * ==========================================================
     */

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
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
                ) {

                    Text(

                        text =
                            "Delete",

                        color =
                            MaterialTheme
                                .colorScheme
                                .onError
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

                        text =
                            "Cancel",

                        color =
                            textDark
                    )
                }
            }
        )
    }
}