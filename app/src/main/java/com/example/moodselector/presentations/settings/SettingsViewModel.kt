package com.example.moodselector.presentations.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.notifications.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val moodReminderEnabled: Boolean = false,
    val journalReminderEnabled: Boolean = false,
    val wellbeingReminderEnabled: Boolean = false,
    val moodReminderTime: String = "09:00",
    val journalReminderTime: String = "20:00",
    val wellbeingReminderTime: String = "12:00",
    val themeMode: String = "system"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application,
    private val userPreferencesRepository: UserPreferencesRepository
) : AndroidViewModel(application) {

    private var currentUserId: String? = null

    private val moodReminderEnabled =
        MutableStateFlow(false)

    private val journalReminderEnabled =
        MutableStateFlow(false)

    private val wellbeingReminderEnabled =
        MutableStateFlow(false)

    private val moodReminderTime =
        MutableStateFlow("09:00")

    private val journalReminderTime =
        MutableStateFlow("20:00")

    private val wellbeingReminderTime =
        MutableStateFlow("12:00")

    private val themeMode =
        MutableStateFlow("system")


    val uiState: StateFlow<SettingsUiState> =
        combine(
            combine(
                moodReminderEnabled,
                journalReminderEnabled,
                wellbeingReminderEnabled
            ) { mood, journal, wellbeing ->

                Triple(
                    mood,
                    journal,
                    wellbeing
                )
            },
            combine(
                moodReminderTime,
                journalReminderTime,
                wellbeingReminderTime
            ) { moodTime, journalTime, wellbeingTime ->

                Triple(
                    moodTime,
                    journalTime,
                    wellbeingTime
                )
            },
            themeMode
        ) { enabledStates, timeStates, selectedTheme ->

            SettingsUiState(
                moodReminderEnabled =
                    enabledStates.first,

                journalReminderEnabled =
                    enabledStates.second,

                wellbeingReminderEnabled =
                    enabledStates.third,

                moodReminderTime =
                    timeStates.first,

                journalReminderTime =
                    timeStates.second,

                wellbeingReminderTime =
                    timeStates.third,

                themeMode =
                    selectedTheme
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState()
        )


    fun loadForUser(
        userId: String
    ) {

        if (currentUserId == userId) {
            return
        }

        currentUserId =
            userId

        viewModelScope.launch {

            userPreferencesRepository
                .isMoodReminderEnabled(userId)
                .collect { enabled ->

                    moodReminderEnabled.value =
                        enabled

                    if (enabled) {

                        ReminderScheduler.schedule(
                            getApplication(),
                            ReminderScheduler.MOOD,
                            moodReminderTime.value
                        )

                    } else {

                        ReminderScheduler.cancel(
                            getApplication(),
                            ReminderScheduler.MOOD
                        )
                    }
                }
        }

        viewModelScope.launch {

            userPreferencesRepository
                .isJournalReminderEnabled(userId)
                .collect { enabled ->

                    journalReminderEnabled.value =
                        enabled

                    if (enabled) {

                        ReminderScheduler.schedule(
                            getApplication(),
                            ReminderScheduler.JOURNAL,
                            journalReminderTime.value
                        )

                    } else {

                        ReminderScheduler.cancel(
                            getApplication(),
                            ReminderScheduler.JOURNAL
                        )
                    }
                }
        }

        viewModelScope.launch {

            userPreferencesRepository
                .isWellbeingReminderEnabled(userId)
                .collect { enabled ->

                    wellbeingReminderEnabled.value =
                        enabled

                    if (enabled) {

                        ReminderScheduler.schedule(
                            getApplication(),
                            ReminderScheduler.WELLBEING,
                            wellbeingReminderTime.value
                        )

                    } else {

                        ReminderScheduler.cancel(
                            getApplication(),
                            ReminderScheduler.WELLBEING
                        )
                    }
                }
        }

        viewModelScope.launch {

            userPreferencesRepository
                .getMoodReminderTime(userId)
                .collect { time ->

                    moodReminderTime.value =
                        time

                    if (
                        moodReminderEnabled.value
                    ) {

                        ReminderScheduler.schedule(
                            getApplication(),
                            ReminderScheduler.MOOD,
                            time
                        )
                    }
                }
        }

        viewModelScope.launch {

            userPreferencesRepository
                .getJournalReminderTime(userId)
                .collect { time ->

                    journalReminderTime.value =
                        time

                    if (
                        journalReminderEnabled.value
                    ) {

                        ReminderScheduler.schedule(
                            getApplication(),
                            ReminderScheduler.JOURNAL,
                            time
                        )
                    }
                }
        }

        viewModelScope.launch {

            userPreferencesRepository
                .getWellbeingReminderTime(userId)
                .collect { time ->

                    wellbeingReminderTime.value =
                        time

                    if (
                        wellbeingReminderEnabled.value
                    ) {

                        ReminderScheduler.schedule(
                            getApplication(),
                            ReminderScheduler.WELLBEING,
                            time
                        )
                    }
                }
        }

        viewModelScope.launch {

            userPreferencesRepository
                .getThemeMode(userId)
                .collect { selectedTheme ->

                    themeMode.value =
                        selectedTheme
                }
        }
    }


    fun setMoodReminderEnabled(
        enabled: Boolean
    ) {

        val userId =
            currentUserId
                ?: return

        moodReminderEnabled.value =
            enabled

        viewModelScope.launch {

            userPreferencesRepository
                .setMoodReminderEnabled(
                    userId = userId,
                    enabled = enabled
                )

            if (enabled) {

                ReminderScheduler.schedule(
                    getApplication(),
                    ReminderScheduler.MOOD,
                    moodReminderTime.value
                )

            } else {

                ReminderScheduler.cancel(
                    getApplication(),
                    ReminderScheduler.MOOD
                )
            }
        }
    }


    fun setJournalReminderEnabled(
        enabled: Boolean
    ) {

        val userId =
            currentUserId
                ?: return

        journalReminderEnabled.value =
            enabled

        viewModelScope.launch {

            userPreferencesRepository
                .setJournalReminderEnabled(
                    userId = userId,
                    enabled = enabled
                )

            if (enabled) {

                ReminderScheduler.schedule(
                    getApplication(),
                    ReminderScheduler.JOURNAL,
                    journalReminderTime.value
                )

            } else {

                ReminderScheduler.cancel(
                    getApplication(),
                    ReminderScheduler.JOURNAL
                )
            }
        }
    }


    fun setWellbeingReminderEnabled(
        enabled: Boolean
    ) {

        val userId =
            currentUserId
                ?: return

        wellbeingReminderEnabled.value =
            enabled

        viewModelScope.launch {

            userPreferencesRepository
                .setWellbeingReminderEnabled(
                    userId = userId,
                    enabled = enabled
                )

            if (enabled) {

                ReminderScheduler.schedule(
                    getApplication(),
                    ReminderScheduler.WELLBEING,
                    wellbeingReminderTime.value
                )

            } else {

                ReminderScheduler.cancel(
                    getApplication(),
                    ReminderScheduler.WELLBEING
                )
            }
        }
    }


    fun setMoodReminderTime(
        time: String
    ) {

        val userId =
            currentUserId
                ?: return

        moodReminderTime.value =
            time

        viewModelScope.launch {

            userPreferencesRepository
                .setMoodReminderTime(
                    userId = userId,
                    time = time
                )

            if (
                moodReminderEnabled.value
            ) {

                ReminderScheduler.schedule(
                    getApplication(),
                    ReminderScheduler.MOOD,
                    time
                )
            }
        }
    }


    fun setJournalReminderTime(
        time: String
    ) {

        val userId =
            currentUserId
                ?: return

        journalReminderTime.value =
            time

        viewModelScope.launch {

            userPreferencesRepository
                .setJournalReminderTime(
                    userId = userId,
                    time = time
                )

            if (
                journalReminderEnabled.value
            ) {

                ReminderScheduler.schedule(
                    getApplication(),
                    ReminderScheduler.JOURNAL,
                    time
                )
            }
        }
    }


    fun setWellbeingReminderTime(
        time: String
    ) {

        val userId =
            currentUserId
                ?: return

        wellbeingReminderTime.value =
            time

        viewModelScope.launch {

            userPreferencesRepository
                .setWellbeingReminderTime(
                    userId = userId,
                    time = time
                )

            if (
                wellbeingReminderEnabled.value
            ) {

                ReminderScheduler.schedule(
                    getApplication(),
                    ReminderScheduler.WELLBEING,
                    time
                )
            }
        }
    }


    fun setThemeMode(
        selectedTheme: String
    ) {

        val userId =
            currentUserId
                ?: return

        themeMode.value =
            selectedTheme

        viewModelScope.launch {

            userPreferencesRepository
                .setThemeMode(
                    userId = userId,
                    themeMode = selectedTheme
                )
        }
    }
}