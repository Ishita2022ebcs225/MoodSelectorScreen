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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.moodselector.data.local.entity.ABCModelCompletionEntity
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.data.local.entity.Grounding54321CompletionEntity
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import com.example.moodselector.data.local.entity.SelfCompassionReflectionCompletionEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Lavender = Color(0xFF6C63FF)

private val SoftLavender: Color
    @Composable
    get() = MaterialTheme.colorScheme.surfaceVariant

private val PaleLavender: Color
    @Composable
    get() = MaterialTheme.colorScheme.surface

private val SoftRose = Color(0xFFFFEEF4)
private val Rose = Color(0xFFE88BA5)

private val SoftTeal = Color(0xFFE8F7F5)
private val Teal = Color(0xFF4BA89C)

private val TextPrimary: Color
    @Composable
    get() = MaterialTheme.colorScheme.onBackground

private val TextSecondary: Color
    @Composable
    get() = MaterialTheme.colorScheme.onSurfaceVariant

private val Background: Color
    @Composable
    get() = MaterialTheme.colorScheme.background

private val SurfaceWhite: Color
    @Composable
    get() = MaterialTheme.colorScheme.surface


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CBTProgressScreen(
    onBackClick: () -> Unit,
    viewModel: CBTProgressViewModel =
        hiltViewModel()
) {

    val progressItems by
    viewModel.progressItems
        .collectAsStateWithLifecycle(
            initialValue = emptyList()
        )

    val completionCount by
    viewModel.uniqueCompletedExerciseCount
        .collectAsStateWithLifecycle(
            initialValue = 0
        )

    Scaffold(

        containerColor =
            Background,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text =
                                "Your Progress",

                            fontWeight =
                                FontWeight.Bold,

                            fontSize =
                                19.sp,

                            color =
                                TextPrimary
                        )

                        Text(
                            text =
                                "Your CBT journey",

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
                        containerColor =
                            Background
                    )
            )
        }

    ) { paddingValues ->

        if (progressItems.isEmpty()) {

            EmptyProgressState(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            paddingValues
                        )
            )

        } else {

            LazyColumn(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            paddingValues
                        ),

                contentPadding =
                    PaddingValues(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(
                        18.dp
                    )
            ) {

                item {

                    ProgressSummaryCard(
                        completionCount =
                            completionCount
                    )
                }

                item {

                    Text(
                        text =
                            "Your Journey",

                        color =
                            TextPrimary,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            18.sp
                    )
                }

                items(

                    items =
                        progressItems,

                    key = { item ->

                        when (item) {

                            is CBTProgressItem.ActivityCompletion ->
                                "activity_${item.completion.id}"

                            is CBTProgressItem.ABCModelCompletion ->
                                "abc_${item.completion.id}"

                            is CBTProgressItem.FiveMinuteStarterCompletion ->
                                "starter_${item.completion.id}"

                            is CBTProgressItem.MindfulMeditationCompletion ->
                                "meditation_${item.completion.id}"

                            is CBTProgressItem.Grounding54321Completion ->
                                "grounding54321_${item.completion.id}"

                            is CBTProgressItem.SelfCompassionReflectionCompletion ->
                                "self_compassion_${item.completion.id}"
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

                        is CBTProgressItem.ABCModelCompletion -> {

                            ABCModelTimelineItem(
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

                        is CBTProgressItem.Grounding54321Completion -> {

                            Grounding54321TimelineItem(
                                completion =
                                    item.completion
                            )
                        }

                        is CBTProgressItem.SelfCompassionReflectionCompletion -> {

                            SelfCompassionReflectionTimelineItem(
                                completion =
                                    item.completion
                            )
                        }
                    }
                }

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
                containerColor =
                    PaleLavender
            )
    ) {

        Row(

            modifier =
                Modifier.padding(20.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(

                modifier =
                    Modifier
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
                                if (
                                    completionCount == 1
                                ) {
                                    "activity completed"
                                } else {
                                    "activities completed"
                                },

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


@Composable
private fun ActivityCompletionTimelineItem(
    completion:
    CBTActivityCompletionEntity
) {

    TimelineContainer {

        CompletionCard(
            completion =
                completion,

            modifier =
                Modifier.weight(1f)
        )
    }
}


@Composable
private fun ABCModelTimelineItem(
    completion:
    ABCModelCompletionEntity
) {

    TimelineContainer {

        ABCModelCompletionCard(
            completion =
                completion,

            modifier =
                Modifier.weight(1f)
        )
    }
}


@Composable
private fun FiveMinuteStarterTimelineItem(
    completion:
    FiveMinuteStarterCompletionEntity
) {

    TimelineContainer {

        FiveMinuteStarterCompletionCard(
            completion =
                completion,

            modifier =
                Modifier.weight(1f)
        )
    }
}


@Composable
private fun MindfulMeditationTimelineItem(
    completion:
    MindfulMeditationCompletionEntity
) {

    TimelineContainer {

        MindfulMeditationCompletionCard(
            completion =
                completion,

            modifier =
                Modifier.weight(1f)
        )
    }
}


@Composable
private fun Grounding54321TimelineItem(
    completion:
    Grounding54321CompletionEntity
) {

    TimelineContainer {

        Grounding54321CompletionCard(
            completion =
                completion,

            modifier =
                Modifier.weight(1f)
        )
    }
}


@Composable
private fun SelfCompassionReflectionTimelineItem(
    completion:
    SelfCompassionReflectionCompletionEntity
) {

    TimelineContainer {

        SelfCompassionReflectionCompletionCard(
            completion =
                completion,

            modifier =
                Modifier.weight(1f)
        )
    }
}


@Composable
private fun TimelineContainer(
    content:
    @Composable RowScope.() -> Unit
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

                modifier =
                    Modifier
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

                modifier =
                    Modifier
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


@Composable
private fun CompletionCard(
    completion:
    CBTActivityCompletionEntity,

    modifier:
    Modifier = Modifier
) {

    Card(

        modifier =
            modifier,

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
                    Icons.Default.TaskAlt,

                text =
                    completion.scheduledWhen
            )

            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )

            TimelineDetailRow(
                icon =
                    Icons.Default.TaskAlt,

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


@Composable
private fun ABCModelCompletionCard(
    completion:
    ABCModelCompletionEntity,

    modifier:
    Modifier = Modifier
) {

    Card(

        modifier =
            modifier,

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
                    "ABC Model",

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
                    "Cognitive",

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

                modifier =
                    Modifier
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
                            Icons.Default.Visibility,

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
                            "ABC Model",

                        color =
                            Lavender,

                        fontWeight =
                            FontWeight.SemiBold,

                        fontSize =
                            11.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(15.dp)
            )

            ResponseSummary(
                label =
                    "A — Activating Event",

                response =
                    completion.activatingEvent
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            ResponseSummary(
                label =
                    "B — Beliefs",

                response =
                    completion.beliefs
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            ResponseSummary(
                label =
                    "C — Consequences",

                response =
                    completion.consequences
            )
        }
    }
}


@Composable
private fun FiveMinuteStarterCompletionCard(
    completion:
    FiveMinuteStarterCompletionEntity,

    modifier:
    Modifier = Modifier
) {

    Card(

        modifier =
            modifier,

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

                modifier =
                    Modifier
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


@Composable
private fun MindfulMeditationCompletionCard(
    completion:
    MindfulMeditationCompletionEntity,

    modifier:
    Modifier = Modifier
) {

    Card(

        modifier =
            modifier,

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

            Box(

                modifier =
                    Modifier
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


@Composable
private fun Grounding54321CompletionCard(
    completion:
    Grounding54321CompletionEntity,

    modifier:
    Modifier = Modifier
) {

    Card(

        modifier =
            modifier,

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
                    "5-4-3-2-1 Grounding",

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

            Box(

                modifier =
                    Modifier
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
                            Icons.Default.Visibility,

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
                            "Grounding",

                        color =
                            Lavender,

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

            if (
                completion.reflection.isNotBlank()
            ) {

                ReflectionBox(
                    reflection =
                        completion.reflection
                )
            }
        }
    }
}


@Composable
private fun SelfCompassionReflectionCompletionCard(
    completion:
    SelfCompassionReflectionCompletionEntity,

    modifier:
    Modifier = Modifier
) {

    Card(

        modifier =
            modifier,

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
                    "Self-Compassion Reflection",

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

            Box(

                modifier =
                    Modifier
                        .clip(
                            RoundedCornerShape(50)
                        )
                        .background(
                            SoftRose
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
                            Icons.Default.Favorite,

                        contentDescription =
                            null,

                        tint =
                            Rose,

                        modifier =
                            Modifier.size(15.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(5.dp)
                    )

                    Text(

                        text =
                            "Self-Compassion",

                        color =
                            Rose,

                        fontWeight =
                            FontWeight.SemiBold,

                        fontSize =
                            11.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(15.dp)
            )

            ResponseSummary(
                label =
                    "Situation",

                response =
                    completion.situation
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            ResponseSummary(
                label =
                    "What I would say to a friend",

                response =
                    completion.friendResponse
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            ResponseSummary(
                label =
                    "What I can say to myself",

                response =
                    completion.selfCompassionResponse
            )
        }
    }
}


@Composable
private fun ResponseSummary(
    label: String,
    response: String
) {

    Column {

        Text(
            text =
                label,

            color =
                Lavender,

            fontWeight =
                FontWeight.SemiBold,

            fontSize =
                12.sp
        )

        Spacer(
            modifier =
                Modifier.height(5.dp)
        )

        Box(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(14.dp)
                    )
                    .background(
                        PaleLavender
                    )
                    .padding(12.dp)
        ) {

            Text(

                text =
                    if (response.isBlank()) {
                        "No response recorded."
                    } else {
                        response
                    },

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


@Composable
private fun ReflectionBox(
    reflection: String
) {

    Box(

        modifier =
            Modifier
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


@Composable
private fun SmallLabel(
    text: String
) {

    Text(

        text =
            text,

        color =
            Lavender,

        fontWeight =
            FontWeight.SemiBold,

        fontSize =
            12.sp
    )
}


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

        modifier =
            Modifier
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


@Composable
private fun EmptyProgressState(
    modifier:
    Modifier = Modifier
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

            modifier =
                Modifier
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


@Composable
private fun EncouragementCard() {

    val isDarkTheme =
        androidx.compose.foundation.isSystemInDarkTheme()

    val cardBackground =
        if (isDarkTheme) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            SoftTeal
        }

    val cardTextColor =
        if (isDarkTheme) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            TextPrimary
        }

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(20.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    cardBackground
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
                    cardTextColor,

                fontSize =
                    13.sp,

                lineHeight =
                    19.sp
            )
        }
    }
}


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