package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.MindfulMeditationCompletionEntity
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.MindfulMeditationCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MindfulMeditationUiState(

    /*
     * Current guided meditation step.
     */
    val currentStep: Int = 0,

    /*
     * Whether the meditation is currently running.
     */
    val isRunning: Boolean = false,

    /*
     * Whether the user has started the meditation.
     *
     * This is different from isRunning because a meditation
     * can be paused.
     */
    val hasStarted: Boolean = false,

    /*
     * Remaining seconds in the current guided step.
     *
     * Presentation-only.
     */
    val remainingSeconds: Int = 0,

    /*
     * Number of breathing cycles completed.
     *
     * Presentation-only.
     */
    val breathingCycles: Int = 0,

    /*
     * Total elapsed meditation time.
     *
     * Presentation-only.
     */
    val elapsedSeconds: Int = 0,

    /*
     * Whether the meditation has actually finished.
     *
     * This becomes true only when the narration reaches
     * the end of the meditation.
     */
    val isCompleted: Boolean = false,

    /*
     * User reflection.
     */
    val reflection: String = "",

    /*
     * Whether the completed meditation has already
     * been persisted.
     */
    val isSaved: Boolean = false
)

@HiltViewModel
class MindfulMeditationViewModel @Inject constructor(
    private val repository:
    MindfulMeditationCompletionRepository,
    private val authRepository:
    AuthRepository,
    private val cloudBackupRepository:
    CloudBackupRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            MindfulMeditationUiState()
        )

    val uiState: StateFlow<MindfulMeditationUiState> =
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
     * START MEDITATION
     * --------------------------------------------------
     *
     * Starts a completely new meditation session.
     *
     * This resets the presentation state.
     *
     * No persistence occurs here.
     */

    fun startMeditation() {

        _uiState.value =
            MindfulMeditationUiState(
                currentStep = 0,
                isRunning = true,
                hasStarted = true,
                remainingSeconds = STEP_DURATIONS[0],
                elapsedSeconds = 0,
                breathingCycles = 0,
                isCompleted = false,
                reflection = "",
                isSaved = false
            )
    }


    /*
     * --------------------------------------------------
     * PAUSE MEDITATION
     * --------------------------------------------------
     *
     * Pausing does not reset the meditation.
     *
     * The current audio position is maintained by the
     * MediaPlayer in the screen.
     *
     * No completion is saved.
     */

    fun pauseMeditation() {

        val state =
            _uiState.value

        if (
            !state.hasStarted ||
            state.isCompleted ||
            !state.isRunning
        ) {
            return
        }

        _uiState.value =
            state.copy(
                isRunning = false
            )
    }


    /*
     * --------------------------------------------------
     * RESUME MEDITATION
     * --------------------------------------------------
     *
     * Resumes an existing paused meditation.
     *
     * It does NOT reset the meditation position.
     */

    fun resumeMeditation() {

        val state =
            _uiState.value

        if (
            !state.hasStarted ||
            state.isCompleted ||
            state.isRunning
        ) {
            return
        }

        _uiState.value =
            state.copy(
                isRunning = true
            )
    }


    /*
     * --------------------------------------------------
     * TOGGLE MEDITATION
     * --------------------------------------------------
     *
     * Convenience method for the screen.
     */

    fun toggleMeditation() {

        val state =
            _uiState.value

        when {

            !state.hasStarted -> {
                startMeditation()
            }

            state.isCompleted -> {
                return
            }

            state.isRunning -> {
                pauseMeditation()
            }

            else -> {
                resumeMeditation()
            }
        }
    }


    /*
     * --------------------------------------------------
     * MARK MEDITATION COMPLETE
     * --------------------------------------------------
     *
     * Called by the screen when the actual narration
     * reaches the end.
     *
     * This does NOT persist anything.
     *
     * Persistence happens only after the user explicitly
     * presses "Complete Meditation".
     */

    fun markCompleted() {

        val state =
            _uiState.value

        if (
            !state.hasStarted ||
            state.isCompleted
        ) {
            return
        }

        _uiState.value =
            state.copy(
                isRunning = false,
                hasStarted = true,
                currentStep = STEP_DURATIONS.lastIndex,
                remainingSeconds = 0,
                isCompleted = true
            )
    }


    /*
     * --------------------------------------------------
     * ADVANCE STEP
     * --------------------------------------------------
     *
     * Retained for compatibility with the guided-step
     * implementation.
     */

    fun advanceStep() {

        val state =
            _uiState.value

        if (
            !state.isRunning ||
            state.isCompleted
        ) {
            return
        }

        val newBreathingCycles =
            if (state.currentStep == 2) {
                state.breathingCycles + 1
            } else {
                state.breathingCycles
            }

        val nextStep =
            state.currentStep + 1

        if (nextStep >= STEP_DURATIONS.size) {

            _uiState.value =
                state.copy(
                    isRunning = false,
                    currentStep =
                        STEP_DURATIONS.lastIndex,
                    remainingSeconds = 0,
                    breathingCycles =
                        newBreathingCycles,
                    isCompleted = true
                )

            return
        }

        _uiState.value =
            state.copy(
                currentStep = nextStep,
                remainingSeconds =
                    STEP_DURATIONS[nextStep],
                breathingCycles =
                    newBreathingCycles
            )
    }


    /*
     * --------------------------------------------------
     * UPDATE TIMER
     * --------------------------------------------------
     */

    fun updateRemainingSeconds(
        seconds: Int
    ) {

        if (!_uiState.value.isRunning) {
            return
        }

        _uiState.value =
            _uiState.value.copy(
                remainingSeconds =
                    seconds.coerceAtLeast(0)
            )
    }


    /*
     * --------------------------------------------------
     * UPDATE ELAPSED TIME
     * --------------------------------------------------
     */

    fun updateElapsedSeconds(
        seconds: Int
    ) {

        if (!_uiState.value.isRunning) {
            return
        }

        _uiState.value =
            _uiState.value.copy(
                elapsedSeconds =
                    seconds.coerceAtLeast(0)
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
     *
     * The meditation must:
     *
     * 1. Have finished.
     * 2. Not already have been saved.
     * 3. Have an authenticated user.
     *
     * Therefore repeatedly pressing the completion
     * button cannot create duplicate records.
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
                MindfulMeditationCompletionEntity(
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

            /*
             * --------------------------------------------------
             * CLOUD BACKUP
             * --------------------------------------------------
             *
             * The completion has already been saved locally.
             * Cloud backup is best-effort and does not prevent
             * the exercise from being considered completed if
             * Firestore is temporarily unavailable.
             */

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
            MindfulMeditationUiState()
    }


    companion object {

        /*
         * Guided meditation step durations in seconds.
         *
         * 0 - Settle
         * 1 - Inhale
         * 2 - Exhale
         * 3 - Continue breathing
         * 4 - Mindful awareness
         * 5 - Return to breath
         * 6 - Closing breath
         */

        val STEP_DURATIONS =
            listOf(
                5,
                4,
                6,
                20,
                20,
                20,
                6
            )


        /*
         * Guided meditation instructions.
         */

        val STEP_INSTRUCTIONS =
            listOf(
                "Find a comfortable position and allow your body to settle.",

                "Take a slow, deep breath in through your nose.",

                "Slowly breathe out through your mouth. Let your body soften as you exhale.",

                "Continue taking slow, comfortable breaths. Notice the movement of your chest or stomach.",

                "Notice any thoughts, feelings or sensations that arise. You don't need to change or judge them.",

                "If your attention wanders, gently acknowledge it and return your attention to your breathing.",

                "Take one final slow breath. Notice how your body and mind feel now."
            )
    }
}

