package com.example.moodselector.presentations.cbt.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.presentations.cbt.progress.CBTProgressViewModel

private val LavenderBackground = Color(0xFFF8F4FC)
private val SoftLavender = Color(0xFFE9DDF4)
private val Lavender = Color(0xFFB99ACB)
private val DeepLavender = Color(0xFF765A86)
private val SoftRose = Color(0xFFF3DDE6)
private val SoftTeal = Color(0xFFDCEEEB)
private val SoftPeriwinkle = Color(0xFFE1E3F5)
private val TextPrimary = Color(0xFF443A48)
private val TextSecondary = Color(0xFF766B7A)

@Composable
fun CBTHomeScreen(
    onActivityClick: (CBTActivity) -> Unit,
    onProgressClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CBTViewModel = hiltViewModel(),
    progressViewModel: CBTProgressViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val completedCount by progressViewModel
        .uniqueCompletedExerciseCount
        .collectAsStateWithLifecycle(
            initialValue = 0
        )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LavenderBackground),

        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 24.dp,
            bottom = 32.dp
        ),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        /*
         * --------------------------------------------------
         * HEADER
         * --------------------------------------------------
         */

        item {
            CBTHeader()
        }

        /*
         * --------------------------------------------------
         * CURRENT PROGRESS SUMMARY
         * --------------------------------------------------
         */

        item {
            ProgressCard(
                completedCount = completedCount,
                totalCount = uiState.allActivities.size
            )
        }

        /*
         * --------------------------------------------------
         * DETAILED PROGRESS / TIMELINE
         * --------------------------------------------------
         */

        item {
            ProgressTimelineCard(
                completedCount = completedCount,
                onClick = onProgressClick
            )
        }

        /*
         * --------------------------------------------------
         * PERSONALIZED CBT PLAN
         * --------------------------------------------------
         */

        item {
            SectionHeader(
                title = "Your personalized plan",
                subtitle =
                    "Exercises selected based on your assessment."
            )
        }

        when {

            uiState.isLoading -> {

                item {
                    LoadingPlanCard()
                }
            }

            !uiState.hasAssessmentResult -> {

                item {
                    NoAssessmentCard()
                }
            }

            uiState.activities.isEmpty() -> {

                item {
                    EmptyPlanCard()
                }
            }

            else -> {

                items(
                    items = uiState.activities,
                    key = { "recommended_${it.id}" }
                ) { activity ->

                    CBTActivityCard(
                        activity = activity,
                        onClick = {
                            /*
                             * Always forward the complete CBTActivity.
                             *
                             * AppNavHost is responsible for deciding
                             * which screen belongs to this activity ID.
                             */
                            onActivityClick(activity)
                        }
                    )
                }
            }
        }

        /*
         * --------------------------------------------------
         * ALL CBT ACTIVITIES
         * --------------------------------------------------
         */

        item {

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            SectionHeader(
                title = "Explore CBT activities",
                subtitle =
                    "You can choose any exercise that feels right for you."
            )
        }

        items(
            items = uiState.allActivities,
            key = { "all_${it.id}" }
        ) { activity ->

            CBTActivityCard(
                activity = activity,
                onClick = {
                    /*
                     * Same navigation path as personalized activities.
                     */
                    onActivityClick(activity)
                }
            )
        }

        /*
         * --------------------------------------------------
         * ENCOURAGEMENT
         * --------------------------------------------------
         */

        item {

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            EncouragementCard()
        }
    }
}


/*
 * ==========================================================
 * HEADER
 * ==========================================================
 */

@Composable
private fun CBTHeader() {

    Column(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(
                        SoftLavender
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Outlined.SelfImprovement,

                    contentDescription = null,

                    tint =
                        DeepLavender,

                    modifier =
                        Modifier.size(28.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.size(14.dp)
            )

            Column {

                Text(
                    text = "Your CBT Plan",

                    style =
                        MaterialTheme.typography.headlineSmall,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        TextPrimary
                )

                Text(
                    text =
                        "A little time for yourself",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        TextSecondary
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(18.dp)
        )

        Text(
            text =
                "Take things one step at a time. " +
                        "Choose an exercise that feels right for you today.",

            style =
                MaterialTheme.typography.bodyLarge,

            color =
                TextSecondary,

            lineHeight =
                MaterialTheme.typography.bodyLarge.lineHeight
        )
    }
}


/*
 * ==========================================================
 * PROGRESS SUMMARY
 * ==========================================================
 */

@Composable
private fun ProgressCard(
    completedCount: Int,
    totalCount: Int
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SoftLavender
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
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
                        Icons.Outlined.TaskAlt,

                    contentDescription = null,

                    tint =
                        DeepLavender,

                    modifier =
                        Modifier.size(25.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.size(14.dp)
            )

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        "Your progress",

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        TextPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )

                Text(
                    text =
                        if (completedCount == 0) {
                            "Your journey starts when you are ready."
                        } else {
                            "$completedCount " +
                                    if (completedCount == 1)
                                        "activity completed"
                                    else
                                        "activities completed"
                        },

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        TextSecondary
                )
            }

            Text(
                text =
                    "$completedCount/$totalCount",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.Bold,

                color =
                    DeepLavender
            )
        }
    }
}


/*
 * ==========================================================
 * PROGRESS TIMELINE BUTTON
 * ==========================================================
 */

@Composable
private fun ProgressTimelineCard(
    completedCount: Int,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(
                        SoftTeal
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Outlined.EmojiEvents,

                    contentDescription = null,

                    tint =
                        DeepLavender,

                    modifier =
                        Modifier.size(27.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.size(14.dp)
            )

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        "Your CBT journey",

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        TextPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text =
                        if (completedCount == 0) {
                            "Complete an activity to start your timeline."
                        } else {
                            "View your completed activities and reflections."
                        },

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        TextSecondary
                )
            }

            Icon(
                imageVector =
                    Icons.Outlined.CheckCircle,

                contentDescription =
                    "View progress",

                tint =
                    Lavender,

                modifier =
                    Modifier.size(23.dp)
            )
        }
    }
}


/*
 * ==========================================================
 * SECTION HEADER
 * ==========================================================
 */

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String
) {

    Column {

        Text(
            text =
                title,

            style =
                MaterialTheme.typography.titleLarge,

            fontWeight =
                FontWeight.SemiBold,

            color =
                TextPrimary
        )

        Spacer(
            modifier =
                Modifier.height(4.dp)
        )

        Text(
            text =
                subtitle,

            style =
                MaterialTheme.typography.bodyMedium,

            color =
                TextSecondary
        )
    }
}


/*
 * ==========================================================
 * CBT ACTIVITY CARD
 * ==========================================================
 */

@Composable
private fun CBTActivityCard(
    activity: CBTActivity,
    onClick: () -> Unit
) {

    val categoryColor =
        categoryBackground(
            activity.category
        )

    val categoryIcon =
        categoryIcon(
            activity.category
        )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),

        shape =
            RoundedCornerShape(22.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White
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
                    .padding(18.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(52.dp)
                        .clip(
                            RoundedCornerShape(17.dp)
                        )
                        .background(
                            categoryColor
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        categoryIcon,

                    contentDescription = null,

                    tint =
                        DeepLavender,

                    modifier =
                        Modifier.size(27.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.size(15.dp)
            )

            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        activity.title,

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        TextPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )

                Text(
                    text =
                        activity.description,

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        TextSecondary,

                    maxLines = 3
                )

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )

                CategoryLabel(
                    category =
                        activity.category
                )
            }

            Spacer(
                modifier =
                    Modifier.size(8.dp)
            )

            Icon(
                imageVector =
                    Icons.Outlined.FavoriteBorder,

                contentDescription =
                    "Open exercise",

                tint =
                    Lavender,

                modifier =
                    Modifier.size(22.dp)
            )
        }
    }
}


/*
 * ==========================================================
 * CATEGORY LABEL
 * ==========================================================
 */

@Composable
private fun CategoryLabel(
    category: CBTCategory
) {

    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(50)
            )
            .background(
                SoftLavender
            )
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
    ) {

        Text(
            text =
                categoryDisplayName(
                    category
                ),

            style =
                MaterialTheme.typography.labelMedium,

            color =
                DeepLavender,

            fontWeight =
                FontWeight.Medium
        )
    }
}


/*
 * ==========================================================
 * LOADING
 * ==========================================================
 */

@Composable
private fun LoadingPlanCard() {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SoftLavender
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(28.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.SelfImprovement,

                contentDescription = null,

                tint =
                    DeepLavender,

                modifier =
                    Modifier.size(40.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text =
                    "Preparing your plan...",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    TextPrimary
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            Text(
                text =
                    "We're personalizing your exercises.",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    TextSecondary
            )
        }
    }
}


/*
 * ==========================================================
 * NO ASSESSMENT
 * ==========================================================
 */

@Composable
private fun NoAssessmentCard() {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SoftLavender
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(28.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.SelfImprovement,

                contentDescription = null,

                tint =
                    DeepLavender,

                modifier =
                    Modifier.size(40.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text =
                    "Complete your assessment first",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    TextPrimary
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            Text(
                text =
                    "Your personalized CBT plan will appear here after your assessment.",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    TextSecondary
            )
        }
    }
}


/*
 * ==========================================================
 * EMPTY PLAN
 * ==========================================================
 */

@Composable
private fun EmptyPlanCard() {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SoftLavender
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(28.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.SelfImprovement,

                contentDescription = null,

                tint =
                    DeepLavender,

                modifier =
                    Modifier.size(40.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(
                text =
                    "Your plan is taking shape",

                style =
                    MaterialTheme.typography.titleMedium,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    TextPrimary
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            Text(
                text =
                    "There are no CBT exercises to show right now.",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    TextSecondary
            )
        }
    }
}


/*
 * ==========================================================
 * ENCOURAGEMENT
 * ==========================================================
 */

@Composable
private fun EncouragementCard() {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SoftRose
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
    ) {

        Row(
            modifier =
                Modifier.padding(20.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector =
                    Icons.Outlined.AutoAwesome,

                contentDescription = null,

                tint =
                    DeepLavender,

                modifier =
                    Modifier.size(28.dp)
            )

            Spacer(
                modifier =
                    Modifier.size(14.dp)
            )

            Column {

                Text(
                    text =
                        "Be gentle with yourself",

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        TextPrimary
                )

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )

                Text(
                    text =
                        "Progress doesn't have to be perfect. " +
                                "Showing up is already a step forward.",

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        TextSecondary
                )
            }
        }
    }
}


/*
 * ==========================================================
 * CATEGORY HELPERS
 * ==========================================================
 */

private fun categoryDisplayName(
    category: CBTCategory
): String {

    return when (category) {

        CBTCategory.COGNITIVE ->
            "Cognitive"

        CBTCategory.BEHAVIORAL ->
            "Behavioral"

        CBTCategory.MINDFULNESS ->
            "Mindfulness"
    }
}

private fun categoryBackground(
    category: CBTCategory
): Color {

    return when (category) {

        CBTCategory.COGNITIVE ->
            SoftPeriwinkle

        CBTCategory.BEHAVIORAL ->
            SoftRose

        CBTCategory.MINDFULNESS ->
            SoftTeal
    }
}

private fun categoryIcon(
    category: CBTCategory
) = when (category) {

    CBTCategory.COGNITIVE ->
        Icons.Outlined.AutoAwesome

    CBTCategory.BEHAVIORAL ->
        Icons.Outlined.CheckCircle

    CBTCategory.MINDFULNESS ->
        Icons.Outlined.SelfImprovement
}

