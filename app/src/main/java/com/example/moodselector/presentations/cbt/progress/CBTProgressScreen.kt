package com.example.moodselector.presentations.cbt.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Lavender = Color(0xFF6C63FF)
private val SoftLavender = Color(0xFFEDEBFF)
private val PaleLavender = Color(0xFFF7F5FF)

private val SoftRose = Color(0xFFFFEEF4)
private val Rose = Color(0xFFE88BA5)

private val SoftTeal = Color(0xFFE8F7F5)
private val Teal = Color(0xFF4BA89C)

private val TextPrimary = Color(0xFF292638)
private val TextSecondary = Color(0xFF777282)

private val Background = Color(0xFFFAF9FD)
private val SurfaceWhite = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CBTProgressScreen(
    onBackClick: () -> Unit,
    viewModel: CBTProgressViewModel = hiltViewModel()
) {

    val progressItems by
    viewModel.progressItems
        .collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

    /*
     * IMPORTANT:
     *
     * This is the number of UNIQUE exercises completed,
     * not the number of completion records.
     */
    val completionCount by
    viewModel.uniqueCompletedExerciseCount
        .collectAsStateWithLifecycle(
            initialValue = 0
        )

    Scaffold(

        containerColor = Background,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "Your Progress",
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            color = TextPrimary
                        )

                        Text(
                            text = "Your CBT journey",
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
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,

                            contentDescription =
                                "Back",

                            tint =
                                TextPrimary
                        )
                    }
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Background
                    )
            )
        }

    ) { paddingValues ->

        if (progressItems.isEmpty()) {

            EmptyProgressState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

        } else {

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),

                contentPadding =
                    PaddingValues(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(18.dp)
            ) {

                /*
                 * ------------------------------------------
                 * PROGRESS SUMMARY
                 * ------------------------------------------
                 */

                item {

                    ProgressSummaryCard(
                        completionCount =
                            completionCount
                    )
                }


                /*
                 * ------------------------------------------
                 * JOURNEY
                 * ------------------------------------------
                 */

                item {

                    Text(
                        text = "Your Journey",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }


                /*
                 * ------------------------------------------
                 * COMBINED TIMELINE
                 * ------------------------------------------
                 */

                items(

                    items = progressItems,

                    key = { item ->

                        when (item) {

                            is CBTProgressItem.ActivityCompletion ->
                                "activity_${item.completion.id}"

                            is CBTProgressItem.FiveMinuteStarterCompletion ->
                                "starter_${item.completion.id}"

                            is CBTProgressItem.MindfulMeditationCompletion ->
                                "meditation_${item.completion.id}"
                        }
                    }

                ) { item ->

                    when (item) {

                        is CBTProgressItem.ActivityCompletion -> {

                            ActivityCompletionTimelineItem(
                                completion =
                                    item.completion
                            )
                        }

                        is CBTProgressItem.FiveMinuteStarterCompletion -> {

                            FiveMinuteStarterTimelineItem(
                                completion =
                                    item.completion
                            )
                        }

                        is CBTProgressItem.MindfulMeditationCompletion -> {

                            MindfulMeditationTimelineItem(
                                completion =
                                    item.completion
                            )
                        }
                    }
                }


                /*
                 * ------------------------------------------
                 * ENCOURAGEMENT
                 * ------------------------------------------
                 */

                item {

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    EncouragementCard()
                }
            }
        }
    }
}


/*
 * ======================================================
 * PROGRESS SUMMARY
 * ======================================================
 */

@Composable
private fun ProgressSummaryCard(
    completionCount: Int
) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor = PaleLavender
            )
    ) {

        Row(

            modifier =
                Modifier.padding(20.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(

                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(
                        SoftLavender
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector =
                        Icons.Default.EmojiEvents,

                    contentDescription =
                        null,

                    tint =
                        Lavender,

                    modifier =
                        Modifier.size(30.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.width(16.dp)
            )

            Column {

                Text(

                    text =
                        "$completionCount " +
                                if (completionCount == 1)
                                    "activity completed"
                                else
                                    "activities completed",

                    color =
                        TextPrimary,

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
                        "Every small step is part of your progress.",

                    color =
                        TextSecondary,

                    fontSize =
                        13.sp
                )
            }
        }
    }
}


/*
 * ======================================================
 * ACTIVITY SCHEDULING TIMELINE ITEM
 * ======================================================
 */

@Composable
private fun ActivityCompletionTimelineItem(
    completion: CBTActivityCompletionEntity
) {

    TimelineContainer {

        CompletionCard(
            completion = completion,
            modifier = Modifier.weight(1f)
        )
    }
}


/*
 * ======================================================
 * FIVE-MINUTE STARTER TIMELINE ITEM
 * ======================================================
 */

@Composable
private fun FiveMinuteStarterTimelineItem(
    completion: FiveMinuteStarterCompletionEntity
) {

    TimelineContainer {

        FiveMinuteStarterCompletionCard(
            completion = completion,
            modifier = Modifier.weight(1f)
        )
    }
}


/*
 * ======================================================
 * MINDFUL MEDITATION TIMELINE ITEM
 * ======================================================
 */

@Composable
private fun MindfulMeditationTimelineItem(
    completion: MindfulMeditationCompletionEntity
) {

    TimelineContainer {

        MindfulMeditationCompletionCard(
            completion = completion,
            modifier = Modifier.weight(1f)
        )
    }
}


/*
 * ======================================================
 * TIMELINE CONTAINER
 * ======================================================
 */

@Composable
private fun TimelineContainer(
    content: @Composable RowScope.() -> Unit
) {

    Row(

        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.Top
    ) {

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally
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

                    imageVector =
                        Icons.Default.Check,

                    contentDescription =
                        "Completed",

                    tint =
                        Lavender,

                    modifier =
                        Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            Box(

                modifier = Modifier
                    .width(2.dp)
                    .height(190.dp)
                    .background(
                        SoftLavender
                    )
            )
        }

        Spacer(
            modifier =
                Modifier.width(12.dp)
        )

        content()
    }
}


/*
 * ======================================================
 * ACTIVITY SCHEDULING COMPLETION CARD
 * ======================================================
 */

@Composable
private fun CompletionCard(
    completion: CBTActivityCompletionEntity,
    modifier: Modifier = Modifier
) {

    Card(

        modifier = modifier,

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
                Modifier.padding(17.dp)
        ) {

            Text(

                text =
                    formatDate(
                        completion.completedAt
                    ),

                color =
                    Lavender,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    12.sp
            )

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            Text(

                text =
                    completion.activityTitle,

                color =
                    TextPrimary,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    17.sp
            )

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(

                text =
                    completion.activityName,

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.SemiBold
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            ActivityTypeTag(
                activityType =
                    completion.activityType
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            TimelineDetailRow(
                icon =
                    androidx.compose.material.icons.Icons.Default.TaskAlt,
                text =
                    completion.scheduledWhen
            )

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            TimelineDetailRow(
                icon =
                    androidx.compose.material.icons.Icons.Default.TaskAlt,
                text =
                    completion.scheduledWhere
            )

            if (
                completion.reflection.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )

                ReflectionBox(
                    reflection =
                        completion.reflection
                )
            }
        }
    }
}


/*
 * ======================================================
 * FIVE-MINUTE STARTER COMPLETION CARD
 * ======================================================
 */

@Composable
private fun FiveMinuteStarterCompletionCard(
    completion: FiveMinuteStarterCompletionEntity,
    modifier: Modifier = Modifier
) {

    Card(

        modifier = modifier,

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
                Modifier.padding(17.dp)
        ) {

            Text(

                text =
                    formatDate(
                        completion.completedAt
                    ),

                color =
                    Lavender,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    12.sp
            )

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            Text(

                text =
                    "Five-Minute Starter",

                color =
                    TextPrimary,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    17.sp
            )

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(

                text =
                    completion.task,

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.SemiBold
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Box(

                modifier = Modifier
                    .clip(
                        RoundedCornerShape(50)
                    )
                    .background(
                        SoftTeal
                    )
                    .padding(
                        horizontal = 11.dp,
                        vertical = 6.dp
                    )
            ) {

                Row(

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.SelfImprovement,

                        contentDescription =
                            null,

                        tint =
                            Teal,

                        modifier =
                            Modifier.size(15.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(5.dp)
                    )

                    Text(

                        text =
                            "Behavioral Activation",

                        color =
                            Teal,

                        fontWeight =
                            FontWeight.SemiBold,

                        fontSize =
                            11.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )

            TimelineDetailRow(

                icon =
                    Icons.Default.TaskAlt,

                text =
                    completion.firstStep
            )

            if (
                completion.outcome.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )

                SmallLabel(
                    text =
                        "What happened?"
                )

                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )

                Text(

                    text =
                        completion.outcome,

                    color =
                        TextSecondary,

                    fontSize =
                        13.sp,

                    lineHeight =
                        19.sp
                )
            }

            if (
                completion.reflection.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )

                ReflectionBox(
                    reflection =
                        completion.reflection
                )
            }
        }
    }
}


/*
 * ======================================================
 * MINDFUL MEDITATION COMPLETION CARD
 * ======================================================
 */

@Composable
private fun MindfulMeditationCompletionCard(
    completion: MindfulMeditationCompletionEntity,
    modifier: Modifier = Modifier
) {

    Card(

        modifier = modifier,

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
                Modifier.padding(17.dp)
        ) {

            Text(

                text =
                    formatDate(
                        completion.completedAt
                    ),

                color =
                    Lavender,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    12.sp
            )

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            Text(

                text =
                    "Mindful Meditation",

                color =
                    TextPrimary,

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    17.sp
            )

            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )

            Text(

                text =
                    "Mindfulness",

                color =
                    TextSecondary,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.SemiBold
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            /*
             * Mindfulness category tag
             */

            Box(

                modifier = Modifier
                    .clip(
                        RoundedCornerShape(50)
                    )
                    .background(
                        SoftLavender
                    )
                    .padding(
                        horizontal = 11.dp,
                        vertical = 6.dp
                    )
            ) {

                Row(

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Spa,

                        contentDescription =
                            null,

                        tint =
                            Lavender,

                        modifier =
                            Modifier.size(15.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(5.dp)
                    )

                    Text(

                        text =
                            "Mindfulness",

                        color =
                            Lavender,

                        fontWeight =
                            FontWeight.SemiBold,

                        fontSize =
                            11.sp
                    )
                }
            }

            /*
             * Reflection
             */

            if (
                completion.reflection.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )

                ReflectionBox(
                    reflection =
                        completion.reflection
                )
            }
        }
    }
}


/*
 * ======================================================
 * REFLECTION BOX
 * ======================================================
 */

@Composable
private fun ReflectionBox(
    reflection: String
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(14.dp)
            )
            .background(
                PaleLavender
            )
            .padding(13.dp)
    ) {

        Row(
            verticalAlignment =
                Alignment.Top
        ) {

            Icon(

                imageVector =
                    Icons.Default.SelfImprovement,

                contentDescription =
                    null,

                tint =
                    Lavender,

                modifier =
                    Modifier.size(19.dp)
            )

            Spacer(
                modifier =
                    Modifier.width(9.dp)
            )

            Text(

                text =
                    reflection,

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
 * ======================================================
 * SMALL LABEL
 * ======================================================
 */

@Composable
private fun SmallLabel(
    text: String
) {

    Text(

        text = text,

        color =
            Lavender,

        fontWeight =
            FontWeight.SemiBold,

        fontSize =
            12.sp
    )
}


/*
 * ======================================================
 * ACTIVITY TYPE TAG
 * ======================================================
 */

@Composable
private fun ActivityTypeTag(
    activityType: String
) {

    val isPleasure =
        activityType.contains(
            "Pleasure",
            ignoreCase = true
        )

    val backgroundColor =
        if (isPleasure) {
            SoftRose
        } else {
            SoftTeal
        }

    val contentColor =
        if (isPleasure) {
            Rose
        } else {
            Teal
        }

    Box(

        modifier = Modifier
            .clip(
                RoundedCornerShape(50)
            )
            .background(
                backgroundColor
            )
            .padding(
                horizontal = 11.dp,
                vertical = 6.dp
            )
    ) {

        Row(

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(

                imageVector =
                    if (isPleasure) {
                        Icons.Default.Star
                    } else {
                        Icons.Default.TaskAlt
                    },

                contentDescription =
                    null,

                tint =
                    contentColor,

                modifier =
                    Modifier.size(15.dp)
            )

            Spacer(
                modifier =
                    Modifier.width(5.dp)
            )

            Text(

                text =
                    activityType,

                color =
                    contentColor,

                fontWeight =
                    FontWeight.SemiBold,

                fontSize =
                    11.sp
            )
        }
    }
}


/*
 * ======================================================
 * DETAIL ROW
 * ======================================================
 */

@Composable
private fun TimelineDetailRow(
    icon:
    androidx.compose.ui.graphics.vector.ImageVector,
    text: String
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
                Lavender,

            modifier =
                Modifier.size(17.dp)
        )

        Spacer(
            modifier =
                Modifier.width(8.dp)
        )

        Text(

            text =
                text,

            color =
                TextSecondary,

            fontSize =
                12.sp
        )
    }
}


/*
 * ======================================================
 * EMPTY STATE
 * ======================================================
 */

@Composable
private fun EmptyProgressState(
    modifier: Modifier = Modifier
) {

    Column(

        modifier =
            modifier.padding(28.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Box(

            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape)
                .background(
                    SoftLavender
                ),

            contentAlignment =
                Alignment.Center
        ) {

            Icon(

                imageVector =
                    Icons.Default.SelfImprovement,

                contentDescription =
                    null,

                tint =
                    Lavender,

                modifier =
                    Modifier.size(42.dp)
            )
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        Text(

            text =
                "Your journey starts here",

            color =
                TextPrimary,

            fontWeight =
                FontWeight.Bold,

            fontSize =
                21.sp
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(

            text =
                "Complete a CBT activity and it will appear here. " +
                        "Every small action is worth remembering.",

            color =
                TextSecondary,

            fontSize =
                14.sp,

            lineHeight =
                21.sp
        )
    }
}


/*
 * ======================================================
 * ENCOURAGEMENT
 * ======================================================
 */

@Composable
private fun EncouragementCard() {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(20.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SoftTeal
            )
    ) {

        Row(

            modifier =
                Modifier.padding(17.dp),

            verticalAlignment =
                Alignment.Top
        ) {

            Icon(

                imageVector =
                    Icons.Default.EmojiEvents,

                contentDescription =
                    null,

                tint =
                    Teal,

                modifier =
                    Modifier.size(22.dp)
            )

            Spacer(
                modifier =
                    Modifier.width(10.dp)
            )

            Text(

                text =
                    "Progress isn't about doing everything at once. " +
                            "It's about noticing the small steps you take.",

                color =
                    TextPrimary,

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
 * DATE FORMATTER
 * ======================================================
 */

private fun formatDate(
    timestamp: Long
): String {

    val formatter =
        SimpleDateFormat(
            "EEEE, MMM d • h:mm a",
            Locale.getDefault()
        )

    return formatter.format(
        Date(timestamp)
    )
}