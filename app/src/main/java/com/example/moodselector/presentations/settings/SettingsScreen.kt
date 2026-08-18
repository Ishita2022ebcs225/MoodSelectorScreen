package com.example.moodselector.presentations.settings

import android.Manifest
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodselector.presentations.auth.AuthViewModel

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val context =
        LocalContext.current

    /*
     * --------------------------------------------------
     * AUTHENTICATED USER
     * --------------------------------------------------
     */

    val authUiState by
    authViewModel.uiState.collectAsStateWithLifecycle()

    val userId =
        authUiState.user?.uid


    /*
     * --------------------------------------------------
     * SETTINGS STATE
     * --------------------------------------------------
     */

    val settingsUiState by
    settingsViewModel.uiState
        .collectAsStateWithLifecycle()


    LaunchedEffect(userId) {

        userId?.let {

            settingsViewModel.loadForUser(
                userId = it
            )
        }
    }


    /*
     * --------------------------------------------------
     * NOTIFICATION PERMISSION STATE
     * --------------------------------------------------
     */

    var notificationsEnabled by remember {

        mutableStateOf(

            if (
                Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.TIRAMISU
            ) {

                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            } else {

                true
            }
        )
    }


    /*
     * --------------------------------------------------
     * NOTIFICATION PERMISSION REQUEST
     * --------------------------------------------------
     */

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            notificationsEnabled =
                isGranted
        }


    /*
     * --------------------------------------------------
     * OPEN ANDROID NOTIFICATION SETTINGS
     * --------------------------------------------------
     */

    fun openNotificationSettings() {

        val intent =
            Intent(
                Settings.ACTION_APP_NOTIFICATION_SETTINGS
            ).apply {

                putExtra(
                    Settings.EXTRA_APP_PACKAGE,
                    context.packageName
                )
            }

        context.startActivity(
            intent
        )
    }


    /*
     * --------------------------------------------------
     * TIME PICKER
     * --------------------------------------------------
     */

    fun showTimePicker(
        currentTime: String,
        onTimeSelected: (String) -> Unit
    ) {

        val parts =
            currentTime.split(":")

        val hour =
            parts.getOrNull(0)
                ?.toIntOrNull()
                ?: 9

        val minute =
            parts.getOrNull(1)
                ?.toIntOrNull()
                ?: 0

        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->

                onTimeSelected(
                    String.format(
                        "%02d:%02d",
                        selectedHour,
                        selectedMinute
                    )
                )
            },
            hour,
            minute,
            false
        ).show()
    }


    /*
     * --------------------------------------------------
     * COLORS
     * --------------------------------------------------
     *
     * Keep the screen background consistent with the
     * other app screens by deriving it from MaterialTheme.
     *
     * The active light/dark palette is controlled by
     * MoodselectorTheme.kt.
     */

    val background =
        MaterialTheme.colorScheme.background

    val surface =
        MaterialTheme.colorScheme.surface

    val textDark =
        MaterialTheme.colorScheme.onSurface

    val darkPurple =
        MaterialTheme.colorScheme.primary

    val softLavender =
        MaterialTheme.colorScheme.secondaryContainer

    val warmGlass =
        surface.copy(
            alpha = 0.92f
        )


    /*
     * --------------------------------------------------
     * SCREEN
     * --------------------------------------------------
     */

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            background,
                            surface,
                            MaterialTheme
                                .colorScheme
                                .surfaceVariant
                        )
                    )
                )
    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 18.dp
                    )
        ) {

            /*
             * --------------------------------------------------
             * HEADER
             * --------------------------------------------------
             */

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 12.dp
                        ),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.ArrowBack,

                        contentDescription =
                            "Back",

                        tint =
                            MaterialTheme
                                .colorScheme
                                .onBackground
                    )
                }

                Spacer(
                    modifier =
                        Modifier.weight(1f)
                )

                Icon(
                    imageVector =
                        Icons.Default.Settings,

                    contentDescription =
                        null,

                    tint =
                        darkPurple
                )

                Spacer(
                    modifier =
                        Modifier.width(6.dp)
                )

                Text(
                    text =
                        "Settings",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onBackground
                )

                Spacer(
                    modifier =
                        Modifier.weight(1f)
                )

                Spacer(
                    modifier =
                        Modifier.width(48.dp)
                )
            }


            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )


            /*
             * --------------------------------------------------
             * NOTIFICATIONS
             * --------------------------------------------------
             */

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        28.dp
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            warmGlass
                    )
            ) {

                Column(

                    modifier =
                        Modifier.padding(
                            20.dp
                        )
                ) {

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Notifications,

                            contentDescription =
                                null,

                            tint =
                                darkPurple
                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Column(

                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    "Notifications",

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
                                    if (
                                        notificationsEnabled
                                    ) {
                                        "Notifications are enabled."
                                    } else {
                                        "Notifications are disabled."
                                    },

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }

                        Switch(

                            checked =
                                notificationsEnabled,

                            onCheckedChange = { enabled ->

                                if (enabled) {

                                    if (
                                        Build.VERSION.SDK_INT >=
                                        Build.VERSION_CODES.TIRAMISU
                                    ) {

                                        notificationPermissionLauncher
                                            .launch(
                                                Manifest.permission
                                                    .POST_NOTIFICATIONS
                                            )

                                    } else {

                                        notificationsEnabled =
                                            true
                                    }

                                } else {

                                    openNotificationSettings()
                                }
                            }
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(22.dp)
                    )


                    /*
                     * --------------------------------------------------
                     * MOOD CHECK-IN
                     * --------------------------------------------------
                     */

                    ReminderRow(

                        title =
                            "Mood check-in",

                        description =
                            "Remind me to record how I'm feeling.",

                        checked =
                            settingsUiState
                                .moodReminderEnabled,

                        time =
                            settingsUiState
                                .moodReminderTime,

                        onCheckedChange =
                            settingsViewModel::
                            setMoodReminderEnabled,

                        onTimeClick = {

                            showTimePicker(
                                settingsUiState
                                    .moodReminderTime
                            ) { selectedTime ->

                                settingsViewModel
                                    .setMoodReminderTime(
                                        selectedTime
                                    )
                            }
                        }
                    )


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    /*
                     * --------------------------------------------------
                     * JOURNAL
                     * --------------------------------------------------
                     */

                    ReminderRow(

                        title =
                            "Journal reminder",

                        description =
                            "Remind me to reflect in my journal.",

                        checked =
                            settingsUiState
                                .journalReminderEnabled,

                        time =
                            settingsUiState
                                .journalReminderTime,

                        onCheckedChange =
                            settingsViewModel::
                            setJournalReminderEnabled,

                        onTimeClick = {

                            showTimePicker(
                                settingsUiState
                                    .journalReminderTime
                            ) { selectedTime ->

                                settingsViewModel
                                    .setJournalReminderTime(
                                        selectedTime
                                    )
                            }
                        }
                    )


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    /*
                     * --------------------------------------------------
                     * WELLBEING
                     * --------------------------------------------------
                     */

                    ReminderRow(

                        title =
                            "Wellbeing reminder",

                        description =
                            "Remind me to take a moment for myself.",

                        checked =
                            settingsUiState
                                .wellbeingReminderEnabled,

                        time =
                            settingsUiState
                                .wellbeingReminderTime,

                        onCheckedChange =
                            settingsViewModel::
                            setWellbeingReminderEnabled,

                        onTimeClick = {

                            showTimePicker(
                                settingsUiState
                                    .wellbeingReminderTime
                            ) { selectedTime ->

                                settingsViewModel
                                    .setWellbeingReminderTime(
                                        selectedTime
                                    )
                            }
                        }
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            /*
             * --------------------------------------------------
             * THEME
             * --------------------------------------------------
             */

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        28.dp
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            warmGlass
                    )
            ) {

                Column(

                    modifier =
                        Modifier.padding(
                            20.dp
                        )
                ) {

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                when (
                                    settingsUiState.themeMode
                                ) {

                                    "dark" ->
                                        Icons.Default.DarkMode

                                    else ->
                                        Icons.Default.WbSunny
                                },

                            contentDescription =
                                null,

                            tint =
                                darkPurple
                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Column {

                            Text(
                                text =
                                    "Theme",

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
                                    "Choose how the app should appear.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
                            )
                        }
                    }


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    ThemeOption(
                        title = "System default",
                        selected =
                            settingsUiState.themeMode ==
                                    "system",
                        onClick = {
                            settingsViewModel.setThemeMode(
                                "system"
                            )
                        }
                    )


                    ThemeOption(
                        title = "Light",
                        selected =
                            settingsUiState.themeMode ==
                                    "light",
                        onClick = {
                            settingsViewModel.setThemeMode(
                                "light"
                            )
                        }
                    )


                    ThemeOption(
                        title = "Dark",
                        selected =
                            settingsUiState.themeMode ==
                                    "dark",
                        onClick = {
                            settingsViewModel.setThemeMode(
                                "dark"
                            )
                        }
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )
        }
    }
}


/*
 * ==================================================
 * THEME OPTION
 * ==================================================
 */

@Composable
private fun ThemeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Row(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onClick
                )
                .padding(
                    vertical = 8.dp
                ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        RadioButton(
            selected =
                selected,

            onClick =
                onClick
        )

        Spacer(
            modifier =
                Modifier.width(8.dp)
        )

        Text(
            text =
                title,

            style =
                MaterialTheme
                    .typography
                    .bodyLarge
        )
    }
}


/*
 * ==================================================
 * REMINDER ROW
 * ==================================================
 */

@Composable
private fun ReminderRow(
    title: String,
    description: String,
    checked: Boolean,
    time: String,
    onCheckedChange: (Boolean) -> Unit,
    onTimeClick: () -> Unit
) {

    Row(

        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Column(

            modifier =
                Modifier.weight(1f)
        ) {

            Text(

                text =
                    title,

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.SemiBold
            )

            Spacer(
                modifier =
                    Modifier.height(3.dp)
            )

            Text(

                text =
                    description,

                style =
                    MaterialTheme
                        .typography
                        .bodySmall,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(

                text =
                    "Daily at $time",

                modifier =
                    Modifier.clickable(
                        onClick =
                            onTimeClick
                    ),

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                color =
                    MaterialTheme
                        .colorScheme
                        .primary,

                fontWeight =
                    FontWeight.SemiBold
            )
        }

        Spacer(
            modifier =
                Modifier.width(12.dp)
        )

        Switch(

            checked =
                checked,

            onCheckedChange =
                onCheckedChange
        )
    }
}

