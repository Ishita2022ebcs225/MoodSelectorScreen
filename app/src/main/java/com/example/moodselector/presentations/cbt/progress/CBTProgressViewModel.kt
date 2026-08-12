package com.example.moodselector.presentations.cbt.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.CBTActivityCompletionEntity
import com.example.moodselector.domain.repository.CBTProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CBTProgressViewModel @Inject constructor(
    private val repository: CBTProgressRepository
) : ViewModel() {

    val completions:
            Flow<List<CBTActivityCompletionEntity>> =
        repository.getAllCompletions()

    val completionCount:
            Flow<Int> =
        repository.getCompletionCount()

    fun saveActivityCompletion(
        activityId: String,
        activityTitle: String,
        activityDescription: String,
        activityName: String,
        activityType: String,
        scheduledWhen: String,
        scheduledWhere: String,
        reflection: String,
        onSaved: () -> Unit
    ) {

        viewModelScope.launch {

            val completion =
                CBTActivityCompletionEntity(

                    activityId = activityId,

                    activityTitle = activityTitle,

                    activityDescription =
                        activityDescription,

                    activityName = activityName,

                    activityType = activityType,

                    scheduledWhen =
                        scheduledWhen,

                    scheduledWhere =
                        scheduledWhere,

                    reflection = reflection,

                    completedAt =
                        System.currentTimeMillis()
                )

            repository.saveCompletion(
                completion
            )

            onSaved()
        }
    }

    fun deleteCompletion(
        completion: CBTActivityCompletionEntity
    ) {

        viewModelScope.launch {

            repository.deleteCompletion(
                completion
            )
        }
    }

    fun deleteAllCompletions() {

        viewModelScope.launch {

            repository.deleteAllCompletions()
        }
    }
}