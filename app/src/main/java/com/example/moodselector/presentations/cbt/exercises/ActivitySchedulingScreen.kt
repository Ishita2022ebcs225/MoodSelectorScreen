package com.example.moodselector.presentations.cbt.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.presentations.cbt.progress.CBTProgressViewModel

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitySchedulingScreen(
    activity: CBTActivity,
    onBackClick: () -> Unit,
    onExerciseCompleted: () -> Unit,
    viewModel: CBTProgressViewModel = hiltViewModel()
) {

    var currentStep by remember {
        mutableIntStateOf(0)
    }

    var activityName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var isPleasure by remember {
        mutableStateOf(false)
    }

    var isMastery by remember {
        mutableStateOf(false)
    }

    var whenText by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var whereText by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var completed by remember {
        mutableStateOf(false)
    }

    var reflection by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var isSaving by remember {
        mutableStateOf(false)
    }

    val totalSteps = 4

    Scaffold(

        containerColor = Background,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = activity.title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        Text(
                            text = "Behavioral Activation",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .navigationBarsPadding()
        ) {

            /*
             * --------------------------------------------------
             * Progress
             * --------------------------------------------------
             */

            LinearProgressIndicator(

                progress = {
                    (currentStep + 1).toFloat() / totalSteps
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),

                color = Lavender,

                trackColor = SoftLavender
            )

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    )
            ) {

                /*
                 * --------------------------------------------------
                 * Step Header
                 * --------------------------------------------------
                 */

                StepHeader(
                    step = currentStep + 1,
                    totalSteps = totalSteps
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                when (currentStep) {

                    /*
                     * ==================================================
                     * STEP 1
                     * ==================================================
                     */

                    0 -> {

                        ActivitySelectionStep(

                            activityName = activityName,

                            onActivityNameChange = {
                                activityName = it
                            },

                            isPleasure = isPleasure,

                            onPleasureChange = {
                                isPleasure = it
                            },

                            isMastery = isMastery,

                            onMasteryChange = {
                                isMastery = it
                            }
                        )
                    }

                    /*
                     * ==================================================
                     * STEP 2
                     * ==================================================
                     */

                    1 -> {

                        SchedulingStep(

                            whenText = whenText,

                            onWhenChange = {
                                whenText = it
                            },

                            whereText = whereText,

                            onWhereChange = {
                                whereText = it
                            }
                        )
                    }

                    /*
                     * ==================================================
                     * STEP 3
                     * ==================================================
                     */

                    2 -> {

                        CompletionStep(

                            activityName =
                                activityName.text,

                            whenText =
                                whenText.text,

                            whereText =
                                whereText.text,

                            isPleasure =
                                isPleasure,

                            isMastery =
                                isMastery,

                            completed =
                                completed,

                            onCompletedChange = {
                                completed = it
                            }
                        )
                    }

                    /*
                     * ==================================================
                     * STEP 4
                     * ==================================================
                     */

                    3 -> {

                        ReflectionStep(

                            reflection = reflection,

                            onReflectionChange = {
                                reflection = it
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                /*
                 * --------------------------------------------------
                 * Navigation Buttons
                 * --------------------------------------------------
                 */

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    if (currentStep > 0) {

                        Button(

                            onClick = {
                                currentStep--
                            },

                            modifier = Modifier.weight(1f),

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        SoftLavender,
                                    contentColor =
                                        Lavender
                                ),

                            shape =
                                RoundedCornerShape(16.dp)
                        ) {

                            Text(
                                text = "Back",
                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }
                    }

                    Button(

                        onClick = {

                            if (
                                currentStep <
                                totalSteps - 1
                            ) {

                                currentStep++

                            } else {

                                if (isSaving) {
                                    return@Button
                                }

                                isSaving = true

                                val activityType =
                                    buildList {

                                        if (isPleasure) {
                                            add("Pleasure")
                                        }

                                        if (isMastery) {
                                            add("Mastery")
                                        }

                                    }.joinToString(" + ")

                                viewModel.saveActivityCompletion(

                                    activityId =
                                        activity.id,

                                    activityTitle =
                                        activity.title,

                                    activityDescription =
                                        activity.description,

                                    activityName =
                                        activityName.text,

                                    activityType =
                                        activityType,

                                    scheduledWhen =
                                        whenText.text,

                                    scheduledWhere =
                                        whereText.text,

                                    reflection =
                                        reflection.text,

                                    onSaved = {

                                        onExerciseCompleted()
                                    }
                                )
                            }
                        },

                        modifier =
                            Modifier.weight(1f),

                        enabled =
                            !isSaving &&
                                    when (currentStep) {

                                        0 ->
                                            activityName.text
                                                .isNotBlank() &&
                                                    (
                                                            isPleasure ||
                                                                    isMastery
                                                            )

                                        1 ->
                                            whenText.text
                                                .isNotBlank() &&
                                                    whereText.text
                                                        .isNotBlank()

                                        2 ->
                                            completed

                                        else ->
                                            true
                                    },

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Lavender,
                                contentColor =
                                    Color.White,
                                disabledContainerColor =
                                    SoftLavender,
                                disabledContentColor =
                                    TextSecondary
                            ),

                        shape =
                            RoundedCornerShape(16.dp)
                    ) {

                        if (isSaving) {

                            Text(
                                text = "Saving..."
                            )

                        } else {

                            Text(
                                text =
                                    if (
                                        currentStep ==
                                        totalSteps - 1
                                    ) {
                                        "Complete"
                                    } else {
                                        "Continue"
                                    },

                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

/*
 * ==============================================================
 * STEP HEADER
 * ==============================================================
 */

@Composable
private fun StepHeader(
    step: Int,
    totalSteps: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceBetween,
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text = "Step $step of $totalSteps",
            color = Lavender,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )

        Text(
            text = when (step) {
                1 -> "Choose your activity"
                2 -> "Make your plan"
                3 -> "Complete your activity"
                else -> "Reflect"
            },
            color = TextSecondary,
            fontSize = 13.sp
        )
    }
}

/*
 * ==============================================================
 * STEP 1 — ACTIVITY SELECTION
 * ==============================================================
 */

@Composable
private fun ActivitySelectionStep(
    activityName: TextFieldValue,
    onActivityNameChange: (TextFieldValue) -> Unit,
    isPleasure: Boolean,
    onPleasureChange: (Boolean) -> Unit,
    isMastery: Boolean,
    onMasteryChange: (Boolean) -> Unit
) {

    ExerciseIntroCard(

        icon = Icons.Default.Star,

        title = "Choose one meaningful activity",

        description =
            "Think of one activity you would like to complete. Keep it realistic and achievable."
    )

    Spacer(
        modifier = Modifier.height(20.dp)
    )

    OutlinedTextField(

        value = activityName,

        onValueChange =
            onActivityNameChange,

        modifier = Modifier.fillMaxWidth(),

        label = {
            Text("What would you like to do?")
        },

        placeholder = {
            Text(
                "e.g. Go for a 20-minute walk"
            )
        },

        leadingIcon = {

            Icon(
                imageVector =
                    Icons.Default.TaskAlt,
                contentDescription = null
            )
        },

        shape =
            RoundedCornerShape(16.dp),

        singleLine = true
    )

    Spacer(
        modifier = Modifier.height(20.dp)
    )

    Text(
        text = "How would you classify this activity?",
        color = TextPrimary,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp
    )

    Spacer(
        modifier = Modifier.height(12.dp)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        ActivityTypeCard(

            title = "Pleasure",

            description =
                "Something enjoyable",

            selected =
                isPleasure,

            onClick = {
                onPleasureChange(
                    !isPleasure
                )
            },

            selectedColor =
                SoftRose,

            iconColor =
                Rose,

            modifier =
                Modifier.weight(1f)
        )

        ActivityTypeCard(

            title = "Mastery",

            description =
                "Something meaningful",

            selected =
                isMastery,

            onClick = {
                onMasteryChange(
                    !isMastery
                )
            },

            selectedColor =
                SoftTeal,

            iconColor =
                Teal,

            modifier =
                Modifier.weight(1f)
        )
    }
}

/*
 * ==============================================================
 * STEP 2 — SCHEDULING
 * ==============================================================
 */

@Composable
private fun SchedulingStep(
    whenText: TextFieldValue,
    onWhenChange: (TextFieldValue) -> Unit,
    whereText: TextFieldValue,
    onWhereChange: (TextFieldValue) -> Unit
) {

    ExerciseIntroCard(

        icon = Icons.Default.CalendarToday,

        title = "Make your plan specific",

        description =
            "Deciding when and where you will do something makes it easier to turn an intention into action."
    )

    Spacer(
        modifier = Modifier.height(20.dp)
    )

    OutlinedTextField(

        value = whenText,

        onValueChange = onWhenChange,

        modifier = Modifier.fillMaxWidth(),

        label = {
            Text("When will you do it?")
        },

        placeholder = {
            Text("e.g. Tomorrow at 6:00 PM")
        },

        leadingIcon = {

            Icon(
                imageVector =
                    Icons.Default.AccessTime,
                contentDescription = null
            )
        },

        trailingIcon = {

            Icon(
                imageVector =
                    Icons.Default.Event,
                contentDescription = null,
                tint = Lavender
            )
        },

        shape =
            RoundedCornerShape(16.dp),

        singleLine = true
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    OutlinedTextField(

        value = whereText,

        onValueChange = onWhereChange,

        modifier = Modifier.fillMaxWidth(),

        label = {
            Text("Where will you do it?")
        },

        placeholder = {
            Text("e.g. Around my neighborhood")
        },

        leadingIcon = {

            Icon(
                imageVector =
                    Icons.Default.LocationOn,
                contentDescription = null
            )
        },

        shape =
            RoundedCornerShape(16.dp),

        singleLine = true
    )
}

/*
 * ==============================================================
 * STEP 3 — COMPLETION
 * ==============================================================
 */

@Composable
private fun CompletionStep(
    activityName: String,
    whenText: String,
    whereText: String,
    isPleasure: Boolean,
    isMastery: Boolean,
    completed: Boolean,
    onCompletedChange: (Boolean) -> Unit
) {

    ExerciseIntroCard(

        icon = Icons.Default.TaskAlt,

        title = "Check in with your plan",

        description =
            "When you have completed the activity, check the box below."
    )

    Spacer(
        modifier = Modifier.height(18.dp)
    )

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(20.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SurfaceWhite
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(18.dp)
        ) {

            Text(
                text = activityName,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DetailRow(
                icon = Icons.Default.AccessTime,
                text = whenText
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            DetailRow(
                icon = Icons.Default.LocationOn,
                text = whereText
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                if (isPleasure) {

                    TypeTag(
                        text = "Pleasure",
                        backgroundColor = SoftRose,
                        textColor = Rose
                    )
                }

                if (isMastery) {

                    TypeTag(
                        text = "Mastery",
                        backgroundColor = SoftTeal,
                        textColor = Teal
                    )
                }
            }
        }
    }

    Spacer(
        modifier = Modifier.height(18.dp)
    )

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCompletedChange(!completed)
            },

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (completed) {
                        SoftLavender
                    } else {
                        SurfaceWhite
                    }
            )
    ) {

        Row(

            modifier =
                Modifier.padding(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Checkbox(
                checked = completed,
                onCheckedChange =
                    onCompletedChange
            )

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        "I've completed this activity",

                    color = TextPrimary,

                    fontWeight =
                        FontWeight.SemiBold
                )

                Text(
                    text =
                        "Check this when you have finished.",

                    color =
                        TextSecondary,

                    fontSize = 12.sp
                )
            }

            if (completed) {

                Icon(

                    imageVector =
                        Icons.Default.CheckCircle,

                    contentDescription = null,

                    tint = Lavender
                )
            }
        }
    }
}

/*
 * ==============================================================
 * STEP 4 — REFLECTION
 * ==============================================================
 */

@Composable
private fun ReflectionStep(
    reflection: TextFieldValue,
    onReflectionChange:
        (TextFieldValue) -> Unit
) {

    ExerciseIntroCard(

        icon = Icons.Default.Psychology,

        title = "Reflect on your experience",

        description =
            "Take a moment to notice what the experience was like. There is no right or wrong answer."
    )

    Spacer(
        modifier = Modifier.height(20.dp)
    )

    ReflectionPromptCard(

        icon = Icons.Default.SelfImprovement,

        title = "Before the activity",

        text =
            "How were you feeling before you started? Was anything making it difficult to get started?"
    )

    Spacer(
        modifier = Modifier.height(12.dp)
    )

    ReflectionPromptCard(

        icon = Icons.Default.ThumbUp,

        title = "After the activity",

        text =
            "How did you feel afterward? Did anything make the activity easier or harder?"
    )

    Spacer(
        modifier = Modifier.height(20.dp)
    )

    OutlinedTextField(

        value = reflection,

        onValueChange =
            onReflectionChange,

        modifier =
            Modifier.fillMaxWidth(),

        label = {
            Text("Your remarks")
        },

        placeholder = {
            Text(
                "Write anything you'd like to remember..."
            )
        },

        leadingIcon = {

            Icon(
                imageVector =
                    Icons.Default.Edit,
                contentDescription = null
            )
        },

        shape =
            RoundedCornerShape(16.dp),

        minLines = 5,

        maxLines = 8
    )

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    PaleLavender
            )
    ) {

        Row(

            modifier =
                Modifier.padding(16.dp),

            verticalAlignment =
                Alignment.Top
        ) {

            Icon(

                imageVector =
                    Icons.Default.Lightbulb,

                contentDescription = null,

                tint = Lavender
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(

                text =
                    "Small actions count. Completing one meaningful activity is already a step toward building a healthier routine.",

                color =
                    TextSecondary,

                fontSize = 13.sp,

                lineHeight = 19.sp
            )
        }
    }
}

/*
 * ==============================================================
 * REUSABLE COMPONENTS
 * ==============================================================
 */

@Composable
private fun ExerciseIntroCard(
    icon: ImageVector,
    title: String,
    description: String
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    PaleLavender
            )
    ) {

        Row(

            modifier =
                Modifier.padding(18.dp),

            verticalAlignment =
                Alignment.Top
        ) {

            Box(

                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                        SoftLavender
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Lavender
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

@Composable
private fun ActivityTypeCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    iconColor: Color,
    modifier: Modifier
) {

    Card(

        modifier =
            modifier.clickable(
                onClick = onClick
            ),

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (selected) {
                        selectedColor
                    } else {
                        SurfaceWhite
                    }
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    if (selected) 2.dp else 1.dp
            )
    ) {

        Column(

            modifier =
                Modifier.padding(16.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(

                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected) {
                            Color.White.copy(
                                alpha = 0.7f
                            )
                        } else {
                            selectedColor
                        }
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector =
                        if (title == "Pleasure") {
                            Icons.Default.Star
                        } else {
                            Icons.Default.TaskAlt
                        },

                    contentDescription =
                        title,

                    tint =
                        iconColor
                )
            }

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            Text(
                text = title,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(
                text = description,
                color = TextSecondary,
                fontSize = 12.sp
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            if (selected) {

                Icon(
                    imageVector =
                        Icons.Default.CheckCircle,
                    contentDescription =
                        "Selected",
                    tint =
                        iconColor,
                    modifier =
                        Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    text: String
) {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Lavender,
            modifier =
                Modifier.size(19.dp)
        )

        Spacer(
            modifier =
                Modifier.width(10.dp)
        )

        Text(
            text = text,
            color = TextSecondary,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun TypeTag(
    text: String,
    backgroundColor: Color,
    textColor: Color
) {

    Box(

        modifier = Modifier
            .clip(
                RoundedCornerShape(50)
            )
            .background(
                backgroundColor
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    ) {

        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ReflectionPromptCard(
    icon: ImageVector,
    title: String,
    text: String
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SurfaceWhite
            )
    ) {

        Row(

            modifier =
                Modifier.padding(16.dp),

            verticalAlignment =
                Alignment.Top
        ) {

            Box(

                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(
                        SoftLavender
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Lavender,
                    modifier =
                        Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = title,
                    color = TextPrimary,
                    fontWeight =
                        FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
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

