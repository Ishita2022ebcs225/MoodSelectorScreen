package com.example.moodselector.presentations.assessment.questionnaire

import androidx.lifecycle.ViewModel
import com.example.moodselector.data.assessment.provider.AssessmentDefinitionProvider
import com.example.moodselector.domain.assessment.model.AssessmentType
import com.example.moodselector.domain.assessment.utils.GAD7SeverityCalculator
import com.example.moodselector.domain.assessment.utils.PHQ9SeverityCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AssessmentViewModel @Inject constructor(
    private val assessmentDefinitionProvider: AssessmentDefinitionProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AssessmentUiState()
    )

    val uiState: StateFlow<AssessmentUiState> =
        _uiState.asStateFlow()

    fun loadAssessment(
        type: AssessmentType
    ) {

        val definition =
            assessmentDefinitionProvider
                .getAssessmentDefinition(type)

        _uiState.value = AssessmentUiState(
            assessment = definition
        )
    }

    fun selectAnswer(
        questionId: Int,
        score: Int
    ) {

        val updatedAnswers =
            _uiState.value.selectedAnswers.toMutableMap()

        updatedAnswers[questionId] = score

        _uiState.update {
            it.copy(
                selectedAnswers = updatedAnswers
            )
        }
    }

    fun nextQuestion() {

        val assessment =
            _uiState.value.assessment ?: return

        val currentIndex =
            _uiState.value.currentQuestionIndex

        if (currentIndex < assessment.questions.lastIndex) {

            _uiState.update {
                it.copy(
                    currentQuestionIndex = currentIndex + 1
                )
            }

        } else {

            completeAssessment()
        }
    }

    fun previousQuestion() {

        val currentIndex =
            _uiState.value.currentQuestionIndex

        if (currentIndex > 0) {

            _uiState.update {
                it.copy(
                    currentQuestionIndex = currentIndex - 1
                )
            }
        }
    }

    private fun completeAssessment() {

        val assessment =
            _uiState.value.assessment ?: return

        val score =
            _uiState.value.selectedAnswers.values.sum()

        val severity = when (assessment.type) {

            AssessmentType.PHQ9 ->
                PHQ9SeverityCalculator.getSeverity(score)

            AssessmentType.GAD7 ->
                GAD7SeverityCalculator.getSeverity(score)
        }

        _uiState.update {

            it.copy(
                isCompleted = true,
                totalScore = score,
                severity = severity
            )
        }
    }

    fun restartAssessment() {

        val assessment =
            _uiState.value.assessment

        _uiState.value = AssessmentUiState(
            assessment = assessment
        )
    }
}