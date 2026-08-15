package com.example.moodselector.presentations.assessment.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.AssessmentResultEntity
import com.example.moodselector.domain.repository.AssessmentRepository
import com.example.moodselector.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AssessmentResultsViewModel @Inject constructor(
    repository: AssessmentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val userId: String?
        get() = authRepository.currentUser?.uid

    val latestResult: StateFlow<AssessmentResultEntity?> =
        repository
            .getLatestResult(
                userId = userId ?: ""
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
}