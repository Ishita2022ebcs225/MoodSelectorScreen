package com.example.moodselector.presentations.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.MoodEntry
import com.example.moodselector.data.preferences.UserPreferencesRepository
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val repository: MoodRepository,
    private val authRepository: AuthRepository,
    private val cloudBackupRepository: CloudBackupRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * CURRENT USER ID
     * --------------------------------------------------
     */

    private val userId: String?
        get() = authRepository.currentUser?.uid


    /*
     * --------------------------------------------------
     * MOOD LIST
     * --------------------------------------------------
     *
     * Only moods belonging to the currently
     * authenticated user are loaded.
     */

    val moodList = repository
        .getAllMoods(
            userId = userId ?: ""
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    /*
     * --------------------------------------------------
     * ASSESSMENT STATUS
     * --------------------------------------------------
     *
     * This observes the current user's assessment
     * completion preference.
     *
     * When the assessment-completion preference is
     * deleted, this flow becomes false and the
     * MoodInsightsScreen can show the assessment prompt
     * again.
     */

    val assessmentCompleted: StateFlow<Boolean> =
        if (userId != null) {

            userPreferencesRepository
                .hasCompletedAssessment(userId!!)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = false
                )

        } else {

            kotlinx.coroutines.flow.MutableStateFlow(false)
        }


    /*
     * --------------------------------------------------
     * ADD MOOD ENTRY
     * --------------------------------------------------
     */

    fun addMood(
        mood: String,
        emoji: String,
        trigger: String
    ) {

        val currentUserId = userId
            ?: return

        val timestamp = SimpleDateFormat(
            "dd MMM yyyy • hh:mm a",
            Locale.getDefault()
        ).format(Date())

        viewModelScope.launch {

            repository.insertMood(
                MoodEntry(
                    userId = currentUserId,
                    mood = mood,
                    emoji = emoji,
                    trigger = trigger,
                    timestamp = timestamp
                )
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE MOOD ENTRY
     * --------------------------------------------------
     */

    fun deleteMood(
        mood: MoodEntry
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteMood(
                mood
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }
}