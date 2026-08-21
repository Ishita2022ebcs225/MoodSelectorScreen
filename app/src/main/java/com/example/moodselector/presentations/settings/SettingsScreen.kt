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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
    onAccountDeleted: () -> Unit,
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
     * DELETE ALL DATA DIALOG
     * --------------------------------------------------
     */

    var showDeleteAllDataDialog by remember {
        mutableStateOf(false)
    }


    /*
     * --------------------------------------------------
     * DELETE ACCOUNT DIALOG
     * --------------------------------------------------
     */

    var showDeleteAccountDialog by remember {
        mutableStateOf(false)
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
     */

    val background =
        MaterialTheme.colorScheme.background

    val surface =
        MaterialTheme.colorScheme.surface

    val textDark =
        MaterialTheme.colorScheme.onSurface

    val darkPurple =
        MaterialTheme.colorScheme.primary

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
                    Modifier.height(16.dp)
            )


            /*
             * --------------------------------------------------
             * DELETE ALL USER DATA
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
                                Icons.Default.Delete,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .error
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
                                    "Delete all data",

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
                                    "Permanently delete your saved app data.",

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
                            Modifier.height(16.dp)
                    )

                    OutlinedButton(

                        onClick = {
                            showDeleteAllDataDialog = true
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            ButtonDefaults.outlinedButtonColors(
                                contentColor =
                                    MaterialTheme
                                        .colorScheme
                                        .error
                            )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Delete,

                            contentDescription =
                                null
                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Text(
                            text =
                                "Delete all data"
                        )
                    }
                }
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            /*
             * --------------------------------------------------
             * DELETE ACCOUNT
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
                                Icons.Default.Delete,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .error
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
                                    "Delete account",

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
                                    "Permanently delete your account and its authentication profile.",

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
                            Modifier.height(16.dp)
                    )

                    OutlinedButton(

                        onClick = {
                            showDeleteAccountDialog = true
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            ButtonDefaults.outlinedButtonColors(
                                contentColor =
                                    MaterialTheme
                                        .colorScheme
                                        .error
                            )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Delete,

                            contentDescription =
                                null
                        )

                        Spacer(
                            modifier =
                                Modifier.width(8.dp)
                        )

                        Text(
                            text =
                                "Delete account"
                        )
                    }
                }
            }


            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ALL DATA CONFIRMATION
     * --------------------------------------------------
     */

    if (showDeleteAllDataDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteAllDataDialog = false
            },

            title = {
                Text(
                    text =
                        "Delete all data?"
                )
            },

            text = {
                Text(
                    text =
                        "This will permanently delete your saved moods, journals, assessment results, CBT progress, scheduled CBT activities, and other saved app data. This action cannot be undone."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        userId?.let {

                            settingsViewModel
                                .deleteAllUserData(it)
                        }

                        showDeleteAllDataDialog =
                            false
                    }
                ) {

                    Text(
                        text =
                            "Delete",

                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteAllDataDialog = false
                    }
                ) {

                    Text(
                        text =
                            "Cancel"
                    )
                }
            }
        )
    }


    /*
     * --------------------------------------------------
     * DELETE ACCOUNT CONFIRMATION
     * --------------------------------------------------
     */

    if (showDeleteAccountDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteAccountDialog = false
            },

            title = {
                Text(
                    text =
                        "Delete account?"
                )
            },

            text = {
                Text(
                    text =
                        "This will permanently delete your account. Your authentication account will be removed and you will no longer be able to sign in with it. This action cannot be undone."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        showDeleteAccountDialog =
                            false

                        onAccountDeleted()
                    }
                ) {

                    Text(
                        text =
                            "Delete account",

                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteAccountDialog = false
                    }
                ) {

                    Text(
                        text =
                            "Cancel"
                    )
                }
            }
        )
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