package com.example.moodselector.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val HAS_COMPLETED_ASSESSMENT =
            booleanPreferencesKey("has_completed_assessment")
    }

    val hasCompletedAssessment: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HAS_COMPLETED_ASSESSMENT] ?: false
        }

    suspend fun setAssessmentCompleted(
        completed: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[HAS_COMPLETED_ASSESSMENT] = completed
        }
    }

    suspend fun clearAssessmentCompleted() {
        dataStore.edit { preferences ->
            preferences[HAS_COMPLETED_ASSESSMENT] = false
        }
    }
}