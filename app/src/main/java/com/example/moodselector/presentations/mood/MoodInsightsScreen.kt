package com.example.moodselector.presentations.mood

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodselector.R
import com.example.moodselector.notifications.NotificationHelper
import com.example.moodselector.presentations.auth.AuthViewModel
import kotlinx.coroutines.launch

data class MoodOption(
    val label: String,
    val emoji: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodInsightsScreen(
    onSettingsClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onAssessmentResultsClick: () -> Unit = {},
    onAssessmentClick: () -> Unit = {},
    onReadingClick: () -> Unit = {},
    onRealStoriesClick: () -> Unit = {},
    viewModel: MoodViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val moods by viewModel.moodList.collectAsState()

    val authUiState by
    authViewModel.uiState.collectAsStateWithLifecycle()

    val displayName =
        authUiState.user
            ?.displayName
            ?.trim()
            ?.takeIf { it.isNotBlank() }
            ?: "there"

    /*
     * --------------------------------------------------
     * ASSESSMENT STATUS
     * --------------------------------------------------
     */

    val assessmentCompleted by
    viewModel.assessmentCompleted.collectAsStateWithLifecycle()

    /*
     * --------------------------------------------------
     * DRAWER
     * --------------------------------------------------
     */

    val drawerState =
        rememberDrawerState(
            initialValue = DrawerValue.Closed
        )

    val scope =
        rememberCoroutineScope()

    /*
     * --------------------------------------------------
     * COLORS
     * --------------------------------------------------
     */

    val darkPurple =
        Color(0xFF6E63A8)

    val primaryPurple =
        Color(0xFF8F84C7)

    val softLavender =
        MaterialTheme.colorScheme.surfaceVariant

    val mutedRose =
        if (MaterialTheme.colorScheme.background.luminance() > 0.5f) {
            Color(0xFFF1E2E8)
        } else {
            Color(0xFF3A2D38)
        }

    val mistBlue =
        if (MaterialTheme.colorScheme.background.luminance() > 0.5f) {
            Color(0xFFE2EBF2)
        } else {
            Color(0xFF29343D)
        }

    val glassWhite =
        if (MaterialTheme.colorScheme.background.luminance() > 0.5f) {
            Color(0xEAF9F6FC)
        } else {
            Color(0xE82B2633)
        }

    val textDark =
        MaterialTheme.colorScheme.onSurface

    val secondaryText =
        MaterialTheme.colorScheme.onSurfaceVariant

    /*
     * --------------------------------------------------
     * NOTIFICATIONS
     * --------------------------------------------------
     */

    val context =
        LocalContext.current

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                NotificationHelper.showNotification(
                    context = context,
                    title = "HerMind",
                    body = "Your notifications are now enabled."
                )
            }
        }

    fun handleNotificationClick() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            val permissionGranted =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) ==
                        PackageManager.PERMISSION_GRANTED

            if (permissionGranted) {

                NotificationHelper.showNotification(
                    context = context,
                    title = "HerMind",
                    body = "Your notifications are enabled."
                )

            } else {

                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

        } else {

            NotificationHelper.showNotification(
                context = context,
                title = "HerMind",
                body = "Your notifications are now enabled."
            )
        }
    }

    /*
     * --------------------------------------------------
     * MOOD INPUT
     * --------------------------------------------------
     */

    var moodText by remember {
        mutableStateOf("")
    }

    var triggerText by remember {
        mutableStateOf("")
    }

    var selectedMood by remember {
        mutableStateOf("Happy")
    }

    val moodOptions =
        listOf(
            MoodOption(
                label = "Happy",
                emoji = "🤩"
            ),
            MoodOption(
                label = "Calm",
                emoji = "😌"
            ),
            MoodOption(
                label = "Neutral",
                emoji = "😐"
            ),
            MoodOption(
                label = "Sad",
                emoji = "🥺"
            ),
            MoodOption(
                label = "Angry",
                emoji = "😤"
            )
        )

    /*
     * --------------------------------------------------
     * MOOD CALCULATIONS
     * --------------------------------------------------
     */

    val scores =
        moods.map {

            when (it.emoji) {

                "Happy",
                "😊",
                "🤩" -> 5

                "Calm",
                "😌" -> 4

                "Neutral",
                "😐" -> 3

                "Sad",
                "😔",
                "🥺" -> 2

                "Angry",
                "😡",
                "😤" -> 1

                else -> 3
            }
        }

    val averageMood =
        if (scores.isNotEmpty()) {
            scores.average()
        } else {
            0.0
        }

    val insight =
        when {

            averageMood >= 4 ->
                "Your emotional wellness is improving beautifully ✨"

            averageMood >= 3 ->
                "Your emotional state has remained balanced 🌸"

            else ->
                "You've been emotionally overwhelmed lately 💙"
        }

    /*
     * ==================================================
     * SIDEBAR
     * ==================================================
     */

    ModalNavigationDrawer(

        drawerState =
            drawerState,

        drawerContent = {

            ModalDrawerSheet(

                drawerContainerColor =
                    MaterialTheme.colorScheme.surface,

                drawerShape =
                    RoundedCornerShape(
                        topEnd = 28.dp,
                        bottomEnd = 28.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .padding(
                                horizontal = 18.dp
                            )
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )

                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column {

                            Text(
                                text =
                                    "HerMind",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    darkPurple
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(2.dp)
                            )

                            Text(
                                text =
                                    "Your wellbeing space",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    secondaryText
                            )
                        }

                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Close,

                                contentDescription =
                                    "Close sidebar",

                                tint =
                                    secondaryText
                            )
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(28.dp)
                    )

                    HorizontalDivider(
                        color =
                            MaterialTheme.colorScheme.outlineVariant
                    )

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )

                    NavigationDrawerItem(

                        label = {
                            Text(
                                text =
                                    "Assessment Results",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyLarge,

                                fontWeight =
                                    FontWeight.Medium
                            )
                        },

                        selected = false,

                        onClick = {

                            scope.launch {
                                drawerState.close()
                            }

                            onAssessmentResultsClick()
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Assessment,

                                contentDescription =
                                    null
                            )
                        },

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            NavigationDrawerItemDefaults
                                .colors(
                                    unselectedIconColor =
                                        darkPurple,

                                    unselectedTextColor =
                                        textDark,

                                    unselectedContainerColor =
                                        Color.Transparent
                                )
                    )

                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )

                    NavigationDrawerItem(

                        label = {
                            Text(
                                text =
                                    "Settings",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyLarge,

                                fontWeight =
                                    FontWeight.Medium
                            )
                        },

                        selected = false,

                        onClick = {

                            scope.launch {
                                drawerState.close()
                            }

                            onSettingsClick()
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Settings,

                                contentDescription =
                                    null
                            )
                        },

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            NavigationDrawerItemDefaults
                                .colors(
                                    unselectedIconColor =
                                        darkPurple,

                                    unselectedTextColor =
                                        textDark,

                                    unselectedContainerColor =
                                        Color.Transparent
                                )
                    )

                    Spacer(
                        modifier =
                            Modifier.weight(1f)
                    )

                    HorizontalDivider(
                        color =
                            MaterialTheme.colorScheme.outlineVariant
                    )

                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )

                    NavigationDrawerItem(

                        label = {
                            Text(
                                text =
                                    "Log Out",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyLarge,

                                fontWeight =
                                    FontWeight.Medium
                            )
                        },

                        selected = false,

                        onClick = {

                            scope.launch {
                                drawerState.close()
                            }

                            authViewModel.signOut()
                            onLogout()
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Logout,

                                contentDescription =
                                    null
                            )
                        },

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            NavigationDrawerItemDefaults
                                .colors(
                                    unselectedIconColor =
                                        Color(0xFFB44A5A),

                                    unselectedTextColor =
                                        Color(0xFFB44A5A),

                                    unselectedContainerColor =
                                        Color.Transparent
                                )
                    )

                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )
                }
            }
        }
    ) {

        Box(
            modifier =
                Modifier.fillMaxSize()
        ) {

            Image(
                painter =
                    painterResource(
                        id = R.drawable.lavender_scenery
                    ),

                contentDescription =
                    null,

                modifier =
                    Modifier.fillMaxSize(),

                contentScale =
                    ContentScale.Crop
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0x30000000),
                                    Color(0x22000000),
                                    Color(0x55000000)
                                )
                            )
                        )
            )

            LazyColumn(

                modifier =
                    Modifier.fillMaxSize(),

                contentPadding =
                    PaddingValues(
                        bottom = 4.dp
                    ),

                verticalArrangement =
                    Arrangement.spacedBy(
                        16.dp
                    )
            ) {

                /*
                 * ==================================================
                 * HEADER
                 * ==================================================
                 */

                item {

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                )
                                .clip(
                                    RoundedCornerShape(
                                        28.dp
                                    )
                                )
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            darkPurple.copy(
                                                alpha = 0.82f
                                            ),
                                            primaryPurple.copy(
                                                alpha = 0.62f
                                            )
                                        )
                                    )
                                )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 18.dp
                                )
                        ) {

                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween,

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Row(
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Surface(
                                        modifier =
                                            Modifier
                                                .size(42.dp)
                                                .clickable {

                                                    scope.launch {
                                                        drawerState.open()
                                                    }
                                                },

                                        shape =
                                            CircleShape,

                                        color =
                                            Color.White.copy(
                                                alpha = 0.18f
                                            )
                                    ) {

                                        Box(
                                            contentAlignment =
                                                Alignment.Center
                                        ) {

                                            Icon(
                                                imageVector =
                                                    Icons.Default.Menu,

                                                contentDescription =
                                                    "Open sidebar",

                                                tint =
                                                    Color.White,

                                                modifier =
                                                    Modifier.size(
                                                        21.dp
                                                    )
                                            )
                                        }
                                    }

                                    Spacer(
                                        modifier =
                                            Modifier.width(12.dp)
                                    )

                                    Column {

                                        Text(
                                            text =
                                                "Hello, $displayName 🌸",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .titleLarge,

                                            fontWeight =
                                                FontWeight.Bold,

                                            color =
                                                Color.White
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.height(3.dp)
                                        )

                                        Text(
                                            text =
                                                "Track your emotional wellbeing",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodySmall,

                                            color =
                                                Color.White.copy(
                                                    alpha = 0.88f
                                                )
                                        )
                                    }
                                }

                                Surface(
                                    modifier =
                                        Modifier
                                            .size(42.dp)
                                            .clickable {
                                                handleNotificationClick()
                                            },

                                    shape =
                                        CircleShape,

                                    color =
                                        Color.White.copy(
                                            alpha = 0.18f
                                        )
                                ) {

                                    Box(
                                        contentAlignment =
                                            Alignment.Center
                                    ) {

                                        Icon(
                                            imageVector =
                                                Icons.Default.Notifications,

                                            contentDescription =
                                                "Notifications",

                                            tint =
                                                Color.White,

                                            modifier =
                                                Modifier.size(
                                                    21.dp
                                                )
                                        )
                                    }
                                }
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(22.dp)
                            )

                            Row(
                                verticalAlignment =
                                    Alignment.Bottom
                            ) {

                                Text(
                                    text =
                                        String.format(
                                            "%.1f",
                                            averageMood
                                        ),

                                    style =
                                        MaterialTheme
                                            .typography
                                            .displaySmall,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        Color.White
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )

                                Text(
                                    text =
                                        "/ 5.0",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodyMedium,

                                    color =
                                        Color.White.copy(
                                            alpha = 0.78f
                                        ),

                                    modifier =
                                        Modifier.padding(
                                            bottom = 5.dp
                                        )
                                )
                            }

                            Text(
                                text =
                                    "Current emotional score",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    Color.White.copy(
                                        alpha = 0.85f
                                    )
                            )
                        }
                    }
                }

                /*
                 * ==================================================
                 * ASSESSMENT PROMPT
                 * ==================================================
                 */

                if (!assessmentCompleted) {

                    item {

                        Card(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    )
                                    .clickable {
                                        onAssessmentClick()
                                    },

                            shape =
                                RoundedCornerShape(
                                    22.dp
                                ),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        MaterialTheme
                                            .colorScheme
                                            .primaryContainer
                                            .copy(
                                                alpha = 0.92f
                                            )
                                ),

                            elevation =
                                CardDefaults.cardElevation(
                                    defaultElevation = 3.dp
                                )
                        ) {

                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 18.dp,
                                            vertical = 16.dp
                                        ),

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier =
                                        Modifier
                                            .size(46.dp)
                                            .clip(
                                                CircleShape
                                            )
                                            .background(
                                                MaterialTheme
                                                    .colorScheme
                                                    .primary
                                                    .copy(
                                                        alpha = 0.14f
                                                    )
                                            ),

                                    contentAlignment =
                                        Alignment.Center
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Assessment,

                                        contentDescription =
                                            null,

                                        tint =
                                            MaterialTheme
                                                .colorScheme
                                                .primary,

                                        modifier =
                                            Modifier.size(
                                                23.dp
                                            )
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
                                            "Take the assessment for a personalized plan",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleMedium,

                                        fontWeight =
                                            FontWeight.Bold,

                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .onPrimaryContainer
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(4.dp)
                                    )

                                    Text(
                                        text =
                                            "Complete the assessment to receive CBT-based recommendations tailored to you.",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall,

                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .onPrimaryContainer
                                                .copy(
                                                    alpha = 0.78f
                                                )
                                    )
                                }

                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )

                                Icon(
                                    imageVector =
                                        Icons.Default.ChevronRight,

                                    contentDescription =
                                        "Take assessment",

                                    tint =
                                        MaterialTheme
                                            .colorScheme
                                            .primary
                                )
                            }
                        }
                    }
                }

                /*
                 * ==================================================
                 * SUMMARY
                 * ==================================================
                 */

                item {

                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                ),

                        shape =
                            RoundedCornerShape(
                                22.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    glassWhite
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            )
                    ) {

                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 18.dp,
                                        vertical = 15.dp
                                    ),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Column {

                                Text(
                                    text =
                                        "Average Mood",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodySmall,

                                    color =
                                        secondaryText
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(3.dp)
                                )

                                Text(
                                    text =
                                        String.format(
                                            "%.1f / 5.0",
                                            averageMood
                                        ),

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        textDark
                                )
                            }

                            Column(
                                horizontalAlignment =
                                    Alignment.End
                            ) {

                                Text(
                                    text =
                                        "Trend",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodySmall,

                                    color =
                                        secondaryText
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(3.dp)
                                )

                                Text(
                                    text =
                                        when {

                                            averageMood >= 4 ->
                                                "Positive"

                                            averageMood >= 3 ->
                                                "Stable"

                                            else ->
                                                "Low"
                                        },

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        darkPurple
                                )
                            }
                        }
                    }
                }

                /*
                 * ==================================================
                 * MOOD PICKER
                 * ==================================================
                 */

                item {

                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                ),

                        shape =
                            RoundedCornerShape(
                                24.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    softLavender.copy(
                                        alpha = 0.90f
                                    )
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    horizontal = 18.dp,
                                    vertical = 18.dp
                                )
                        ) {

                            Text(
                                text =
                                    "How do you feel today?",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    textDark
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(18.dp)
                            )

                            Row(
                                modifier =
                                    Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween,

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                moodOptions.forEach { mood ->

                                    val selected =
                                        selectedMood ==
                                                mood.label

                                    val emojiScale by
                                    animateFloatAsState(

                                        targetValue =
                                            if (selected) {
                                                1.18f
                                            } else {
                                                1.0f
                                            },

                                        animationSpec =
                                            tween(
                                                durationMillis = 280,
                                                easing =
                                                    FastOutSlowInEasing
                                            ),

                                        label =
                                            "moodEmojiScale"
                                    )

                                    Column(
                                        horizontalAlignment =
                                            Alignment.CenterHorizontally
                                    ) {

                                        Box(
                                            modifier =
                                                Modifier
                                                    .size(
                                                        if (selected) {
                                                            54.dp
                                                        } else {
                                                            48.dp
                                                        }
                                                    )
                                                    .clip(
                                                        CircleShape
                                                    )
                                                    .background(
                                                        if (selected) {
                                                            Brush.linearGradient(
                                                                listOf(
                                                                    darkPurple,
                                                                    primaryPurple
                                                                )
                                                            )
                                                        } else {
                                                            Brush.linearGradient(
                                                                listOf(
                                                                    MaterialTheme
                                                                        .colorScheme
                                                                        .surface,
                                                                    MaterialTheme
                                                                        .colorScheme
                                                                        .surfaceVariant
                                                                )
                                                            )
                                                        }
                                                    )
                                                    .clickable {

                                                        selectedMood =
                                                            mood.label
                                                    },

                                            contentAlignment =
                                                Alignment.Center
                                        ) {

                                            Text(
                                                text =
                                                    mood.emoji,

                                                modifier =
                                                    Modifier.scale(
                                                        emojiScale
                                                    )
                                            )
                                        }

                                        Spacer(
                                            modifier =
                                                Modifier.height(5.dp)
                                        )

                                        Text(
                                            text =
                                                mood.label,

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .labelSmall,

                                            color =
                                                if (selected) {
                                                    darkPurple
                                                } else {
                                                    secondaryText
                                                },

                                            fontWeight =
                                                if (selected) {
                                                    FontWeight.SemiBold
                                                } else {
                                                    FontWeight.Normal
                                                }
                                        )
                                    }
                                }
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(18.dp)
                            )

                            OutlinedTextField(

                                value =
                                    moodText,

                                onValueChange = {
                                    moodText = it
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                label = {
                                    Text(
                                        "Describe your emotions"
                                    )
                                },

                                textStyle =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                shape =
                                    RoundedCornerShape(
                                        16.dp
                                    ),

                                colors =
                                    TextFieldDefaults.colors(

                                        focusedContainerColor =
                                            MaterialTheme
                                                .colorScheme
                                                .surface
                                                .copy(
                                                    alpha = 0.55f
                                                ),

                                        unfocusedContainerColor =
                                            MaterialTheme
                                                .colorScheme
                                                .surface
                                                .copy(
                                                    alpha = 0.40f
                                                ),

                                        focusedIndicatorColor =
                                            Color.Transparent,

                                        unfocusedIndicatorColor =
                                            Color.Transparent
                                    )
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            OutlinedTextField(

                                value =
                                    triggerText,

                                onValueChange = {
                                    triggerText = it
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                label = {
                                    Text(
                                        "What triggered this mood?"
                                    )
                                },

                                placeholder = {
                                    Text(
                                        "For example: work, relationships, stress, or something that happened today"
                                    )
                                },

                                textStyle =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                shape =
                                    RoundedCornerShape(
                                        16.dp
                                    ),

                                colors =
                                    TextFieldDefaults.colors(

                                        focusedContainerColor =
                                            MaterialTheme
                                                .colorScheme
                                                .surface
                                                .copy(
                                                    alpha = 0.55f
                                                ),

                                        unfocusedContainerColor =
                                            MaterialTheme
                                                .colorScheme
                                                .surface
                                                .copy(
                                                    alpha = 0.40f
                                                ),

                                        focusedIndicatorColor =
                                            Color.Transparent,

                                        unfocusedIndicatorColor =
                                            Color.Transparent
                                    )
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )

                            Button(

                                onClick = {

                                    if (
                                        moodText.isNotBlank()
                                    ) {

                                        viewModel.addMood(

                                            mood =
                                                moodText,

                                            emoji =
                                                selectedMood,

                                            trigger =
                                                triggerText
                                        )

                                        moodText = ""
                                        triggerText = ""
                                    }
                                },

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),

                                shape =
                                    RoundedCornerShape(
                                        16.dp
                                    ),

                                colors =
                                    ButtonDefaults
                                        .buttonColors(
                                            containerColor =
                                                darkPurple
                                        )
                            ) {

                                Text(
                                    text =
                                        "Save Mood",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .labelLarge,

                                    color =
                                        Color.White
                                )
                            }
                        }
                    }
                }

                /*
                 * ==================================================
                 * MOOD INSIGHT
                 * ==================================================
                 */

                item {

                    Card(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                ),

                        shape =
                            RoundedCornerShape(
                                22.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    mistBlue.copy(
                                        alpha = 0.88f
                                    )
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    horizontal = 18.dp,
                                    vertical = 16.dp
                                )
                        ) {

                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.AutoAwesome,

                                    contentDescription =
                                        null,

                                    tint =
                                        darkPurple,

                                    modifier =
                                        Modifier.size(
                                            20.dp
                                        )
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )

                                Text(
                                    text =
                                        "Mood Insight",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        textDark
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            Text(
                                text =
                                    insight,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    textDark.copy(
                                        alpha = 0.82f
                                    )
                            )
                        }
                    }
                }

                /*
                 * ==================================================
                 * READING
                 * ==================================================
                 */

                item {

                    Card(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                )
                                .clickable {
                                    onReadingClick()
                                },

                        shape =
                            RoundedCornerShape(
                                22.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    mutedRose.copy(
                                        alpha = 0.88f
                                    )
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                    ) {

                        Row(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 18.dp,
                                        vertical = 16.dp
                                    ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Box(

                                modifier =
                                    Modifier
                                        .size(46.dp)
                                        .clip(
                                            CircleShape
                                        )
                                        .background(
                                            MaterialTheme
                                                .colorScheme
                                                .surface
                                                .copy(
                                                    alpha = 0.55f
                                                )
                                        ),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.MenuBook,

                                    contentDescription =
                                        null,

                                    tint =
                                        darkPurple,

                                    modifier =
                                        Modifier.size(
                                            23.dp
                                        )
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
                                        "Reading",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        textDark
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(4.dp)
                                )

                                Text(

                                    text =
                                        "Explore recommended books about mental health, wellbeing, and personal growth.",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodySmall,

                                    color =
                                        secondaryText
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.width(8.dp)
                            )

                            Icon(

                                imageVector =
                                    Icons.Default.ChevronRight,

                                contentDescription =
                                    "Open reading",

                                tint =
                                    darkPurple
                            )
                        }
                    }
                }

                /*
                 * ==================================================
                 * REAL STORIES
                 * ==================================================
                 */

                item {

                    Card(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                )
                                .clickable {
                                    onRealStoriesClick()
                                },

                        shape =
                            RoundedCornerShape(
                                22.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    mistBlue.copy(
                                        alpha = 0.88f
                                    )
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                    ) {

                        Row(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 18.dp,
                                        vertical = 16.dp
                                    ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Box(

                                modifier =
                                    Modifier
                                        .size(46.dp)
                                        .clip(
                                            CircleShape
                                        )
                                        .background(
                                            MaterialTheme
                                                .colorScheme
                                                .surface
                                                .copy(
                                                    alpha = 0.55f
                                                )
                                        ),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.MenuBook,

                                    contentDescription =
                                        null,

                                    tint =
                                        darkPurple,

                                    modifier =
                                        Modifier.size(
                                            23.dp
                                        )
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
                                        "Real Stories",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .titleMedium,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        textDark
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(4.dp)
                                )

                                Text(

                                    text =
                                        "Read personal experiences with anxiety, depression, and CBT from people who have been through it.",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodySmall,

                                    color =
                                        secondaryText
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.width(8.dp)
                            )

                            Icon(

                                imageVector =
                                    Icons.Default.ChevronRight,

                                contentDescription =
                                    "Open real stories",

                                tint =
                                    darkPurple
                            )
                        }
                    }
                }
            }
        }
    }
}