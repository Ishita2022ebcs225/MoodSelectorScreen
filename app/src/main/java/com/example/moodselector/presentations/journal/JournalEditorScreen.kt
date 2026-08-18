package com.example.moodselector.presentations.journal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    journalId: Int? = null,
    viewModel: JournalViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {

    val content by
    viewModel.content.collectAsState()

    val selectedMood by
    viewModel.selectedMood.collectAsState()

    val isLoadingJournal by
    viewModel.isLoadingJournal.collectAsState()

    val colorScheme =
        androidx.compose.material3.MaterialTheme.colorScheme

    val background =
        colorScheme.background

    val textPrimary =
        colorScheme.onBackground

    val textSecondary =
        colorScheme.onSurfaceVariant

    val primary =
        colorScheme.primary

    val onPrimary =
        colorScheme.onPrimary

    val lavender =
        colorScheme.primaryContainer

    val mutedBlue =
        colorScheme.secondaryContainer

    /*
     * ==========================================================
     * THEME-AWARE PAPER OVERLAY
     * ==========================================================
     *
     * The original lined-paper image is preserved.
     *
     * Light theme:
     * subtle white overlay.
     *
     * Dark theme:
     * darker overlay so the light image fits the dark theme.
     */

    val isDarkTheme =
        isSystemInDarkTheme()

    val paperOverlay =
        if (isDarkTheme) {

            Color.Black.copy(
                alpha = 0.48f
            )

        } else {

            Color.White.copy(
                alpha = 0.30f
            )
        }

    val moodOptions =
        listOf(
            "Happy",
            "Calm",
            "Neutral",
            "Sad",
            "Angry"
        )

    LaunchedEffect(journalId) {

        if (journalId != null) {

            viewModel.loadJournal(
                journalId
            )
        }
    }

    val isEditing =
        journalId != null

    Scaffold(
        containerColor =
            background,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            if (isEditing)
                                "Edit Journal Entry"
                            else
                                "New Journal Entry",

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            textPrimary
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick =
                            onBackClick
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription =
                                "Back",

                            tint =
                                textPrimary
                        )
                    }
                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(
                            containerColor =
                                background,

                            titleContentColor =
                                textPrimary,

                            navigationIconContentColor =
                                textPrimary
                        )
            )
        }

    ) { padding ->

        if (
            isEditing &&
            isLoadingJournal
        ) {

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            background
                        )
                        .padding(padding),

                contentAlignment =
                    Alignment.Center
            ) {

                CircularProgressIndicator(
                    color =
                        primary
                )
            }

        } else {

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            background
                        )
                        .padding(padding)
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 16.dp
                        ),

                verticalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {

                CardHeader(
                    lavender =
                        lavender,

                    mutedBlue =
                        mutedBlue,

                    textDark =
                        textPrimary
                )

                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 260.dp,
                                max = 420.dp
                            )
                            .clip(
                                RoundedCornerShape(
                                    20.dp
                                )
                            )
                ) {

                    Image(
                        painter =
                            painterResource(
                                id =
                                    R.drawable
                                        .lavender_lined_paper
                            ),

                        contentDescription =
                            null,

                        contentScale =
                            ContentScale.Crop,

                        modifier =
                            Modifier.matchParentSize()
                    )

                    /*
                     * Theme-dependent overlay.
                     */

                    Box(
                        modifier =
                            Modifier
                                .matchParentSize()
                                .background(
                                    paperOverlay
                                )
                    )

                    OutlinedTextField(
                        value =
                            content,

                        onValueChange =
                            viewModel::updateContent,

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(10.dp),

                        placeholder = {

                            Text(
                                text =
                                    "Write about your day...",

                                color =
                                    textSecondary
                            )
                        },

                        shape =
                            RoundedCornerShape(
                                20.dp
                            ),

                        colors =
                            TextFieldDefaults.colors(
                                focusedContainerColor =
                                    Color.Transparent,

                                unfocusedContainerColor =
                                    Color.Transparent,

                                focusedIndicatorColor =
                                    Color.Transparent,

                                unfocusedIndicatorColor =
                                    Color.Transparent,

                                cursorColor =
                                    primary,

                                focusedTextColor =
                                    textPrimary,

                                unfocusedTextColor =
                                    textPrimary
                            )
                    )
                }

                Text(
                    text =
                        "Mood",

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        textPrimary
                )

                FlowRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    moodOptions.forEach { mood ->

                        val selected =
                            selectedMood == mood

                        AssistChip(
                            onClick = {

                                viewModel.updateMood(
                                    mood
                                )
                            },

                            label = {
                                Text(
                                    text =
                                        mood
                                )
                            },

                            shape =
                                CircleShape,

                            colors =
                                AssistChipDefaults
                                    .assistChipColors(
                                        containerColor =
                                            if (selected)
                                                primary
                                            else
                                                colorScheme
                                                    .surfaceVariant,

                                        labelColor =
                                            if (selected)
                                                onPrimary
                                            else
                                                textPrimary
                                    )
                        )
                    }
                }

                Button(
                    onClick = {

                        viewModel.saveJournal()

                        onBackClick()
                    },

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(52.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                primary,

                            contentColor =
                                onPrimary
                        ),

                    shape =
                        RoundedCornerShape(
                            20.dp
                        )
                ) {

                    Text(
                        text =
                            if (isEditing)
                                "Save Changes"
                            else
                                "Save Entry",

                        color =
                            onPrimary,

                        fontWeight =
                            FontWeight.Bold
                    )
                }

                Spacer(
                    modifier =
                        Modifier.height(40.dp)
                )
            }
        }
    }
}

@Composable
private fun CardHeader(
    lavender: Color,
    mutedBlue: Color,
    textDark: Color
) {

    Card(
        shape =
            RoundedCornerShape(24.dp),

        colors =
            androidx.compose.material3
                .CardDefaults
                .cardColors(
                    containerColor =
                        androidx.compose.material3
                            .MaterialTheme
                            .colorScheme
                            .surface
                ),

        modifier =
            Modifier.fillMaxWidth()
    ) {

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                lavender,
                                mutedBlue
                            )
                        )
                    )
                    .padding(20.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(
                                textDark.copy(
                                    alpha = 0.10f
                                )
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        Icons.Default.AutoStories,
                        contentDescription =
                            null,

                        tint =
                            textDark
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Column {

                    Text(
                        text =
                            "Write Freely",

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            textDark
                    )

                    Text(
                        text =
                            "Capture your thoughts and emotions",

                        color =
                            textDark.copy(
                                alpha = 0.7f
                            )
                    )
                }
            }
        }
    }
}