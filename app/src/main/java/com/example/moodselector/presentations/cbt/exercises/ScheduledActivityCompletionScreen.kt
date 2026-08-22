package com.example.moodselector.presentations.cbt.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity
import com.example.moodselector.presentations.cbt.progress.CBTProgressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduledActivityCompletionScreen(
    scheduledActivity: ScheduledCBTActivityEntity,
    onBackClick: () -> Unit,
    onActivityCompleted: () -> Unit,
    viewModel: CBTProgressViewModel = hiltViewModel()
) {

    /*
     * --------------------------------------------------
     * THEME COLORS
     * --------------------------------------------------
     *
     * All colors come from MaterialTheme so this screen
     * responds automatically to the application's
     * light/dark theme.
     */

    val colorScheme = MaterialTheme.colorScheme

    val primaryColor = colorScheme.primary
    val primaryContainer = colorScheme.primaryContainer
    val onPrimaryContainer = colorScheme.onPrimaryContainer

    val backgroundColor = colorScheme.background
    val surfaceColor = colorScheme.surface

    val textPrimary = colorScheme.onBackground
    val textSecondary = colorScheme.onSurfaceVariant

    val softRose = colorScheme.tertiaryContainer
    val rose = colorScheme.onTertiaryContainer

    val softTeal = colorScheme.secondaryContainer
    val teal = colorScheme.onSecondaryContainer


    /*
     * --------------------------------------------------
     * STATE
     * --------------------------------------------------
     */

    var completed by remember {
        mutableStateOf(false)
    }

    var reflection by remember {
        mutableStateOf("")
    }

    var isSaving by remember {
        mutableStateOf(false)
    }


    /*
     * --------------------------------------------------
     * SCREEN
     * --------------------------------------------------
     */

    Scaffold(

        containerColor =
            backgroundColor,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "Complete Activity",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        Text(
                            text = "Activity Scheduling",
                            color = textSecondary,
                            fontSize = 12.sp
                        )
                    }
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription =
                                "Back"
                        )
                    }
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            backgroundColor
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
             * INTRODUCTION
             * --------------------------------------------------
             */

            ExerciseIntroCard(

                icon =
                    Icons.Default.TaskAlt,

                title =
                    "Check in with your activity",

                description =
                    "Take a moment to complete the activity you planned and notice how the experience felt.",

                primaryColor =
                    primaryColor,

                primaryContainer =
                    primaryContainer,

                textPrimary =
                    textPrimary,

                textSecondary =
                    textSecondary
            )

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )


            /*
             * --------------------------------------------------
             * ACTIVITY DETAILS
             * --------------------------------------------------
             */

            ActivityDetailsCard(
                activity = scheduledActivity,
                primaryColor = primaryColor,
                primaryContainer = primaryContainer,
                surfaceColor = surfaceColor,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                softRose = softRose,
                rose = rose,
                softTeal = softTeal,
                teal = teal
            )

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )


            /*
             * --------------------------------------------------
             * COMPLETION CHECKBOX
             * --------------------------------------------------
             */

            Card(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {

                            completed =
                                !completed
                        },

                shape =
                    RoundedCornerShape(20.dp),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            if (completed) {
                                primaryContainer
                            } else {
                                surfaceColor
                            }
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    )
            ) {

                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(14.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Checkbox(

                        checked =
                            completed,

                        onCheckedChange = {
                            completed = it
                        }
                    )

                    Spacer(
                        modifier =
                            Modifier.width(6.dp)
                    )

                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(

                            text =
                                "I've completed this activity",

                            color =
                                textPrimary,

                            fontWeight =
                                FontWeight.SemiBold
                        )

                        Spacer(
                            modifier =
                                Modifier.height(3.dp)
                        )

                        Text(

                            text =
                                "Check this when you have finished.",

                            color =
                                textSecondary,

                            fontSize =
                                12.sp
                        )
                    }

                    if (completed) {

                        Icon(

                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                null,

                            tint =
                                primaryColor,

                            modifier =
                                Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(22.dp)
            )


            /*
             * --------------------------------------------------
             * REFLECTION
             * --------------------------------------------------
             */

            ReflectionSection(

                reflection =
                    reflection,

                onReflectionChange = {
                    reflection = it
                },

                primaryColor =
                    primaryColor,

                primaryContainer =
                    primaryContainer,

                surfaceColor =
                    surfaceColor,

                textPrimary =
                    textPrimary,

                textSecondary =
                    textSecondary
            )

            Spacer(
                modifier =
                    Modifier.height(28.dp)
            )


            /*
             * --------------------------------------------------
             * COMPLETE BUTTON
             * --------------------------------------------------
             */

            Button(

                onClick = {

                    if (isSaving) {
                        return@Button
                    }

                    isSaving = true

                    viewModel.completeScheduledActivity(

                        activity =
                            scheduledActivity,

                        reflection =
                            reflection,

                        onCompleted = {

                            isSaving = false

                            onActivityCompleted()
                        }
                    )
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                enabled =
                    completed && !isSaving,

                shape =
                    RoundedCornerShape(16.dp),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            primaryColor,

                        contentColor =
                            colorScheme.onPrimary,

                        disabledContainerColor =
                            primaryContainer,

                        disabledContentColor =
                            textSecondary
                    )
            ) {

                if (isSaving) {

                    Text(

                        text =
                            "Saving...",

                        fontWeight =
                            FontWeight.SemiBold
                    )

                } else {

                    Icon(

                        imageVector =
                            Icons.Default.CheckCircle,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )

                    Text(

                        text =
                            "Complete Activity",

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(

                text =
                    "Your completed activity will be added to your CBT progress timeline.",

                modifier =
                    Modifier.fillMaxWidth(),

                color =
                    textSecondary,

                fontSize =
                    12.sp
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )
        }
    }
}


/*
 * ==============================================================
 * ACTIVITY DETAILS CARD
 * ==============================================================
 */

@Composable
private fun ActivityDetailsCard(
    activity: ScheduledCBTActivityEntity,
    primaryColor: Color,
    primaryContainer: Color,
    surfaceColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    softRose: Color,
    rose: Color,
    softTeal: Color,
    teal: Color
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    surfaceColor
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(

            modifier =
                Modifier.padding(20.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(

                    modifier =
                        Modifier
                            .size(48.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(
                                primaryContainer
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Star,

                        contentDescription =
                            null,

                        tint =
                            primaryColor,

                        modifier =
                            Modifier.size(25.dp)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(14.dp)
                )

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(

                        text =
                            activity.activityName,

                        color =
                            textPrimary,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            18.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(

                        text =
                            activity.activityTitle,

                        color =
                            textSecondary,

                        fontSize =
                            13.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            if (
                activity.activityDescription
                    .isNotBlank()
            ) {

                Text(

                    text =
                        activity.activityDescription,

                    color =
                        textSecondary,

                    fontSize =
                        14.sp,

                    lineHeight =
                        20.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )
            }

            DetailRow(

                icon =
                    Icons.Default.AccessTime,

                text =
                    activity.scheduledWhen,

                primaryColor =
                    primaryColor,

                textSecondary =
                    textSecondary
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            DetailRow(

                icon =
                    Icons.Default.LocationOn,

                text =
                    activity.scheduledWhere,

                primaryColor =
                    primaryColor,

                textSecondary =
                    textSecondary
            )

            if (
                activity.activityType
                    .isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    activity.activityType
                        .split(" + ")
                        .forEach { type ->

                            when (type) {

                                "Pleasure" -> {

                                    TypeTag(

                                        text =
                                            type,

                                        backgroundColor =
                                            softRose,

                                        textColor =
                                            rose
                                    )
                                }

                                "Mastery" -> {

                                    TypeTag(

                                        text =
                                            type,

                                        backgroundColor =
                                            softTeal,

                                        textColor =
                                            teal
                                    )
                                }
                            }
                        }
                }
            }
        }
    }
}


/*
 * ==============================================================
 * REFLECTION SECTION
 * ==============================================================
 */

@Composable
private fun ReflectionSection(
    reflection: String,
    onReflectionChange: (String) -> Unit,
    primaryColor: Color,
    primaryContainer: Color,
    surfaceColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {

    Column {

        Text(

            text =
                "Reflect on your experience",

            color =
                textPrimary,

            fontWeight =
                FontWeight.Bold,

            fontSize =
                17.sp
        )

        Spacer(
            modifier =
                Modifier.height(6.dp)
        )

        Text(

            text =
                "There is no right or wrong answer. Notice what the experience was like for you.",

            color =
                textSecondary,

            fontSize =
                13.sp,

            lineHeight =
                19.sp
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        ReflectionPromptCard(

            icon =
                Icons.Default.Psychology,

            title =
                "Before the activity",

            text =
                "How were you feeling before you started? Was anything making it difficult to get started?",

            primaryColor =
                primaryColor,

            primaryContainer =
                primaryContainer,

            surfaceColor =
                surfaceColor,

            textPrimary =
                textPrimary,

            textSecondary =
                textSecondary
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        ReflectionPromptCard(

            icon =
                Icons.Default.ThumbUp,

            title =
                "After the activity",

            text =
                "How did you feel afterward? Did anything make the activity easier or harder?",

            primaryColor =
                primaryColor,

            primaryContainer =
                primaryContainer,

            surfaceColor =
                surfaceColor,

            textPrimary =
                textPrimary,

            textSecondary =
                textSecondary
        )

        Spacer(
            modifier =
                Modifier.height(18.dp)
        )

        OutlinedTextField(

            value =
                reflection,

            onValueChange =
                onReflectionChange,

            modifier =
                Modifier.fillMaxWidth(),

            label = {
                Text(
                    "Your remarks"
                )
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

                    contentDescription =
                        null
                )
            },

            shape =
                RoundedCornerShape(16.dp),

            minLines =
                5,

            maxLines =
                8
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(18.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        primaryContainer
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
                        primaryColor
                )

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Text(

                    text =
                        "Small actions count. Completing one meaningful activity is already a step toward building a healthier routine.",

                    color =
                        textSecondary,

                    fontSize =
                        13.sp,

                    lineHeight =
                        19.sp
                )
            }
        }
    }
}


/*
 * ==============================================================
 * REFLECTION PROMPT
 * ==============================================================
 */

@Composable
private fun ReflectionPromptCard(
    icon: ImageVector,
    title: String,
    text: String,
    primaryColor: Color,
    primaryContainer: Color,
    surfaceColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    surfaceColor
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Row(

            modifier =
                Modifier.padding(16.dp),

            verticalAlignment =
                Alignment.Top
        ) {

            Box(

                modifier =
                    Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(
                            primaryContainer
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
                        primaryColor,

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

                    text =
                        title,

                    color =
                        textPrimary,

                    fontWeight =
                        FontWeight.SemiBold,

                    fontSize =
                        14.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(

                    text =
                        text,

                    color =
                        textSecondary,

                    fontSize =
                        12.sp,

                    lineHeight =
                        18.sp
                )
            }
        }
    }
}


/*
 * ==============================================================
 * DETAIL ROW
 * ==============================================================
 */

@Composable
private fun DetailRow(
    icon: ImageVector,
    text: String,
    primaryColor: Color,
    textSecondary: Color
) {

    Row(

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(

            imageVector =
                icon,

            contentDescription =
                null,

            tint =
                primaryColor,

            modifier =
                Modifier.size(19.dp)
        )

        Spacer(
            modifier =
                Modifier.width(10.dp)
        )

        Text(

            text =
                text,

            color =
                textSecondary,

            fontSize =
                13.sp
        )
    }
}


/*
 * ==============================================================
 * TYPE TAG
 * ==============================================================
 */

@Composable
private fun TypeTag(
    text: String,
    backgroundColor: Color,
    textColor: Color
) {

    Box(

        modifier =
            Modifier
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

            text =
                text,

            color =
                textColor,

            fontWeight =
                FontWeight.SemiBold,

            fontSize =
                12.sp
        )
    }
}


/*
 * ==============================================================
 * INTRO CARD
 * ==============================================================
 */

@Composable
private fun ExerciseIntroCard(
    icon: ImageVector,
    title: String,
    description: String,
    primaryColor: Color,
    primaryContainer: Color,
    textPrimary: Color,
    textSecondary: Color
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    primaryContainer
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
                        .clip(CircleShape)
                        .background(
                            primaryContainer
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
                        primaryColor
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
                        textPrimary,

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
                        textSecondary,

                    fontSize =
                        13.sp,

                    lineHeight =
                        19.sp
                )
            }
        }
    }
}