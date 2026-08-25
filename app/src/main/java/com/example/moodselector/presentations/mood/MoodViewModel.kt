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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
     * null  = authentication / assessment status
     *         is still being restored
     *
     * true  = assessment has been completed
     *
     * false = assessment has not been completed
     *
     * Using null as the initial state prevents the
     * assessment prompt from briefly appearing while
     * Firebase restores the authenticated user and the
     * corresponding preference is loaded.
     */

    val assessmentCompleted: StateFlow<Boolean?> =
        authRepository.authState
            .flatMapLatest { user ->

                val currentUserId =
                    user?.uid

                if (currentUserId != null) {

                    userPreferencesRepository
                        .hasCompletedAssessment(
                            currentUserId
                        )
                } else {

                    flowOf(false)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )


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

        val currentUserId =
            userId ?: return

        val timestamp =
            SimpleDateFormat(
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

