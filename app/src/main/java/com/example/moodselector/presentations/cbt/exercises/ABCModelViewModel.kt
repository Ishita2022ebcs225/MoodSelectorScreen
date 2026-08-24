package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.ABCModelCompletionEntity
import com.example.moodselector.domain.repository.ABCModelCompletionRepository
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CBTDailyProgressRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class ABCModelUiState(
    val activatingEvent: String = "",
    val beliefs: String = "",
    val consequences: String = "",
    val isCompleted: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false
)

@HiltViewModel
class ABCModelViewModel @Inject constructor(
    private val repository: ABCModelCompletionRepository,
    private val dailyProgressRepository: CBTDailyProgressRepository,
    private val authRepository: AuthRepository,
    private val cloudBackupRepository: CloudBackupRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            ABCModelUiState()
        )

    val uiState: StateFlow<ABCModelUiState> =
        _uiState.asStateFlow()


    /*
     * --------------------------------------------------
     * CURRENT USER ID
     * --------------------------------------------------
     */

    private val userId: String?
        get() = authRepository.currentUser?.uid


    /*
     * --------------------------------------------------
     * ACTIVATING EVENT
     * --------------------------------------------------
     */

    fun updateActivatingEvent(
        value: String
    ) {

        _uiState.value =
            _uiState.value.copy(
                activatingEvent = value,
                isCompleted = false
            )
    }


    /*
     * --------------------------------------------------
     * BELIEFS
     * --------------------------------------------------
     */

    fun updateBeliefs(
        value: String
    ) {

        _uiState.value =
            _uiState.value.copy(
                beliefs = value,
                isCompleted = false
            )
    }


    /*
     * --------------------------------------------------
     * CONSEQUENCES
     * --------------------------------------------------
     */

    fun updateConsequences(
        value: String
    ) {

        _uiState.value =
            _uiState.value.copy(
                consequences = value,
                isCompleted = false
            )
    }


    /*
     * --------------------------------------------------
     * COMPLETION
     * --------------------------------------------------
     */

    fun markCompleted() {

        val state =
            _uiState.value

        if (
            state.activatingEvent.isBlank() ||
            state.beliefs.isBlank() ||
            state.consequences.isBlank()
        ) {
            return
        }

        _uiState.value =
            state.copy(
                isCompleted = true
            )
    }


    /*
     * --------------------------------------------------
     * SAVE COMPLETION
     * --------------------------------------------------
     *
     * The ABC exercise is persisted only after the user
     * explicitly completes the exercise.
     *
     * A successful completion also increments the user's
     * CBT daily progress for the current calendar date.
     */

    fun saveCompletion(
        onSaved: () -> Unit = {}
    ) {

        val state =
            _uiState.value

        /*
         * Do not save incomplete exercises.
         */

        if (
            !state.isCompleted ||
            state.isSaved ||
            state.isSaving ||
            state.activatingEvent.isBlank() ||
            state.beliefs.isBlank() ||
            state.consequences.isBlank()
        ) {
            return
        }

        val currentUserId =
            userId ?: return

        /*
         * Prevent duplicate save attempts while the
         * persistence operation is running.
         */

        _uiState.value =
            state.copy(
                isSaving = true
            )

        viewModelScope.launch {

            try {

                val completion =
                    ABCModelCompletionEntity(

                        userId =
                            currentUserId,

                        activatingEvent =
                            state.activatingEvent.trim(),

                        beliefs =
                            state.beliefs.trim(),

                        consequences =
                            state.consequences.trim(),

                        completedAt =
                            System.currentTimeMillis()
                    )

                /*
                 * --------------------------------------------------
                 * SAVE LOCALLY FIRST
                 * --------------------------------------------------
                 */

                repository.saveCompletion(
                    completion
                )

                /*
                 * --------------------------------------------------
                 * UPDATE DAILY CBT PROGRESS
                 * --------------------------------------------------
                 *
                 * The daily progress record uses yyyy-MM-dd as
                 * its calendar-date identifier.
                 *
                 * This is incremented once for this explicit
                 * completion.
                 */

                val currentDate =
                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.US
                    ).format(
                        Date()
                    )

                runCatching {

                    dailyProgressRepository
                        .incrementDailyCompletion(
                            userId = currentUserId,
                            date = currentDate
                        )
                }

                /*
                 * Local persistence succeeded.
                 *
                 * The exercise is considered saved even if
                 * daily-progress persistence or cloud backup
                 * is temporarily unavailable.
                 */

                _uiState.value =
                    _uiState.value.copy(
                        isSaving = false,
                        isSaved = true
                    )

                onSaved()

                /*
                 * --------------------------------------------------
                 * CLOUD BACKUP
                 * --------------------------------------------------
                 *
                 * Cloud backup is best-effort. A Firestore
                 * failure must not undo the successful local
                 * completion or prevent navigation.
                 */

                runCatching {

                    cloudBackupRepository
                        .backupUserData(
                            userId = currentUserId
                        )
                }

            } catch (exception: Exception) {

                /*
                 * Keep the user's responses in the
                 * ViewModel so they are not lost if
                 * local completion persistence fails.
                 */

                _uiState.value =
                    _uiState.value.copy(
                        isSaving = false
                    )
            }
        }
    }


    /*
     * --------------------------------------------------
     * RESET
     * --------------------------------------------------
     */

    fun reset() {

        _uiState.value =
            ABCModelUiState()
    }
}