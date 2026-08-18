package com.example.moodselector.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    /*
     * --------------------------------------------------
     * ASSESSMENT COMPLETION KEY
     * --------------------------------------------------
     */

    private fun assessmentCompletedKey(
        userId: String
    ) = booleanPreferencesKey(
        "has_completed_assessment_$userId"
    )


    /*
     * --------------------------------------------------
     * NOTIFICATION PREFERENCE KEYS
     * --------------------------------------------------
     */

    private fun notificationsEnabledKey(
        userId: String
    ) = booleanPreferencesKey(
        "notifications_enabled_$userId"
    )

    private fun moodReminderEnabledKey(
        userId: String
    ) = booleanPreferencesKey(
        "mood_reminder_enabled_$userId"
    )

    private fun journalReminderEnabledKey(
        userId: String
    ) = booleanPreferencesKey(
        "journal_reminder_enabled_$userId"
    )

    private fun wellbeingReminderEnabledKey(
        userId: String
    ) = booleanPreferencesKey(
        "wellbeing_reminder_enabled_$userId"
    )


    /*
     * --------------------------------------------------
     * REMINDER TIME KEYS
     * --------------------------------------------------
     */

    private fun moodReminderTimeKey(
        userId: String
    ) = stringPreferencesKey(
        "mood_reminder_time_$userId"
    )

    private fun journalReminderTimeKey(
        userId: String
    ) = stringPreferencesKey(
        "journal_reminder_time_$userId"
    )

    private fun wellbeingReminderTimeKey(
        userId: String
    ) = stringPreferencesKey(
        "wellbeing_reminder_time_$userId"
    )


    /*
     * --------------------------------------------------
     * THEME PREFERENCE KEY
     * --------------------------------------------------
     */

    private fun themeModeKey(
        userId: String
    ) = stringPreferencesKey(
        "theme_mode_$userId"
    )


    /*
     * --------------------------------------------------
     * GET ASSESSMENT COMPLETION
     * --------------------------------------------------
     */

    fun hasCompletedAssessment(
        userId: String
    ): Flow<Boolean> =
        dataStore.data.map { preferences ->

            preferences[
                assessmentCompletedKey(userId)
            ] ?: false
        }


    /*
     * --------------------------------------------------
     * SET ASSESSMENT COMPLETION
     * --------------------------------------------------
     */

    suspend fun setAssessmentCompleted(
        userId: String,
        completed: Boolean
    ) {

        dataStore.edit { preferences ->

            preferences[
                assessmentCompletedKey(userId)
            ] = completed
        }
    }


    /*
     * --------------------------------------------------
     * CLEAR ASSESSMENT COMPLETION
     * --------------------------------------------------
     */

    suspend fun clearAssessmentCompleted(
        userId: String
    ) {

        dataStore.edit { preferences ->

            preferences.remove(
                assessmentCompletedKey(userId)
            )
        }
    }


    /*
     * ==================================================
     * NOTIFICATION PREFERENCES
     * ==================================================
     */

    fun areNotificationsEnabled(
        userId: String
    ): Flow<Boolean> =
        dataStore.data.map { preferences ->

            preferences[
                notificationsEnabledKey(userId)
            ] ?: true
        }


    suspend fun setNotificationsEnabled(
        userId: String,
        enabled: Boolean
    ) {

        dataStore.edit { preferences ->

            preferences[
                notificationsEnabledKey(userId)
            ] = enabled
        }
    }


    /*
     * ==================================================
     * MOOD REMINDER
     * ==================================================
     */

    fun isMoodReminderEnabled(
        userId: String
    ): Flow<Boolean> =
        dataStore.data.map { preferences ->

            preferences[
                moodReminderEnabledKey(userId)
            ] ?: false
        }


    suspend fun setMoodReminderEnabled(
        userId: String,
        enabled: Boolean
    ) {

        dataStore.edit { preferences ->

            preferences[
                moodReminderEnabledKey(userId)
            ] = enabled
        }
    }


    fun getMoodReminderTime(
        userId: String
    ): Flow<String> =
        dataStore.data.map { preferences ->

            preferences[
                moodReminderTimeKey(userId)
            ] ?: "09:00"
        }


    suspend fun setMoodReminderTime(
        userId: String,
        time: String
    ) {

        dataStore.edit { preferences ->

            preferences[
                moodReminderTimeKey(userId)
            ] = time
        }
    }


    /*
     * ==================================================
     * JOURNAL REMINDER
     * ==================================================
     */

    fun isJournalReminderEnabled(
        userId: String
    ): Flow<Boolean> =
        dataStore.data.map { preferences ->

            preferences[
                journalReminderEnabledKey(userId)
            ] ?: false
        }


    suspend fun setJournalReminderEnabled(
        userId: String,
        enabled: Boolean
    ) {

        dataStore.edit { preferences ->

            preferences[
                journalReminderEnabledKey(userId)
            ] = enabled
        }
    }


    fun getJournalReminderTime(
        userId: String
    ): Flow<String> =
        dataStore.data.map { preferences ->

            preferences[
                journalReminderTimeKey(userId)
            ] ?: "20:00"
        }


    suspend fun setJournalReminderTime(
        userId: String,
        time: String
    ) {

        dataStore.edit { preferences ->

            preferences[
                journalReminderTimeKey(userId)
            ] = time
        }
    }


    /*
     * ==================================================
     * WELLBEING REMINDER
     * ==================================================
     */

    fun isWellbeingReminderEnabled(
        userId: String
    ): Flow<Boolean> =
        dataStore.data.map { preferences ->

            preferences[
                wellbeingReminderEnabledKey(userId)
            ] ?: false
        }


    suspend fun setWellbeingReminderEnabled(
        userId: String,
        enabled: Boolean
    ) {

        dataStore.edit { preferences ->

            preferences[
                wellbeingReminderEnabledKey(userId)
            ] = enabled
        }
    }


    fun getWellbeingReminderTime(
        userId: String
    ): Flow<String> =
        dataStore.data.map { preferences ->

            preferences[
                wellbeingReminderTimeKey(userId)
            ] ?: "12:00"
        }


    suspend fun setWellbeingReminderTime(
        userId: String,
        time: String
    ) {

        dataStore.edit { preferences ->

            preferences[
                wellbeingReminderTimeKey(userId)
            ] = time
        }
    }


    /*
     * ==================================================
     * THEME PREFERENCE
     * ==================================================
     */

    fun getThemeMode(
        userId: String
    ): Flow<String> =
        dataStore.data.map { preferences ->

            preferences[
                themeModeKey(userId)
            ] ?: "system"
        }


    suspend fun setThemeMode(
        userId: String,
        themeMode: String
    ) {

        dataStore.edit { preferences ->

            preferences[
                themeModeKey(userId)
            ] = themeMode
        }
    }
}