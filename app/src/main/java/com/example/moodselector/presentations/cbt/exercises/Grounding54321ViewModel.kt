package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.Grounding54321CompletionEntity
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.Grounding54321CompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Grounding54321UiState(

    /*
     * Whether the grounding exercise has started.
     */
    val isRunning: Boolean = false,

    /*
     * Whether all five grounding stages
     * have been completed.
     */
    val isCompleted: Boolean = false,

    /*
     * User reflection after completing
     * the exercise.
     */
    val reflection: String = "",

    /*
     * Whether the completion has already
     * been persisted.
     */
    val isSaved: Boolean = false
)

@HiltViewModel
class Grounding54321ViewModel @Inject constructor(
    private val repository:
    Grounding54321CompletionRepository,
    private val authRepository:
    AuthRepository,
    private val cloudBackupRepository:
    CloudBackupRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            Grounding54321UiState()
        )

    val uiState: StateFlow<Grounding54321UiState> =
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
     * START GROUNDING
     * --------------------------------------------------
     */

    fun startGrounding() {

        _uiState.value =
            _uiState.value.copy(
                isRunning = true,
                isCompleted = false,
                isSaved = false
            )
    }


    /*
     * --------------------------------------------------
     * MARK GROUNDING COMPLETE
     * --------------------------------------------------
     *
     * This does NOT save anything.
     *
     * Persistence only happens when the user
     * explicitly presses "Complete Grounding".
     */

    fun markCompleted() {

        _uiState.value =
            _uiState.value.copy(
                isRunning = false,
                isCompleted = true
            )
    }


    /*
     * --------------------------------------------------
     * UPDATE REFLECTION
     * --------------------------------------------------
     */

    fun updateReflection(
        reflection: String
    ) {

        _uiState.value =
            _uiState.value.copy(
                reflection = reflection
            )
    }


    /*
     * --------------------------------------------------
     * SAVE COMPLETION
     * --------------------------------------------------
     *
     * This is the ONLY persistence entry point.
     */

    fun saveCompletion(
        onSaved: () -> Unit = {}
    ) {

        val state =
            _uiState.value

        if (
            !state.isCompleted ||
            state.isSaved
        ) {
            return
        }

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            val completion =
                Grounding54321CompletionEntity(
                    userId =
                        currentUserId,

                    reflection =
                        state.reflection.trim(),

                    completedAt =
                        System.currentTimeMillis()
                )

            repository.saveCompletion(
                completion
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )

            _uiState.value =
                _uiState.value.copy(
                    isSaved = true
                )

            onSaved()
        }
    }


    /*
     * --------------------------------------------------
     * RESET
     * --------------------------------------------------
     */

    fun reset() {

        _uiState.value =
            Grounding54321UiState()
    }
}

