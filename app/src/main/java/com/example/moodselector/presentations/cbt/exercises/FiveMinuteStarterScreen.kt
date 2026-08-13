package com.example.moodselector.presentations.cbt.exercises

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val Lavender = Color(0xFF6C63FF)
private val SoftLavender = Color(0xFFEDEBFF)
private val PaleLavender = Color(0xFFF7F5FF)

private val SoftRose = Color(0xFFFFEEF4)
private val Rose = Color(0xFFE88BA5)

private val SoftTeal = Color(0xFFE8F7F5)
private val Teal = Color(0xFF4BA89C)

private val TextPrimary = Color(0xFF292638)
private val TextSecondary = Color(0xFF777282)

private val SurfaceWhite = Color.White
private val Background = Color(0xFFFAF9FD)

@Composable
fun FiveMinuteStarterScreen(
    onBackClick: () -> Unit,

    /*
     * The exercise screen passes the user's selected
     * task and first step to the dedicated completion
     * screen.
     */
    onExerciseCompleted: (
        task: String,
        firstStep: String
    ) -> Unit
) {

    var task by remember {
        mutableStateOf("")
    }

    var firstStep by remember {
        mutableStateOf("")
    }

    val totalSeconds = 5 * 60

    var secondsRemaining by remember {
        mutableStateOf(totalSeconds)
    }

    var timerStarted by remember {
        mutableStateOf(false)
    }

    var timerRunning by remember {
        mutableStateOf(false)
    }

    var timerFinished by remember {
        mutableStateOf(false)
    }

    var stoppedEarly by remember {
        mutableStateOf(false)
    }

    /*
     * --------------------------------------------------
     * TIMER
     * --------------------------------------------------
     */

    LaunchedEffect(timerRunning) {

        while (
            timerRunning &&
            secondsRemaining > 0
        ) {

            delay(1000L)

            if (timerRunning) {

                secondsRemaining -= 1

                if (secondsRemaining <= 0) {

                    timerRunning = false
                    timerFinished = true
                }
            }
        }
    }

    /*
     * --------------------------------------------------
     * TIMER ACTIONS
     * --------------------------------------------------
     */

    fun startTimer() {

        timerStarted = true
        timerRunning = true
        stoppedEarly = false
    }

    fun pauseTimer() {

        timerRunning = false
    }

    fun resumeTimer() {

        if (
            secondsRemaining > 0 &&
            !timerFinished
        ) {

            timerRunning = true
        }
    }

    fun stopTimer() {

        timerRunning = false
        stoppedEarly = true
    }

    fun resetTimer() {

        timerRunning = false
        timerStarted = false
        timerFinished = false
        stoppedEarly = false
        secondsRemaining = totalSeconds
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
                        text = "Five-Minute Starter",
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Behavioral Activation",
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
                        vertical = 10.dp
                    )
            ) {

                /*
                 * --------------------------------------------------
                 * INTRODUCTION
                 * --------------------------------------------------
                 */

                ExerciseIntroCard(
                    icon = Icons.Default.Psychology,
                    title = "You don't have to finish everything",
                    description =
                        "The goal is simply to get started. Commit to five minutes of focused effort and see what happens."
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                /*
                 * --------------------------------------------------
                 * STEP 1
                 * --------------------------------------------------
                 */

                SectionTitle(
                    number = "1",
                    title = "Choose a task you've been avoiding"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "Choose something you've been putting off. It doesn't have to be a big task.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                OutlinedTextField(
                    value = task,
                    onValueChange = {
                        task = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("What have you been avoiding?")
                    },
                    placeholder = {
                        Text("e.g. Clean my desk")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    minLines = 2,
                    maxLines = 4,
                    enabled = !timerStarted
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                /*
                 * --------------------------------------------------
                 * STEP 2
                 * --------------------------------------------------
                 */

                SectionTitle(
                    number = "2",
                    title = "Find the smallest first step"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "What is the smallest action you can take to get started?",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                OutlinedTextField(
                    value = firstStep,
                    onValueChange = {
                        firstStep = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("My first small step")
                    },
                    placeholder = {
                        Text(
                            "e.g. Put away the papers on my desk"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    minLines = 2,
                    maxLines = 4,
                    enabled = !timerStarted
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                /*
                 * --------------------------------------------------
                 * STEP 3
                 * --------------------------------------------------
                 */

                SectionTitle(
                    number = "3",
                    title = "Give yourself five minutes"
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                TimerCard(
                    secondsRemaining = secondsRemaining,
                    timerStarted = timerStarted,
                    timerRunning = timerRunning,
                    timerFinished = timerFinished,
                    stoppedEarly = stoppedEarly,
                    canStart =
                        task.isNotBlank() &&
                                firstStep.isNotBlank(),
                    onStart = ::startTimer,
                    onPause = ::pauseTimer,
                    onResume = ::resumeTimer,
                    onStop = ::stopTimer,
                    onReset = ::resetTimer
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                /*
                 * --------------------------------------------------
                 * CONTINUE TO COMPLETION / REFLECTION
                 * --------------------------------------------------
                 */

                val canContinue =
                    timerFinished || stoppedEarly

                Button(
                    onClick = {

                        if (canContinue) {

                            onExerciseCompleted(
                                task.trim(),
                                firstStep.trim()
                            )
                        }
                    },
                    enabled = canContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Lavender,
                        contentColor = Color.White,
                        disabledContainerColor = SoftLavender,
                        disabledContentColor = TextSecondary
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
                        text = "Continue to Reflection",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text =
                        if (timerFinished) {
                            "Five minutes are complete. Take a moment to reflect on what happened."
                        } else if (stoppedEarly) {
                            "You stopped early. That's okay — you can still reflect on the experience."
                        } else {
                            "Complete the five-minute session before continuing."
                        },
                    modifier = Modifier.fillMaxWidth(),
                    color = TextSecondary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}

/*
 * ==========================================================
 * SECTION TITLE
 * ==========================================================
 */

@Composable
private fun SectionTitle(
    number: String,
    title: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(SoftLavender),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = number,
                color = Lavender,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Text(
            text = title,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
    }
}

/*
 * ==========================================================
 * TIMER CARD
 * ==========================================================
 */

@Composable
private fun TimerCard(
    secondsRemaining: Int,
    timerStarted: Boolean,
    timerRunning: Boolean,
    timerFinished: Boolean,
    stoppedEarly: Boolean,
    canStart: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit
) {

    val minutes =
        secondsRemaining / 60

    val seconds =
        secondsRemaining % 60

    val timeText =
        String.format(
            "%d:%02d",
            minutes,
            seconds
        )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                if (
                    timerFinished ||
                    stoppedEarly
                ) {
                    SoftTeal
                } else {
                    PaleLavender
                }
        ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            if (timerFinished) {

                Icon(
                    imageVector =
                        Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Teal,
                    modifier = Modifier.size(34.dp)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Five minutes are up",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text =
                        "You got started. That's what this exercise is about.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )

            } else if (stoppedEarly) {

                Icon(
                    imageVector =
                        Icons.Default.Stop,
                    contentDescription = null,
                    tint = Teal,
                    modifier = Modifier.size(34.dp)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "You stopped for now",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text =
                        "Starting was still progress. You can reflect on what you noticed.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )

            } else {

                Text(
                    text = "Time remaining",
                    color = TextSecondary,
                    fontSize = 13.sp
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = timeText,
                    color = Lavender,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            if (!timerStarted) {

                Button(
                    onClick = onStart,
                    enabled = canStart,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Lavender,
                        disabledContainerColor = SoftLavender,
                        disabledContentColor = TextSecondary
                    )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.PlayArrow,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(7.dp)
                    )

                    Text(
                        text = "Start Five Minutes",
                        fontWeight = FontWeight.SemiBold
                    )
                }

            } else if (
                !timerFinished &&
                !stoppedEarly
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    Button(
                        onClick = {

                            if (timerRunning) {
                                onPause()
                            } else {
                                onResume()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftLavender,
                            contentColor = Lavender
                        )
                    ) {

                        Icon(
                            imageVector =
                                if (timerRunning) {
                                    Icons.Default.Pause
                                } else {
                                    Icons.Default.PlayArrow
                                },
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text =
                                if (timerRunning) {
                                    "Pause"
                                } else {
                                    "Resume"
                                }
                        )
                    }

                    Button(
                        onClick = onStop,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftRose,
                            contentColor = Rose
                        )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Stop,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text = "Stop"
                        )
                    }
                }

            } else {

                Button(
                    onClick = onReset,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftLavender,
                        contentColor = Lavender
                    )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Refresh,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(7.dp)
                    )

                    Text(
                        text = "Start Again"
                    )
                }
            }
        }
    }
}

/*
 * ==========================================================
 * REFLECTION PROMPT CARD
 * ==========================================================
 */

@Composable
private fun ReflectionPromptCard(
    icon: ImageVector,
    title: String,
    text: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceWhite
        ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(SoftLavender),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Lavender,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = title,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = text,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

/*
 * ==========================================================
 * INTRO CARD
 * ==========================================================
 */

@Composable
private fun ExerciseIntroCard(
    icon: ImageVector,
    title: String,
    description: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = PaleLavender
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top
        ) {

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(SoftLavender),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Lavender,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column {

                Text(
                    text = title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = description,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}