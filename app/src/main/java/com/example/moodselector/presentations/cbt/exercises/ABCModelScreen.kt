package com.example.moodselector.presentations.cbt.exercises

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.R

private val BackgroundTop = Color(0xFFEDEBFF)
private val BackgroundMiddle = Color(0xFFF6EAF7)
private val BackgroundBottom = Color(0xFFE7F6F3)

private val DeepPurple = Color(0xFF5E4275)
private val Purple = Color(0xFF765A86)
private val SoftPurple = Color(0xFFE7D9F0)

private val TextPrimary = Color(0xFF292638)
private val TextSecondary = Color(0xFF777282)

private val SurfaceWhite = Color.White.copy(alpha = 0.80f)

@Composable
fun ABCModelScreen(
    onBackClick: () -> Unit,
    onComplete: () -> Unit,
    viewModel: ABCModelViewModel = hiltViewModel()
) {

    /*
     * --------------------------------------------------
     * PAGE STATE
     * --------------------------------------------------
     *
     * 0 = Explanation
     * 1 = A
     * 2 = B
     * 3 = C
     */

    var currentPage by remember {
        mutableIntStateOf(0)
    }

    /*
     * --------------------------------------------------
     * VIEWMODEL STATE
     * --------------------------------------------------
     */

    val uiState by viewModel.uiState.collectAsState()

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            BackgroundTop,
            BackgroundMiddle,
            BackgroundBottom
        )
    )

    /*
     * --------------------------------------------------
     * RESPONSE VALIDATION
     * --------------------------------------------------
     *
     * Each step must contain a response before the
     * user can move forward.
     */

    val currentResponseIsValid =
        when (currentPage) {
            1 -> uiState.activatingEvent.isNotBlank()
            2 -> uiState.beliefs.isNotBlank()
            3 -> uiState.consequences.isNotBlank()
            else -> true
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
                .navigationBarsPadding()
        ) {

            /*
             * ==================================================
             * TOP BAR
             * ==================================================
             */

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription =
                            "Back",

                        tint =
                            TextPrimary
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text =
                        when (currentPage) {
                            0 -> "About"
                            1 -> "A / 3"
                            2 -> "B / 3"
                            else -> "C / 3"
                        },

                    color =
                        TextSecondary,

                    fontSize =
                        13.sp,

                    fontWeight =
                        FontWeight.Medium
                )
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            /*
             * ==================================================
             * TITLE
             * ==================================================
             */

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    text =
                        "ABC Model",

                    color =
                        TextPrimary,

                    fontSize =
                        27.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(7.dp)
                )

                Text(
                    text =
                        "Understand what happens between an event and your response.",

                    color =
                        TextSecondary,

                    fontSize =
                        14.sp
                )
            }

            Spacer(
                modifier =
                    Modifier.height(26.dp)
            )

            /*
             * ==================================================
             * PROGRESS
             * ==================================================
             */

            if (currentPage > 0) {

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(7.dp)
                ) {

                    repeat(3) { index ->

                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(5.dp)
                                    .clip(
                                        RoundedCornerShape(50)
                                    )
                                    .background(
                                        if (index < currentPage) {
                                            Purple
                                        } else {
                                            SoftPurple
                                        }
                                    )
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(28.dp)
                )
            }

            /*
             * ==================================================
             * CONTENT
             * ==================================================
             *
             * weight(1f) belongs HERE, on AnimatedContent,
             * because AnimatedContent is a direct child of
             * the Column.
             */

            AnimatedContent(
                targetState =
                    currentPage,

                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),

                label =
                    "abc_model_page"
            ) { page ->

                when (page) {

                    /*
                     * ==================================================
                     * EXPLANATION PAGE
                     * ==================================================
                     */

                    0 -> {

                        Column(
                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text =
                                    "How the ABC Model works",

                                color =
                                    DeepPurple,

                                fontSize =
                                    22.sp,

                                fontWeight =
                                    FontWeight.SemiBold
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(18.dp)
                            )

                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .clip(
                                            RoundedCornerShape(24.dp)
                                        )
                                        .background(
                                            SurfaceWhite
                                        )
                            ) {

                                Image(
                                    painter =
                                        painterResource(
                                            id =
                                                R.drawable.abc_model_explanation
                                        ),

                                    contentDescription =
                                        "Diagram explaining the ABC Model",

                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(240.dp),

                                    contentScale =
                                        ContentScale.Fit
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(22.dp)
                            )

                            Text(
                                text =
                                    "The ABC Model helps you notice how " +
                                            "your thoughts and beliefs can influence " +
                                            "your emotional and behavioural responses.",

                                color =
                                    TextSecondary,

                                fontSize =
                                    15.sp,

                                lineHeight =
                                    23.sp
                            )
                        }
                    }

                    /*
                     * ==================================================
                     * A — ACTIVATING EVENT
                     * ==================================================
                     */

                    1 -> {

                        ABCResponsePage(
                            letter = "A",

                            title =
                                "What happened?",

                            instruction =
                                "Describe the situation or event as objectively as possible.",

                            hint =
                                "For example: My manager gave me critical feedback about my work.",

                            value =
                                uiState.activatingEvent,

                            onValueChange =
                                viewModel::updateActivatingEvent
                        )
                    }

                    /*
                     * ==================================================
                     * B — BELIEFS
                     * ==================================================
                     */

                    2 -> {

                        ABCResponsePage(
                            letter = "B",

                            title =
                                "What did you think?",

                            instruction =
                                "Describe the thoughts or beliefs you had about what happened.",

                            hint =
                                "For example: I'm not good enough at my job.",

                            value =
                                uiState.beliefs,

                            onValueChange =
                                viewModel::updateBeliefs
                        )
                    }

                    /*
                     * ==================================================
                     * C — CONSEQUENCES
                     * ==================================================
                     */

                    else -> {

                        ABCResponsePage(
                            letter = "C",

                            title =
                                "How did you feel and respond?",

                            instruction =
                                "Describe the emotions you experienced and what you did in response.",

                            hint =
                                "For example: I felt anxious and embarrassed, so I avoided talking to my manager.",

                            value =
                                uiState.consequences,

                            onValueChange =
                                viewModel::updateConsequences
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            /*
             * ==================================================
             * NEXT / COMPLETE BUTTON
             * ==================================================
             */

            Button(
                onClick = {

                    when {

                        currentPage < 3 -> {

                            if (currentResponseIsValid) {
                                currentPage++
                            }
                        }

                        else -> {

                            /*
                             * Mark the exercise as completed first.
                             * The ViewModel then persists the ABC
                             * response and only calls onComplete()
                             * after the Room save succeeds.
                             */

                            if (currentResponseIsValid) {

                                viewModel.markCompleted()

                                viewModel.saveCompletion(
                                    onSaved = onComplete
                                )
                            }
                        }
                    }
                },

                enabled =
                    currentResponseIsValid &&
                            !uiState.isSaving &&
                            !uiState.isSaved,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                shape =
                    RoundedCornerShape(18.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            DeepPurple,

                        disabledContainerColor =
                            DeepPurple.copy(
                                alpha = 0.35f
                            )
                    )
            ) {

                if (currentPage == 3) {

                    Icon(
                        imageVector =
                            Icons.Default.Check,

                        contentDescription =
                            null
                    )

                    Spacer(
                        modifier =
                            Modifier.size(8.dp)
                    )
                }

                Text(
                    text =
                        when (currentPage) {
                            0 -> "Start Exercise"
                            1 -> "Next"
                            2 -> "Next"
                            else -> {
                                if (uiState.isSaving) {
                                    "Saving..."
                                } else {
                                    "Complete ABC Model"
                                }
                            }
                        },

                    fontWeight =
                        FontWeight.SemiBold
                )
            }

            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )
        }
    }
}


/*
 * ==========================================================
 * ABC RESPONSE PAGE
 * ==========================================================
 *
 * There is intentionally NO weight() here.
 * The parent AnimatedContent already occupies the
 * available vertical space.
 */

@Composable
private fun ABCResponsePage(
    letter: String,
    title: String,
    instruction: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Column(
        modifier =
            Modifier.fillMaxWidth(),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        /*
         * ABC LETTER
         */

        Box(
            modifier =
                Modifier
                    .size(92.dp)
                    .clip(
                        RoundedCornerShape(28.dp)
                    )
                    .background(
                        SoftPurple
                    ),

            contentAlignment =
                Alignment.Center
        ) {

            Text(
                text =
                    letter,

                color =
                    DeepPurple,

                fontSize =
                    48.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        Text(
            text =
                title,

            color =
                DeepPurple,

            fontSize =
                23.sp,

            fontWeight =
                FontWeight.SemiBold
        )

        Spacer(
            modifier =
                Modifier.height(10.dp)
        )

        Text(
            text =
                instruction,

            color =
                TextSecondary,

            fontSize =
                15.sp,

            lineHeight =
                23.sp
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        /*
         * RESPONSE FIELD
         */

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(
                        RoundedCornerShape(22.dp)
                    )
                    .background(
                        SurfaceWhite
                    )
                    .padding(18.dp)
        ) {

            BasicTextField(
                value =
                    value,

                onValueChange =
                    onValueChange,

                modifier =
                    Modifier.fillMaxSize(),

                textStyle =
                    TextStyle(
                        color =
                            TextPrimary,

                        fontSize =
                            15.sp,

                        lineHeight =
                            23.sp
                    ),

                decorationBox = { innerTextField ->

                    if (value.isEmpty()) {

                        Text(
                            text =
                                hint,

                            color =
                                TextSecondary.copy(
                                    alpha = 0.65f
                                ),

                            fontSize =
                                14.sp,

                            lineHeight =
                                22.sp
                        )
                    }

                    innerTextField()
                }
            )
        }
    }
}