package com.example.moodselector.presentations.cbt.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

private val Lavender = Color(0xFF6C63FF)
private val SoftLavender = Color(0xFFEDEBFF)
private val PaleLavender = Color(0xFFF7F5FF)

private val TextPrimary = Color(0xFF292638)
private val TextSecondary = Color(0xFF777282)

private val Background = Color(0xFFFAF9FD)
private val SurfaceWhite = Color.White

@Composable
fun FiveMinuteStarterCompletionScreen(
    task: String,
    firstStep: String,
    onBackClick: () -> Unit,
    onCompleted: () -> Unit,
    viewModel: FiveMinuteStarterViewModel =
        hiltViewModel()
) {

    var outcome by remember {
        mutableStateOf("")
    }

    var reflection by remember {
        mutableStateOf("")
    }

    var completed by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = Background
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .navigationBarsPadding()
        ) {

            /*
             * --------------------------------------------------
             * TOP BAR
             * --------------------------------------------------
             */

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 4.dp)
                ) {

                    Text(
                        text = "Exercise Complete",
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Five-Minute Starter",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }


            /*
             * --------------------------------------------------
             * CONTENT
             * --------------------------------------------------
             */

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    )
            ) {

                /*
                 * --------------------------------------------------
                 * COMPLETION HEADER
                 * --------------------------------------------------
                 */

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PaleLavender
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Lavender,
                            modifier = Modifier.size(42.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "You got started",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                "Take a moment to record what happened. Your experience matters more than how much you accomplished.",
                            color = TextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                /*
                 * --------------------------------------------------
                 * TASK
                 * --------------------------------------------------
                 */

                CompletionInfoCard(
                    title = "Task",
                    value = task
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                CompletionInfoCard(
                    title = "First small step",
                    value = firstStep
                )


                Spacer(
                    modifier = Modifier.height(24.dp)
                )


                /*
                 * --------------------------------------------------
                 * OUTCOME
                 * --------------------------------------------------
                 */

                Text(
                    text = "What happened?",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "Describe what you managed to do during the five minutes. It is completely okay if you stopped early.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                OutlinedTextField(
                    value = outcome,
                    onValueChange = {
                        outcome = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("What did you accomplish?")
                    },
                    placeholder = {
                        Text(
                            "e.g. I cleared part of my desk..."
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector =
                                Icons.Default.Edit,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    minLines = 4,
                    maxLines = 7
                )


                Spacer(
                    modifier = Modifier.height(22.dp)
                )


                /*
                 * --------------------------------------------------
                 * REFLECTION
                 * --------------------------------------------------
                 */

                Text(
                    text = "Reflection",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "What did you notice about your motivation, thoughts, or feelings after getting started?",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                OutlinedTextField(
                    value = reflection,
                    onValueChange = {
                        reflection = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Your reflection")
                    },
                    placeholder = {
                        Text(
                            "Write anything you'd like to remember..."
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector =
                                Icons.Default.Psychology,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    minLines = 5,
                    maxLines = 8
                )


                Spacer(
                    modifier = Modifier.height(22.dp)
                )


                /*
                 * --------------------------------------------------
                 * COMPLETION CHECKBOX
                 * --------------------------------------------------
                 */

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (completed) {
                                SoftLavender
                            } else {
                                SurfaceWhite
                            }
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = completed,
                            onCheckedChange = {
                                completed = it
                            }
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text =
                                "I've completed this exercise",
                            modifier =
                                Modifier.weight(1f),
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (completed) {

                            Icon(
                                imageVector =
                                    Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Lavender,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }


                Spacer(
                    modifier = Modifier.height(22.dp)
                )


                /*
                 * --------------------------------------------------
                 * SAVE
                 * --------------------------------------------------
                 */

                Button(
                    onClick = {

                        if (!completed) {
                            return@Button
                        }

                        viewModel.completeExercise(

                            task = task,

                            firstStep = firstStep,

                            outcome = outcome,

                            reflection = reflection,

                            onCompleted = onCompleted
                        )
                    },
                    enabled = completed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Lavender,
                        contentColor = Color.White,
                        disabledContainerColor =
                            SoftLavender,
                        disabledContentColor =
                            TextSecondary
                    )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Save Completion",
                        fontWeight = FontWeight.SemiBold
                    )
                }


                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}


/*
 * ==========================================================
 * COMPLETION INFO CARD
 * ==========================================================
 */

@Composable
private fun CompletionInfoCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceWhite
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = Lavender,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = value.ifBlank {
                    "Not provided"
                },
                color = TextPrimary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}