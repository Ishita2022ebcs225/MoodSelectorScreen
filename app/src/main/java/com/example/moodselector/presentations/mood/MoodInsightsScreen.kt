package com.example.moodselector.presentations.mood

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
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
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodInsightsScreen(
    onSettingsClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onAssessmentResultsClick: () -> Unit = {},
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
                body = "Your notifications are enabled."
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

    var selectedMood by remember {
        mutableStateOf("Happy")
    }

    val moodOptions =
        listOf(
            MoodOption(
                "Happy",
                Icons.Default.SentimentVerySatisfied
            ),
            MoodOption(
                "Calm",
                Icons.Default.SelfImprovement
            ),
            MoodOption(
                "Neutral",
                Icons.Default.SentimentNeutral
            ),
            MoodOption(
                "Sad",
                Icons.Default.SentimentDissatisfied
            ),
            MoodOption(
                "Angry",
                Icons.Default.MoodBad
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
                "😊" -> 5

                "Calm",
                "😌" -> 4

                "Neutral",
                "😐" -> 3

                "Sad",
                "😔" -> 2

                "Angry",
                "😡" -> 1

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

                    /*
                     * SIDEBAR HEADER
                     */

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

                    /*
                     * ASSESSMENT RESULTS
                     */

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

                    /*
                     * SETTINGS
                     */

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

                    /*
                     * LOGOUT
                     */

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

            /*
             * --------------------------------------------------
             * BACKGROUND
             * --------------------------------------------------
             */

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

            Scaffold(

                containerColor =
                    Color.Transparent

            ) { paddingValues ->

                LazyColumn(

                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(
                                paddingValues
                            )
                            .statusBarsPadding()
                            .navigationBarsPadding(),

                    contentPadding =
                        PaddingValues(
                            bottom = 110.dp
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
                                                Modifier.width(
                                                    12.dp
                                                )
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

                                        Column(
                                            horizontalAlignment =
                                                Alignment.CenterHorizontally
                                        ) {

                                            Box(
                                                modifier =
                                                    Modifier
                                                        .size(
                                                            if (selected) {
                                                                50.dp
                                                            } else {
                                                                44.dp
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

                                                Icon(
                                                    imageVector =
                                                        mood.icon,

                                                    contentDescription =
                                                        mood.label,

                                                    tint =
                                                        if (selected) {
                                                            Color.White
                                                        } else {
                                                            darkPurple
                                                        },

                                                    modifier =
                                                        Modifier.size(
                                                            22.dp
                                                        )
                                                )
                                            }

                                            Spacer(
                                                modifier =
                                                    Modifier.height(
                                                        5.dp
                                                    )
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
                                                    selectedMood
                                            )

                                            moodText = ""
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
                     * AI INSIGHT
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
                                            "AI Insight",

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
                     * RECENT ENTRIES
                     * ==================================================
                     */

                    item {

                        Text(
                            text =
                                "Recent Entries",

                            modifier =
                                Modifier.padding(
                                    horizontal = 18.dp
                                ),

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.White
                        )
                    }

                    items(
                        moods.takeLast(5).reversed(),
                        key = { mood ->
                            mood.id
                        }
                    ) { mood ->

                        Card(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    ),

                            shape =
                                RoundedCornerShape(
                                    20.dp
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
                                    defaultElevation = 1.dp
                                )
                        ) {

                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 13.dp
                                        ),

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier =
                                        Modifier
                                            .size(44.dp)
                                            .clip(
                                                CircleShape
                                            )
                                            .background(
                                                MaterialTheme
                                                    .colorScheme
                                                    .surface
                                                    .copy(
                                                        alpha = 0.42f
                                                    )
                                            ),

                                    contentAlignment =
                                        Alignment.Center
                                ) {

                                    Text(
                                        text =
                                            when (
                                                mood.emoji
                                            ) {

                                                "Happy",
                                                "😊" ->
                                                    "✨"

                                                "Calm",
                                                "😌" ->
                                                    "🌿"

                                                "Neutral",
                                                "😐" ->
                                                    "☁️"

                                                "Sad",
                                                "😔" ->
                                                    "💙"

                                                "Angry",
                                                "😡" ->
                                                    "🔥"

                                                else ->
                                                    "🌸"
                                            }
                                    )
                                }

                                Spacer(
                                    modifier =
                                        Modifier.width(12.dp)
                                )

                                Column(
                                    modifier =
                                        Modifier.weight(1f)
                                ) {

                                    Text(
                                        text =
                                            mood.mood,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodyLarge,

                                        fontWeight =
                                            FontWeight.SemiBold,

                                        color =
                                            textDark
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(2.dp)
                                    )

                                    Text(
                                        text =
                                            mood.timestamp,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall,

                                        color =
                                            secondaryText
                                    )
                                }
                            }
                        }
                    }

                    item {

                        Spacer(
                            modifier =
                                Modifier.height(24.dp)
                        )
                    }
                }
            }
        }
    }
}