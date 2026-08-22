package com.example.moodselector.presentations.cbt.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.R

private val Lavender = Color(0xFF6C63FF)
private val SoftLavender = Color(0xFFEDEBFF)
private val PaleLavender = Color(0xFFF7F5FF)

private val SoftRose = Color(0xFFFFEEF4)
private val Rose = Color(0xFFE88BA5)

private val TextPrimary = Color(0xFF292638)
private val TextSecondary = Color(0xFF777282)

private val Background = Color(0xFFFAF9FD)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfCompassionReflectionScreen(
    onBackClick: () -> Unit,
    onCompleted: () -> Unit,
    viewModel: SelfCompassionReflectionViewModel =
        hiltViewModel()
) {

    /*
     * ======================================================
     * PAGE STATE
     * ======================================================
     */

    var showExercise by rememberSaveable {
        mutableStateOf(false)
    }


    /*
     * ======================================================
     * EXERCISE STATE
     * ======================================================
     */

    var situation by rememberSaveable {
        mutableStateOf("")
    }

    var friendResponse by rememberSaveable {
        mutableStateOf("")
    }

    var selfCompassionResponse by rememberSaveable {
        mutableStateOf("")
    }

    var isCompleted by rememberSaveable {
        mutableStateOf(false)
    }


    /*
     * ======================================================
     * COMPLETION VALIDATION
     * ======================================================
     */

    val canComplete =
        situation.isNotBlank() &&
                friendResponse.isNotBlank() &&
                selfCompassionResponse.isNotBlank() &&
                isCompleted


    Scaffold(

        containerColor =
            MaterialTheme.colorScheme.background,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text =
                                "Self-Compassion",

                            color =
                                MaterialTheme.colorScheme.onBackground,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize =
                                19.sp
                        )

                        Text(
                            text =
                                if (showExercise)
                                    "Reflection exercise"
                                else
                                    "A kinder way to respond to yourself",

                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant,

                            fontSize =
                                12.sp
                        )
                    }
                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            if (showExercise) {

                                showExercise = false

                            } else {

                                onBackClick()
                            }
                        }
                    ) {

                        Icon(

                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,

                            contentDescription =
                                "Back",

                            tint =
                                MaterialTheme.colorScheme.onBackground
                        )
                    }
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(

                        containerColor =
                            MaterialTheme.colorScheme.background
                    )
            )
        }

    ) { paddingValues ->

        if (!showExercise) {

            /*
             * ==================================================
             * INTRODUCTION PAGE
             * ==================================================
             */

            SelfCompassionIntroduction(

                paddingValues =
                    paddingValues,

                onNextClick = {

                    showExercise = true
                }
            )

        } else {

            /*
             * ==================================================
             * EXERCISE PAGE
             * ==================================================
             */

            SelfCompassionExercise(

                paddingValues =
                    paddingValues,

                situation =
                    situation,

                onSituationChange = {

                    situation = it
                },

                friendResponse =
                    friendResponse,

                onFriendResponseChange = {

                    friendResponse = it
                },

                selfCompassionResponse =
                    selfCompassionResponse,

                onSelfCompassionResponseChange = {

                    selfCompassionResponse = it
                },

                isCompleted =
                    isCompleted,

                onCompletedChange = {

                    isCompleted = it
                },

                canComplete =
                    canComplete,

                onComplete = {

                    viewModel.saveCompletion(

                        situation =
                            situation.trim(),

                        friendResponse =
                            friendResponse.trim(),

                        selfCompassionResponse =
                            selfCompassionResponse.trim(),

                        onSaved = {

                            onCompleted()
                        }
                    )
                }
            )
        }
    }
}


/*
 * ======================================================
 * INTRODUCTION PAGE
 * ======================================================
 */

@Composable
private fun SelfCompassionIntroduction(
    paddingValues: PaddingValues,
    onNextClick: () -> Unit
) {

    Box(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    paddingValues
                )
    ) {

        /*
         * ==================================================
         * BACKGROUND ILLUSTRATION
         * ==================================================
         */

        Image(

            painter =
                painterResource(
                    id =
                        R.drawable.self_compassion_background
                ),

            contentDescription =
                null,

            modifier =
                Modifier.fillMaxSize(),

            contentScale =
                ContentScale.Crop,

            alpha =
                0.18f
        )


        /*
         * ==================================================
         * SOFT BACKGROUND OVERLAY
         * ==================================================
         */

        Box(

            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.background.copy(
                            alpha =
                                0.82f
                        )
                    )
        )


        /*
         * ==================================================
         * INTRODUCTION CONTENT
         * ==================================================
         */

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .navigationBarsPadding()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    18.dp
                )
        ) {

            /*
             * ------------------------------------------
             * EXPLANATION IMAGE
             * ------------------------------------------
             */

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        24.dp
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surface.copy(
                                alpha =
                                    0.94f
                            )
                    )
            ) {

                Image(

                    painter =
                        painterResource(
                            id =
                                R.drawable.self_compassion
                        ),

                    contentDescription =
                        "Self-compassion explanation",

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(
                                260.dp
                            )
                            .clip(
                                RoundedCornerShape(
                                    24.dp
                                )
                            ),

                    contentScale =
                        ContentScale.Crop
                )
            }


            /*
             * ------------------------------------------
             * INTRODUCTION CARD
             * ------------------------------------------
             */

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        24.dp
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            if (MaterialTheme.colorScheme.background ==
                                Color(0xFF201A36)
                            ) {
                                MaterialTheme.colorScheme.surfaceVariant
                            } else {
                                PaleLavender.copy(
                                    alpha =
                                        0.95f
                                )
                            }
                    )
            ) {

                Row(

                    modifier =
                        Modifier.padding(
                            18.dp
                        ),

                    verticalAlignment =
                        Alignment.Top
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Favorite,

                        contentDescription =
                            null,

                        tint =
                            Rose,

                        modifier =
                            Modifier.size(
                                27.dp
                            )
                    )

                    Spacer(
                        modifier =
                            Modifier.size(
                                12.dp
                            )
                    )

                    Column {

                        Text(

                            text =
                                "Practice self-kindness",

                            color =
                                MaterialTheme.colorScheme.onSurface,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize =
                                17.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    6.dp
                                )
                        )

                        Text(

                            text =
                                "When something is difficult, we often speak " +
                                        "to ourselves more harshly than we would " +
                                        "speak to someone we care about.",

                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant,

                            fontSize =
                                13.sp,

                            lineHeight =
                                20.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    8.dp
                                )
                        )

                        Text(

                            text =
                                "This exercise helps you pause, imagine how " +
                                        "you would respond to a friend, and then " +
                                        "offer yourself that same kindness.",

                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant,

                            fontSize =
                                13.sp,

                            lineHeight =
                                20.sp
                        )
                    }
                }
            }


            /*
             * ------------------------------------------
             * WHAT YOU WILL DO
             * ------------------------------------------
             */

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        20.dp
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            if (MaterialTheme.colorScheme.background ==
                                Color(0xFF201A36)
                            ) {
                                MaterialTheme.colorScheme.surfaceVariant
                            } else {
                                SoftRose.copy(
                                    alpha =
                                        0.95f
                                )
                            }
                    )
            ) {

                Column(

                    modifier =
                        Modifier.padding(
                            18.dp
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            10.dp
                        )
                ) {

                    Text(

                        text =
                            "In this reflection, you will:",

                        color =
                            MaterialTheme.colorScheme.onSurface,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            15.sp
                    )

                    Text(

                        text =
                            "• Describe a situation you're struggling with\n" +
                                    "• Imagine what you would say to a friend\n" +
                                    "• Offer yourself that same kindness and understanding",

                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant,

                        fontSize =
                            13.sp,

                        lineHeight =
                            21.sp
                    )
                }
            }


            /*
             * ------------------------------------------
             * NEXT BUTTON
             * ------------------------------------------
             */

            Button(

                onClick =
                    onNextClick,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(
                            54.dp
                        ),

                shape =
                    RoundedCornerShape(
                        18.dp
                    ),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.primary
                    )
            ) {

                Text(

                    text =
                        "Next",

                    fontWeight =
                        FontWeight.Bold,

                    fontSize =
                        15.sp
                )

                Spacer(
                    modifier =
                        Modifier.size(
                            8.dp
                        )
                )

                Icon(

                    imageVector =
                        Icons.Default.ArrowForward,

                    contentDescription =
                        null,

                    modifier =
                        Modifier.size(
                            20.dp
                        )
                )
            }


            Spacer(
                modifier =
                    Modifier.height(
                        8.dp
                    )
            )
        }
    }
}


/*
 * ======================================================
 * EXERCISE PAGE
 * ======================================================
 */

@Composable
private fun SelfCompassionExercise(
    paddingValues: PaddingValues,

    situation: String,
    onSituationChange: (String) -> Unit,

    friendResponse: String,
    onFriendResponseChange: (String) -> Unit,

    selfCompassionResponse: String,
    onSelfCompassionResponseChange: (String) -> Unit,

    isCompleted: Boolean,
    onCompletedChange: (Boolean) -> Unit,

    canComplete: Boolean,
    onComplete: () -> Unit
) {

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    paddingValues
                )
                .verticalScroll(
                    rememberScrollState()
                )
                .navigationBarsPadding()
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),

        verticalArrangement =
            Arrangement.spacedBy(
                18.dp
            )
    ) {

        /*
         * ------------------------------------------
         * STEP 1
         * ------------------------------------------
         */

        ReflectionSection(

            number =
                "1",

            title =
                "What happened?",

            description =
                "Briefly describe the situation you're struggling with."
        )

        OutlinedTextField(

            value =
                situation,

            onValueChange =
                onSituationChange,

            modifier =
                Modifier.fillMaxWidth(),

            placeholder = {

                Text(

                    text =
                        "Describe the situation...",

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            },

            minLines =
                4,

            shape =
                RoundedCornerShape(
                    18.dp
                )
        )


        /*
         * ------------------------------------------
         * STEP 2
         * ------------------------------------------
         */

        ReflectionSection(

            number =
                "2",

            title =
                "What would you say to a friend?",

            description =
                "Imagine someone you care about experiencing " +
                        "the same situation. What would you say " +
                        "to them?"
        )

        OutlinedTextField(

            value =
                friendResponse,

            onValueChange =
                onFriendResponseChange,

            modifier =
                Modifier.fillMaxWidth(),

            placeholder = {

                Text(

                    text =
                        "I would tell my friend...",

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            },

            minLines =
                4,

            shape =
                RoundedCornerShape(
                    18.dp
                )
        )


        /*
         * ------------------------------------------
         * STEP 3
         * ------------------------------------------
         */

        ReflectionSection(

            number =
                "3",

            title =
                "What can you say to yourself?",

            description =
                "Now offer yourself some of that same kindness, " +
                        "understanding, and encouragement."
        )

        OutlinedTextField(

            value =
                selfCompassionResponse,

            onValueChange =
                onSelfCompassionResponseChange,

            modifier =
                Modifier.fillMaxWidth(),

            placeholder = {

                Text(

                    text =
                        "I can remind myself...",

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            },

            minLines =
                4,

            shape =
                RoundedCornerShape(
                    18.dp
                )
        )


        /*
         * ------------------------------------------
         * COMPLETION CHECKBOX
         * ------------------------------------------
         */

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(
                    20.dp
                ),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        if (MaterialTheme.colorScheme.background ==
                            Color(0xFF201A36)
                        ) {
                            MaterialTheme.colorScheme.surfaceVariant
                        } else {
                            SoftRose
                        }
                )
        ) {

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            14.dp
                        ),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Checkbox(

                    checked =
                        isCompleted,

                    onCheckedChange =
                        onCompletedChange
                )

                Spacer(
                    modifier =
                        Modifier.size(
                            4.dp
                        )
                )

                Column {

                    Text(

                        text =
                            "I've completed this reflection",

                        color =
                            MaterialTheme.colorScheme.onSurface,

                        fontWeight =
                            FontWeight.SemiBold,

                        fontSize =
                            14.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                2.dp
                            )
                    )

                    Text(

                        text =
                            "Your reflection will be saved to your CBT progress.",

                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant,

                        fontSize =
                            12.sp
                    )
                }
            }
        }


        /*
         * ------------------------------------------
         * COMPLETE BUTTON
         * ------------------------------------------
         */

        Button(

            onClick =
                onComplete,

            enabled =
                canComplete,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        54.dp
                    ),

            shape =
                RoundedCornerShape(
                    18.dp
                ),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        MaterialTheme.colorScheme.primary,

                    disabledContainerColor =
                        MaterialTheme.colorScheme.surfaceVariant,

                    disabledContentColor =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
        ) {

            Icon(

                imageVector =
                    Icons.Default.TaskAlt,

                contentDescription =
                    null,

                modifier =
                    Modifier.size(
                        20.dp
                    )
            )

            Spacer(
                modifier =
                    Modifier.size(
                        8.dp
                    )
            )

            Text(

                text =
                    "Complete Reflection",

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    15.sp
            )
        }


        Spacer(
            modifier =
                Modifier.height(
                    8.dp
                )
        )
    }
}


/*
 * ======================================================
 * REFLECTION SECTION
 * ======================================================
 */

@Composable
private fun ReflectionSection(
    number: String,
    title: String,
    description: String
) {

    Row(

        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.Top
    ) {

        BoxNumber(

            number =
                number
        )

        Spacer(
            modifier =
                Modifier.size(
                    12.dp
                )
        )

        Column(

            modifier =
                Modifier.weight(
                    1f
                )
        ) {

            Text(

                text =
                    title,

                color =
                    MaterialTheme.colorScheme.onBackground,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    16.sp
            )

            Spacer(
                modifier =
                    Modifier.height(
                        4.dp
                    )
            )

            Text(

                text =
                    description,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant,

                fontSize =
                    13.sp,

                lineHeight =
                    19.sp
            )
        }
    }
}


/*
 * ======================================================
 * NUMBER
 * ======================================================
 */

@Composable
private fun BoxNumber(
    number: String
) {

    Box(

        modifier =
            Modifier
                .size(
                    34.dp
                )
                .clip(
                    CircleShape
                )
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                ),

        contentAlignment =
            Alignment.Center
    ) {

        Text(

            text =
                number,

            color =
                MaterialTheme.colorScheme.primary,

            fontWeight =
                FontWeight.Bold,

            fontSize =
                14.sp
        )
    }
}