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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodselector.data.local.entity.ScheduledCBTActivityEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduledActivitiesScreen(
    onBackClick: () -> Unit,
    onScheduleActivityClick: () -> Unit,
    onEditActivity: (ScheduledCBTActivityEntity) -> Unit,
    onCompleteActivity: (ScheduledCBTActivityEntity) -> Unit,
    viewModel: ScheduledCBTActivityViewModel = hiltViewModel()
) {

    /*
     * ----------------------------------------------------------
     * THEME COLORS
     * ----------------------------------------------------------
     *
     * These values come from MaterialTheme so the screen
     * automatically responds to the application's light/dark
     * theme.
     */

    val colorScheme = MaterialTheme.colorScheme

    val lavender = colorScheme.primary
    val softLavender = colorScheme.surfaceVariant
    val paleLavender = colorScheme.surfaceVariant

    val softRose =
        if (colorScheme.background.luminance() < 0.5f) {
            Color(0xFF4A2635)
        } else {
            Color(0xFFFFEEF4)
        }

    val rose =
        if (colorScheme.background.luminance() < 0.5f) {
            Color(0xFFFFA9C1)
        } else {
            Color(0xFFE88BA5)
        }

    val softTeal =
        if (colorScheme.background.luminance() < 0.5f) {
            Color(0xFF203C3A)
        } else {
            Color(0xFFE8F7F5)
        }

    val teal =
        if (colorScheme.background.luminance() < 0.5f) {
            Color(0xFF7ACBC0)
        } else {
            Color(0xFF4BA89C)
        }

    val textPrimary = colorScheme.onBackground
    val textSecondary = colorScheme.onSurfaceVariant
    val surfaceWhite = colorScheme.surface
    val background = colorScheme.background

    /*
     * ----------------------------------------------------------
     * SCHEDULED ACTIVITIES
     * ----------------------------------------------------------
     *
     * This screen observes scheduled activities only.
     *
     * Completing an activity is handled by the dedicated
     * completion screen. This screen only passes the selected
     * scheduled activity to that flow.
     */

    val scheduledActivities by viewModel
        .scheduledActivities
        .collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

    /*
     * ----------------------------------------------------------
     * DELETE STATE
     * ----------------------------------------------------------
     */

    var activityToDelete by remember {
        mutableStateOf<ScheduledCBTActivityEntity?>(null)
    }

    /*
     * ----------------------------------------------------------
     * SCREEN
     * ----------------------------------------------------------
     */

    Scaffold(

        containerColor = background,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "Scheduled Activities",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = textPrimary
                        )

                        Text(
                            text = "Your plans at a glance",
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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = textPrimary
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = background,
                    titleContentColor = textPrimary,
                    navigationIconContentColor = textPrimary
                )
            )
        }

    ) { paddingValues ->

        if (scheduledActivities.isEmpty()) {

            EmptyScheduledActivitiesState(

                paddingValues = paddingValues,

                lavender = lavender,
                softLavender = softLavender,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                background = background,

                onScheduleActivityClick =
                    onScheduleActivityClick
            )

        } else {

            ScheduledActivitiesContent(

                paddingValues = paddingValues,

                activities = scheduledActivities,

                lavender = lavender,
                softLavender = softLavender,
                paleLavender = paleLavender,
                softRose = softRose,
                rose = rose,
                softTeal = softTeal,
                teal = teal,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                surfaceWhite = surfaceWhite,
                background = background,

                onEditActivity = onEditActivity,

                onDeleteActivity = {
                    activityToDelete = it
                },

                /*
                 * The selected activity is passed to the
                 * dedicated completion flow.
                 *
                 * No completion is persisted here.
                 */

                onCompleteActivity = onCompleteActivity,

                onScheduleActivityClick =
                    onScheduleActivityClick
            )
        }
    }

    /*
     * ----------------------------------------------------------
     * DELETE CONFIRMATION
     * ----------------------------------------------------------
     */

    activityToDelete?.let { activity ->

        DeleteScheduledActivityDialog(

            activity = activity,

            textPrimary = textPrimary,
            textSecondary = textSecondary,
            surfaceWhite = surfaceWhite,
            rose = rose,

            onDismiss = {
                activityToDelete = null
            },

            onConfirm = {

                viewModel.deleteScheduledActivity(
                    activity
                )

                activityToDelete = null
            }
        )
    }
}


/*
 * ==============================================================
 * SCHEDULED ACTIVITIES CONTENT
 * ==============================================================
 */

@Composable
private fun ScheduledActivitiesContent(
    paddingValues: PaddingValues,
    activities: List<ScheduledCBTActivityEntity>,

    lavender: Color,
    softLavender: Color,
    paleLavender: Color,
    softRose: Color,
    rose: Color,
    softTeal: Color,
    teal: Color,
    textPrimary: Color,
    textSecondary: Color,
    surfaceWhite: Color,
    background: Color,

    onEditActivity: (ScheduledCBTActivityEntity) -> Unit,
    onDeleteActivity: (ScheduledCBTActivityEntity) -> Unit,
    onCompleteActivity: (ScheduledCBTActivityEntity) -> Unit,
    onScheduleActivityClick: () -> Unit
) {

    LazyColumn(

        modifier =
            Modifier
                .fillMaxSize()
                .background(background)
                .padding(paddingValues)
                .navigationBarsPadding(),

        contentPadding =
            PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 18.dp,
                bottom = 32.dp
            ),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        item {

            ScheduledActivitiesHeader(
                count = activities.size,
                lavender = lavender,
                softLavender = softLavender,
                paleLavender = paleLavender,
                textPrimary = textPrimary,
                textSecondary = textSecondary
            )
        }

        items(

            items = activities,

            key = {
                it.id
            }

        ) { activity ->

            ScheduledActivityCard(

                activity = activity,

                lavender = lavender,
                softLavender = softLavender,
                paleLavender = paleLavender,
                softRose = softRose,
                rose = rose,
                softTeal = softTeal,
                teal = teal,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                surfaceWhite = surfaceWhite,

                onEdit = {
                    onEditActivity(activity)
                },

                onDelete = {
                    onDeleteActivity(activity)
                },

                onComplete = {
                    onCompleteActivity(activity)
                }
            )
        }

        item {

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            ScheduleAnotherActivityCard(

                softTeal = softTeal,
                teal = teal,
                textPrimary = textPrimary,
                textSecondary = textSecondary,

                onClick =
                    onScheduleActivityClick
            )
        }
    }
}


/*
 * ==============================================================
 * HEADER
 * ==============================================================
 */

@Composable
private fun ScheduledActivitiesHeader(
    count: Int,
    lavender: Color,
    softLavender: Color,
    paleLavender: Color,
    textPrimary: Color,
    textSecondary: Color
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(

                modifier =
                    Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(softLavender),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector =
                        Icons.Default.CalendarToday,

                    contentDescription = null,

                    tint = lavender,

                    modifier =
                        Modifier.size(25.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.width(14.dp)
            )

            Column {

                Text(

                    text =
                        if (count == 1) {
                            "1 activity planned"
                        } else {
                            "$count activities planned"
                        },

                    style =
                        MaterialTheme.typography.titleLarge,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        textPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )

                Text(

                    text =
                        "These are the activities you have chosen to work on.",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        textSecondary
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        /*
         * --------------------------------------------------
         * INFORMATION CARD
         * --------------------------------------------------
         */

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(20.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        paleLavender
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

                Icon(

                    imageVector =
                        Icons.Default.SelfImprovement,

                    contentDescription =
                        null,

                    tint =
                        lavender,

                    modifier =
                        Modifier.size(22.dp)
                )

                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )

                Text(

                    text =
                        "When you are ready, open an activity to complete it and reflect on your experience. Completed activities will appear in CBT Progress.",

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
 * SCHEDULED ACTIVITY CARD
 * ==============================================================
 */

@Composable
private fun ScheduledActivityCard(
    activity: ScheduledCBTActivityEntity,

    lavender: Color,
    softLavender: Color,
    paleLavender: Color,
    softRose: Color,
    rose: Color,
    softTeal: Color,
    teal: Color,
    textPrimary: Color,
    textSecondary: Color,
    surfaceWhite: Color,

    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onComplete: () -> Unit
) {

    val hasPleasure =
        activity.activityType.contains(
            "Pleasure",
            ignoreCase = true
        )

    val hasMastery =
        activity.activityType.contains(
            "Mastery",
            ignoreCase = true
        )

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    surfaceWhite
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
    ) {

        Column(

            modifier =
                Modifier.padding(18.dp)
        ) {

            /*
             * --------------------------------------------------
             * ACTIVITY TITLE
             * --------------------------------------------------
             */

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
                                softLavender
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.TaskAlt,

                        contentDescription =
                            null,

                        tint =
                            lavender,

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

                        style =
                            MaterialTheme.typography.titleMedium,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            textPrimary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(

                        text =
                            activity.activityTitle,

                        style =
                            MaterialTheme.typography.bodySmall,

                        color =
                            textSecondary
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            /*
             * --------------------------------------------------
             * WHEN
             * --------------------------------------------------
             */

            ScheduledDetailRow(

                icon =
                    Icons.Default.AccessTime,

                title =
                    "When",

                value =
                    activity.scheduledWhen,

                lavender = lavender,
                paleLavender = paleLavender,
                textPrimary = textPrimary,
                textSecondary = textSecondary
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            /*
             * --------------------------------------------------
             * WHERE
             * --------------------------------------------------
             */

            ScheduledDetailRow(

                icon =
                    Icons.Default.LocationOn,

                title =
                    "Where",

                value =
                    activity.scheduledWhere,

                lavender = lavender,
                paleLavender = paleLavender,
                textPrimary = textPrimary,
                textSecondary = textSecondary
            )

            /*
             * --------------------------------------------------
             * ACTIVITY TYPE
             * --------------------------------------------------
             */

            if (hasPleasure || hasMastery) {

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )

                Row(

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    if (hasPleasure) {

                        ScheduledTypeTag(

                            text = "Pleasure",

                            backgroundColor =
                                softRose,

                            textColor =
                                rose,

                            icon =
                                Icons.Default.Star
                        )
                    }

                    if (hasMastery) {

                        ScheduledTypeTag(

                            text = "Mastery",

                            backgroundColor =
                                softTeal,

                            textColor =
                                teal,

                            icon =
                                Icons.Default.TaskAlt
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            /*
             * --------------------------------------------------
             * COMPLETE & REFLECT
             * --------------------------------------------------
             */

            Button(

                onClick = onComplete,

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            teal,

                        contentColor =
                            Color.White
                    ),

                shape =
                    RoundedCornerShape(14.dp)
            ) {

                Icon(

                    imageVector =
                        Icons.Default.CheckCircle,

                    contentDescription =
                        null,

                    modifier =
                        Modifier.size(19.dp)
                )

                Spacer(
                    modifier =
                        Modifier.width(7.dp)
                )

                Text(

                    text =
                        "Complete & Reflect",

                    fontWeight =
                        FontWeight.SemiBold
                )
            }

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            /*
             * --------------------------------------------------
             * EDIT / REMOVE
             * --------------------------------------------------
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                Button(

                    onClick = onEdit,

                    modifier =
                        Modifier.weight(1f),

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                softLavender,

                            contentColor =
                                lavender
                        ),

                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Edit,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(6.dp)
                    )

                    Text(
                        text = "Edit"
                    )
                }

                Button(

                    onClick = onDelete,

                    modifier =
                        Modifier.weight(1f),

                    colors =
                        ButtonDefaults.buttonColors(

                            containerColor =
                                softRose,

                            contentColor =
                                rose
                        ),

                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Delete,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(6.dp)
                    )

                    Text(
                        text = "Remove"
                    )
                }
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
private fun ScheduledDetailRow(
    icon: ImageVector,
    title: String,
    value: String,
    lavender: Color,
    paleLavender: Color,
    textPrimary: Color,
    textSecondary: Color
) {

    Row(

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Box(

            modifier =
                Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(paleLavender),

            contentAlignment =
                Alignment.Center
        ) {

            Icon(

                imageVector =
                    icon,

                contentDescription =
                    null,

                tint =
                    lavender,

                modifier =
                    Modifier.size(18.dp)
            )
        }

        Spacer(
            modifier =
                Modifier.width(10.dp)
        )

        Column {

            Text(

                text =
                    title,

                color =
                    textSecondary,

                fontSize =
                    11.sp,

                fontWeight =
                    FontWeight.Medium
            )

            Text(

                text =
                    value,

                color =
                    textPrimary,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Medium
            )
        }
    }
}


/*
 * ==============================================================
 * TYPE TAG
 * ==============================================================
 */

@Composable
private fun ScheduledTypeTag(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    icon: ImageVector
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
                    horizontal = 10.dp,
                    vertical = 6.dp
                )
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
                    textColor,

                modifier =
                    Modifier.size(15.dp)
            )

            Spacer(
                modifier =
                    Modifier.width(5.dp)
            )

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
}


/*
 * ==============================================================
 * EMPTY STATE
 * ==============================================================
 */

@Composable
private fun EmptyScheduledActivitiesState(
    paddingValues: PaddingValues,
    lavender: Color,
    softLavender: Color,
    textPrimary: Color,
    textSecondary: Color,
    background: Color,
    onScheduleActivityClick: () -> Unit
) {

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .background(background)
                .padding(paddingValues)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Box(

            modifier =
                Modifier
                    .size(78.dp)
                    .clip(CircleShape)
                    .background(softLavender),

            contentAlignment =
                Alignment.Center
        ) {

            Icon(

                imageVector =
                    Icons.Default.CalendarToday,

                contentDescription =
                    null,

                tint =
                    lavender,

                modifier =
                    Modifier.size(38.dp)
            )
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        Text(

            text =
                "No activities scheduled yet",

            style =
                MaterialTheme.typography.titleLarge,

            fontWeight =
                FontWeight.SemiBold,

            color =
                textPrimary
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(

            text =
                "Choose a meaningful activity and create a specific plan for when and where you will do it.",

            style =
                MaterialTheme.typography.bodyMedium,

            color =
                textSecondary
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        Button(

            onClick =
                onScheduleActivityClick,

            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        lavender
                ),

            shape =
                RoundedCornerShape(16.dp)
        ) {

            Icon(

                imageVector =
                    Icons.Default.CalendarToday,

                contentDescription =
                    null,

                modifier =
                    Modifier.size(18.dp)
            )

            Spacer(
                modifier =
                    Modifier.width(8.dp)
            )

            Text(
                text =
                    "Schedule an activity"
            )
        }
    }
}


/*
 * ==============================================================
 * SCHEDULE ANOTHER ACTIVITY
 * ==============================================================
 */

@Composable
private fun ScheduleAnotherActivityCard(
    softTeal: Color,
    teal: Color,
    textPrimary: Color,
    textSecondary: Color,
    onClick: () -> Unit
) {

    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onClick
                ),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    softTeal
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Row(

            modifier =
                Modifier.padding(18.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(

                modifier =
                    Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            Color.White.copy(
                                alpha = 0.7f
                            )
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector =
                        Icons.Default.CalendarToday,

                    contentDescription =
                        null,

                    tint =
                        teal,

                    modifier =
                        Modifier.size(24.dp)
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
                        "Schedule another activity",

                    color =
                        textPrimary,

                    fontWeight =
                        FontWeight.SemiBold,

                    fontSize =
                        15.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )

                Text(

                    text =
                        "Create a new plan for another meaningful activity.",

                    color =
                        textSecondary,

                    fontSize =
                        12.sp
                )
            }
        }
    }
}


/*
 * ==============================================================
 * DELETE DIALOG
 * ==============================================================
 */

@Composable
private fun DeleteScheduledActivityDialog(
    activity: ScheduledCBTActivityEntity,
    textPrimary: Color,
    textSecondary: Color,
    surfaceWhite: Color,
    rose: Color,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text(
                text =
                    "Remove this plan?",
                color =
                    textPrimary
            )
        },

        text = {

            Text(
                text =
                    "Your scheduled plan for \"${activity.activityName}\" will be removed. This does not delete the CBT activity itself.",
                color =
                    textSecondary
            )
        },

        confirmButton = {

            TextButton(
                onClick =
                    onConfirm
            ) {

                Text(

                    text =
                        "Remove",

                    color =
                        rose,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick =
                    onDismiss
            ) {

                Text(

                    text =
                        "Cancel",

                    color =
                        textSecondary
                )
            }
        },

        containerColor =
            surfaceWhite,

        shape =
            RoundedCornerShape(24.dp)
    )
}