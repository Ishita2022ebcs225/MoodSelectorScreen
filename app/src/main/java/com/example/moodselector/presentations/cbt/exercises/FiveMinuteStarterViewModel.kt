package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.FiveMinuteStarterCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiveMinuteStarterViewModel @Inject constructor(
    private val repository: FiveMinuteStarterCompletionRepository,
    private val authRepository: AuthRepository,
    private val cloudBackupRepository: CloudBackupRepository
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
     * COMPLETIONS
     * --------------------------------------------------
     */

    val completions:
            Flow<List<FiveMinuteStarterCompletionEntity>> =
        repository.getAllCompletions(
            userId = userId ?: ""
        )


    /*
     * --------------------------------------------------
     * COMPLETION COUNT
     * --------------------------------------------------
     */

    val completionCount:
            Flow<Int> =
        repository.getCompletionCount(
            userId = userId ?: ""
        )


    /*
     * --------------------------------------------------
     * COMPLETE EXERCISE
     * --------------------------------------------------
     *
     * This is the ONLY method the completion screen
     * needs in order to save a completed exercise.
     *
     * Nothing is saved before the user explicitly
     * confirms completion.
     */

    fun completeExercise(
        task: String,
        firstStep: String,
        outcome: String,
        reflection: String,
        onCompleted: () -> Unit = {}
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            val completion =
                FiveMinuteStarterCompletionEntity(

                    userId =
                        currentUserId,

                    task = task,

                    firstStep = firstStep,

                    outcome = outcome,

                    reflection = reflection,

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
             * Cloud backup is therefore best-effort and does
             * not prevent the exercise from being considered
             * completed if Firestore is temporarily unavailable.
             */

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )

            onCompleted()
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ONE COMPLETION
     * --------------------------------------------------
     */

    fun deleteCompletion(
        completion: FiveMinuteStarterCompletionEntity
    ) {

        viewModelScope.launch {

            repository.deleteCompletion(
                completion
            )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ALL COMPLETIONS
     * --------------------------------------------------
     */

    fun deleteAllCompletions() {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteAllCompletions(
                currentUserId
            )
        }
    }
}

