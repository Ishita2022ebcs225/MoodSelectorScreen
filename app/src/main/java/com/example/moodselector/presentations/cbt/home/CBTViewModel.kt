package com.example.moodselector.presentations.cbt.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.domain.cbt.definitions.CBTActivityProvider
import com.example.moodselector.domain.cbt.engine.CBTRecommendationEngine
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.repository.AssessmentRepository
import com.example.moodselector.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class CBTUiState(
    /**
     * CBT activities recommended based on assessment results.
     */
    val activities: List<CBTActivity> = emptyList(),

    /**
     * Every CBT activity available in the application.
     *
     * This list is independent of assessment results so that
     * users can access any CBT exercise they choose.
     */
    val allActivities: List<CBTActivity> =
        CBTActivityProvider.allActivities,

    val isLoading: Boolean = true,

    val hasAssessmentResult: Boolean = false
)

@HiltViewModel
class CBTViewModel @Inject constructor(
    private val assessmentRepository: AssessmentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            CBTUiState()
        )

    val uiState: StateFlow<CBTUiState> =
        _uiState.asStateFlow()

    /*
     * --------------------------------------------------
     * CURRENT USER ID
     * --------------------------------------------------
     */

    private val userId: String?
        get() = authRepository.currentUser?.uid

    init {
        observeAssessmentResult()
    }

    private fun observeAssessmentResult() {

        viewModelScope.launch {

            assessmentRepository
                .getLatestResult(
                    userId = userId ?: ""
                )
                .collectLatest { result ->

                    if (result == null) {

                        _uiState.value =
                            CBTUiState(
                                activities =
                                    emptyList(),

                                allActivities =
                                    CBTActivityProvider.allActivities,

                                isLoading = false,

                                hasAssessmentResult =
                                    false
                            )

                        return@collectLatest
                    }

                    val recommendedActivities =
                        CBTRecommendationEngine.recommend(
                            phq9Severity =
                                result.phq9Severity,

                            gad7Severity =
                                result.gad7Severity
                        )

                    _uiState.value =
                        CBTUiState(
                            activities =
                                recommendedActivities,

                            allActivities =
                                CBTActivityProvider.allActivities,

                            isLoading = false,

                            hasAssessmentResult =
                                true
                        )
                }
        }
    }
}