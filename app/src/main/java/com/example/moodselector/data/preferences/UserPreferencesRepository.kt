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

    /*
     * --------------------------------------------------
     * ASSESSMENT COMPLETION KEY
     * --------------------------------------------------
     *
     * A separate key is created for every Firebase user.
     *
     * Example:
     *
     * has_completed_assessment_userA
     * has_completed_assessment_userB
     *
     * This prevents one user's assessment state from
     * affecting another user's account.
     */

    private fun assessmentCompletedKey(
        userId: String
    ) = booleanPreferencesKey(
        "has_completed_assessment_$userId"
    )


    /*
     * --------------------------------------------------
     * GET ASSESSMENT COMPLETION FOR USER
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
     * SET ASSESSMENT COMPLETION FOR USER
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
     * CLEAR ASSESSMENT COMPLETION FOR USER
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
}