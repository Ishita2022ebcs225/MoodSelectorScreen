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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory

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
    activities: List<CBTActivity>,
    onActivityClick: (CBTActivity) -> Unit,
    modifier: Modifier = Modifier
) {
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            CBTHeader()
        }

        item {
            ProgressCard(
                completedCount = 0,
                totalCount = activities.size
            )
        }

        item {
            SectionHeader(
                title = "Your personalized plan",
                subtitle = "Small steps can make a meaningful difference."
            )
        }

        if (activities.isEmpty()) {

            item {
                EmptyPlanCard()
            }

        } else {

            items(
                items = activities,
                key = { it.id }
            ) { activity ->

                CBTActivityCard(
                    activity = activity,
                    onClick = {
                        onActivityClick(activity)
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            EncouragementCard()
        }
    }
}

@Composable
private fun CBTHeader() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(SoftLavender),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.SelfImprovement,
                    contentDescription = null,
                    tint = DeepLavender,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column {
                Text(
                    text = "Your CBT Plan",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Text(
                    text = "A little time for yourself",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Take things one step at a time. Choose an exercise that feels right for you today.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
        )
    }
}

@Composable
private fun ProgressCard(
    completedCount: Int,
    totalCount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SoftLavender
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.TaskAlt,
                    contentDescription = null,
                    tint = DeepLavender,
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Your progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = if (totalCount == 0) {
                        "Your plan is ready when you are."
                    } else {
                        "$completedCount of $totalCount exercises completed"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            Text(
                text = "$completedCount/$totalCount",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DeepLavender
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}

@Composable
private fun CBTActivityCard(
    activity: CBTActivity,
    onClick: () -> Unit
) {
    val categoryColor = categoryBackground(activity.category)
    val categoryIcon = categoryIcon(activity.category)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(categoryColor),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = categoryIcon,
                    contentDescription = null,
                    tint = DeepLavender,
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(modifier = Modifier.size(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = activity.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                CategoryLabel(
                    category = activity.category
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Open exercise",
                tint = Lavender,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun CategoryLabel(
    category: CBTCategory
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(SoftLavender)
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
    ) {
        Text(
            text = categoryDisplayName(category),
            style = MaterialTheme.typography.labelMedium,
            color = DeepLavender,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun EmptyPlanCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SoftLavender
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Outlined.SelfImprovement,
                contentDescription = null,
                tint = DeepLavender,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your plan is taking shape",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "There are no CBT exercises to show right now.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun EncouragementCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SoftRose
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = DeepLavender,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.size(14.dp))

            Column {
                Text(
                    text = "Be gentle with yourself",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Progress doesn't have to be perfect. Showing up is already a step forward.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

private fun categoryDisplayName(
    category: CBTCategory
): String {
    return when (category) {
        CBTCategory.COGNITIVE -> "Cognitive"
        CBTCategory.BEHAVIORAL -> "Behavioral"
        CBTCategory.MINDFULNESS -> "Mindfulness"
    }
}

private fun categoryBackground(
    category: CBTCategory
): Color {
    return when (category) {
        CBTCategory.COGNITIVE -> SoftPeriwinkle
        CBTCategory.BEHAVIORAL -> SoftRose
        CBTCategory.MINDFULNESS -> SoftTeal
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