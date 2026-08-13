package com.example.moodselector.presentations.cbt.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    scheduledActivityId: Int?,
    onBackClick: () -> Unit,
    onExerciseCompleted: () -> Unit,
    onViewScheduledActivities: () -> Unit,
    scheduledViewModel: ScheduledCBTActivityViewModel = hiltViewModel()
) {

    /*
     * --------------------------------------------------
     * State
     * --------------------------------------------------
     *
     * IMPORTANT:
     *
     * This screen is ONLY for creating/editing a schedule.
     *
     * Completion is intentionally NOT part of this screen.
     *
     * There is no:
     * - completed state
     * - completion checkbox
     * - completion persistence
     * - reflection step
     *
     * Those belong to the separate completion flow.
     */

    var currentStep by remember {
        mutableIntStateOf(0)
    }

    var activityName by remember {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    var isPleasure by remember {
        mutableStateOf(false)
    }

    var isMastery by remember {
        mutableStateOf(false)
    }

    var whenText by remember {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    var whereText by remember {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    var isSaving by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(
            scheduledActivityId != null
        )
    }

    /*
     * --------------------------------------------------
     * Load existing scheduled activity when editing
     * --------------------------------------------------
     */

    LaunchedEffect(scheduledActivityId) {

        if (scheduledActivityId != null) {

            val scheduledActivity =
                scheduledViewModel
                    .getScheduledActivityById(
                        scheduledActivityId
                    )

            scheduledActivity?.let {

                activityName =
                    TextFieldValue(
                        it.activityName
                    )

                whenText =
                    TextFieldValue(
                        it.scheduledWhen
                    )

                whereText =
                    TextFieldValue(
                        it.scheduledWhere
                    )

                isPleasure =
                    it.activityType.contains(
                        "Pleasure",
                        ignoreCase = true
                    )

                isMastery =
                    it.activityType.contains(
                        "Mastery",
                        ignoreCase = true
                    )
            }

            isLoading = false
        }
    }

    /*
     * --------------------------------------------------
     * ONLY TWO STEPS
     * --------------------------------------------------
     */

    val totalSteps = 2

    /*
     * --------------------------------------------------
     * Activity type
     * --------------------------------------------------
     */

    val activityType =
        buildList {

            if (isPleasure) {
                add("Pleasure")
            }

            if (isMastery) {
                add("Mastery")
            }

        }.joinToString(" + ")

    /*
     * --------------------------------------------------
     * Loading state
     * --------------------------------------------------
     */

    if (isLoading) {

        Scaffold(
            containerColor = Background
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = "Loading your plan...",
                    color = TextSecondary
                )
            }
        }

        return
    }

    /*
     * --------------------------------------------------
     * Main screen
     * --------------------------------------------------
     */

    Scaffold(

        containerColor = Background,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = activity.title,
                            fontWeight =
                                FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        Text(
                            text =
                                if (
                                    scheduledActivityId != null
                                ) {
                                    "Edit your plan"
                                } else {
                                    "Behavioral Activation"
                                },
                            color =
                                TextSecondary,
                            fontSize =
                                12.sp
                        )
                    }
                },

                navigationIcon = {

                    IconButton(
                        onClick =
                            onBackClick
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,
                            contentDescription =
                                "Back"
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick =
                            onViewScheduledActivities
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.CalendarToday,
                            contentDescription =
                                "Scheduled activities",
                            tint =
                                Lavender
                        )
                    }
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            Background
                    )
            )
        }

    ) { paddingValues ->

        Column(

            modifier =
                Modifier
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
                    (currentStep + 1)
                        .toFloat() /
                            totalSteps
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(4.dp),

                color =
                    Lavender,

                trackColor =
                    SoftLavender
            )

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(
                            horizontal = 20.dp,
                            vertical = 18.dp
                        )
            ) {

                StepHeader(
                    step =
                        currentStep + 1,
                    totalSteps =
                        totalSteps
                )

                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )

                /*
                 * --------------------------------------------------
                 * Scheduling steps ONLY
                 * --------------------------------------------------
                 */

                when (currentStep) {

                    0 -> {

                        ActivitySelectionStep(

                            activityName =
                                activityName,

                            onActivityNameChange = {
                                activityName = it
                            },

                            isPleasure =
                                isPleasure,

                            onPleasureChange = {
                                isPleasure = it
                            },

                            isMastery =
                                isMastery,

                            onMasteryChange = {
                                isMastery = it
                            }
                        )
                    }

                    1 -> {

                        SchedulingStep(

                            whenText =
                                whenText,

                            onWhenChange = {
                                whenText = it
                            },

                            whereText =
                                whereText,

                            onWhereChange = {
                                whereText = it
                            }
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(28.dp)
                )

                /*
                 * --------------------------------------------------
                 * Navigation
                 * --------------------------------------------------
                 */

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    if (currentStep > 0) {

                        Button(

                            onClick = {
                                currentStep--
                            },

                            modifier =
                                Modifier.weight(1f),

                            colors =
                                ButtonDefaults
                                    .buttonColors(
                                        containerColor =
                                            SoftLavender,
                                        contentColor =
                                            Lavender
                                    ),

                            shape =
                                RoundedCornerShape(
                                    16.dp
                                )
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

                            /*
                             * --------------------------------------
                             * Step 1 -> Step 2
                             * --------------------------------------
                             */

                            if (
                                currentStep <
                                totalSteps - 1
                            ) {

                                currentStep++

                                return@Button
                            }

                            /*
                             * --------------------------------------
                             * Final step
                             * --------------------------------------
                             *
                             * This ONLY saves the schedule.
                             *
                             * It does NOT:
                             * - mark the activity complete
                             * - save CBT completion
                             * - create a progress entry
                             * - ask for reflection
                             */

                            if (isSaving) {
                                return@Button
                            }

                            isSaving = true

                            scheduledViewModel.saveScheduledActivity(

                                id =
                                    scheduledActivityId ?: 0,

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

                                onSaved = {

                                    /*
                                     * The schedule has been saved.
                                     *
                                     * We return to the previous flow.
                                     *
                                     * Completion will happen later,
                                     * after the user actually performs
                                     * the scheduled activity.
                                     */

                                    isSaving = false

                                    onExerciseCompleted()
                                }
                            )
                        },

                        modifier =
                            Modifier.weight(1f),

                        enabled =
                            !isSaving &&
                                    when (currentStep) {

                                        0 ->
                                            activityName
                                                .text
                                                .isNotBlank() &&
                                                    (
                                                            isPleasure ||
                                                                    isMastery
                                                            )

                                        1 ->
                                            whenText
                                                .text
                                                .isNotBlank() &&
                                                    whereText
                                                        .text
                                                        .isNotBlank()

                                        else ->
                                            false
                                    },

                        colors =
                            ButtonDefaults
                                .buttonColors(

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
                            RoundedCornerShape(
                                16.dp
                            )
                    ) {

                        Text(

                            text =
                                if (
                                    currentStep ==
                                    totalSteps - 1
                                ) {
                                    if (
                                        scheduledActivityId !=
                                        null
                                    ) {
                                        "Save Changes"
                                    } else {
                                        "Save Schedule"
                                    }
                                } else {
                                    "Continue"
                                },

                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }
                }

                /*
                 * --------------------------------------------------
                 * Scheduled activity shortcut
                 * --------------------------------------------------
                 */

                if (
                    currentStep == 0 &&
                    scheduledActivityId == null
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )

                    TextButton(

                        onClick =
                            onViewScheduledActivities,

                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.CalendarToday,
                            contentDescription =
                                null,
                            tint =
                                Lavender,
                            modifier =
                                Modifier.size(
                                    18.dp
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.width(6.dp)
                        )

                        Text(
                            text =
                                "View my scheduled activities",
                            color =
                                Lavender
                        )
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

        modifier =
            Modifier.fillMaxWidth(),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text =
                "Step $step of $totalSteps",

            color =
                Lavender,

            fontWeight =
                FontWeight.Bold,

            fontSize =
                13.sp
        )

        Text(

            text =
                when (step) {

                    1 ->
                        "Choose your activity"

                    else ->
                        "Make your plan"
                },

            color =
                TextSecondary,

            fontSize =
                13.sp
        )
    }
}


/*
 * ==============================================================
 * STEP 1
 * ==============================================================
 */

@Composable
private fun ActivitySelectionStep(
    activityName: TextFieldValue,
    onActivityNameChange:
        (TextFieldValue) -> Unit,
    isPleasure: Boolean,
    onPleasureChange:
        (Boolean) -> Unit,
    isMastery: Boolean,
    onMasteryChange:
        (Boolean) -> Unit
) {

    ExerciseIntroCard(

        icon =
            Icons.Default.Star,

        title =
            "Choose one meaningful activity",

        description =
            "Think of one activity you would like to complete. Keep it realistic and achievable."
    )

    Spacer(
        modifier =
            Modifier.height(20.dp)
    )

    OutlinedTextField(

        value =
            activityName,

        onValueChange =
            onActivityNameChange,

        modifier =
            Modifier.fillMaxWidth(),

        label = {
            Text(
                "What would you like to do?"
            )
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
                contentDescription =
                    null
            )
        },

        shape =
            RoundedCornerShape(16.dp),

        singleLine = true
    )

    Spacer(
        modifier =
            Modifier.height(20.dp)
    )

    Text(
        text =
            "How would you classify this activity?",

        color =
            TextPrimary,

        fontWeight =
            FontWeight.SemiBold,

        fontSize =
            15.sp
    )

    Spacer(
        modifier =
            Modifier.height(12.dp)
    )

    Row(

        modifier =
            Modifier.fillMaxWidth(),

        horizontalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        ActivityTypeCard(

            title =
                "Pleasure",

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

            title =
                "Mastery",

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
 * STEP 2
 * ==============================================================
 */

@Composable
private fun SchedulingStep(
    whenText: TextFieldValue,
    onWhenChange:
        (TextFieldValue) -> Unit,
    whereText: TextFieldValue,
    onWhereChange:
        (TextFieldValue) -> Unit
) {

    ExerciseIntroCard(

        icon =
            Icons.Default.CalendarToday,

        title =
            "Make your plan specific",

        description =
            "Deciding when and where you will do something makes it easier to turn an intention into action."
    )

    Spacer(
        modifier =
            Modifier.height(20.dp)
    )

    OutlinedTextField(

        value =
            whenText,

        onValueChange =
            onWhenChange,

        modifier =
            Modifier.fillMaxWidth(),

        label = {
            Text(
                "When will you do it?"
            )
        },

        placeholder = {
            Text(
                "e.g. Tomorrow at 6:00 PM"
            )
        },

        leadingIcon = {

            Icon(
                imageVector =
                    Icons.Default.AccessTime,
                contentDescription =
                    null
            )
        },

        trailingIcon = {

            Icon(
                imageVector =
                    Icons.Default.Event,
                contentDescription =
                    null,
                tint =
                    Lavender
            )
        },

        shape =
            RoundedCornerShape(16.dp),

        singleLine = true
    )

    Spacer(
        modifier =
            Modifier.height(16.dp)
    )

    OutlinedTextField(

        value =
            whereText,

        onValueChange =
            onWhereChange,

        modifier =
            Modifier.fillMaxWidth(),

        label = {
            Text(
                "Where will you do it?"
            )
        },

        placeholder = {
            Text(
                "e.g. Around my neighborhood"
            )
        },

        leadingIcon = {

            Icon(
                imageVector =
                    Icons.Default.LocationOn,
                contentDescription =
                    null
            )
        },

        shape =
            RoundedCornerShape(16.dp),

        singleLine = true
    )

    Spacer(
        modifier =
            Modifier.height(18.dp)
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

                contentDescription =
                    null,

                tint =
                    Lavender
            )

            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )

            Text(

                text =
                    "You are creating a plan, not completing the activity yet. Once saved, your plan will appear in Scheduled Activities.",

                color =
                    TextSecondary,

                fontSize =
                    13.sp,

                lineHeight =
                    19.sp
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

                modifier =
                    Modifier
                        .size(46.dp)
                        .clip(
                            CircleShape
                        )
                        .background(
                            SoftLavender
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        icon,

                    contentDescription =
                        null,

                    tint =
                        Lavender
                )
            }

            Spacer(
                modifier =
                    Modifier.width(14.dp)
            )

            Column {

                Text(
                    text =
                        title,

                    color =
                        TextPrimary,

                    fontWeight =
                        FontWeight.Bold,

                    fontSize =
                        17.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )

                Text(
                    text =
                        description,

                    color =
                        TextSecondary,

                    fontSize =
                        13.sp,

                    lineHeight =
                        19.sp
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
                    if (selected) {
                        2.dp
                    } else {
                        1.dp
                    }
            )
    ) {

        Column(

            modifier =
                Modifier.padding(16.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(

                modifier =
                    Modifier
                        .size(42.dp)
                        .clip(
                            CircleShape
                        )
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
                        if (
                            title ==
                            "Pleasure"
                        ) {
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
                text =
                    title,

                color =
                    TextPrimary,

                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(
                text =
                    description,

                color =
                    TextSecondary,

                fontSize =
                    12.sp
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