package com.example.moodselector.presentations.cbt.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.domain.cbt.engine.CBTRecommendationEngine
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.repository.AssessmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class CBTUiState(
    val activities: List<CBTActivity> = emptyList(),
    val isLoading: Boolean = true,
    val hasAssessmentResult: Boolean = false
)

@HiltViewModel
class CBTViewModel @Inject constructor(
    private val assessmentRepository: AssessmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CBTUiState())

    val uiState: StateFlow<CBTUiState> = _uiState.asStateFlow()

    init {
        observeAssessmentResult()
    }

    private fun observeAssessmentResult() {
        viewModelScope.launch {

            assessmentRepository
                .getLatestResult()
                .collectLatest { result ->

                    if (result == null) {
                        _uiState.value = CBTUiState(
                            activities = emptyList(),
                            isLoading = false,
                            hasAssessmentResult = false
                        )

                        return@collectLatest
                    }

                    val recommendedActivities =
                        CBTRecommendationEngine.recommend(
                            phq9Severity = result.phq9Severity,
                            gad7Severity = result.gad7Severity
                        )

                    _uiState.value = CBTUiState(
                        activities = recommendedActivities,
                        isLoading = false,
                        hasAssessmentResult = true
                    )
                }
        }
    }
}