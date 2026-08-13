package com.example.moodselector.presentations.cbt.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.FiveMinuteStarterCompletionEntity
import com.example.moodselector.domain.repository.FiveMinuteStarterCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiveMinuteStarterViewModel @Inject constructor(
    private val repository: FiveMinuteStarterCompletionRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * COMPLETIONS
     * --------------------------------------------------
     */

    val completions:
            Flow<List<FiveMinuteStarterCompletionEntity>> =
        repository.getAllCompletions()


    /*
     * --------------------------------------------------
     * COMPLETION COUNT
     * --------------------------------------------------
     */

    val completionCount:
            Flow<Int> =
        repository.getCompletionCount()


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

        viewModelScope.launch {

            val completion =
                FiveMinuteStarterCompletionEntity(

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

        viewModelScope.launch {

            repository.deleteAllCompletions()
        }
    }
}