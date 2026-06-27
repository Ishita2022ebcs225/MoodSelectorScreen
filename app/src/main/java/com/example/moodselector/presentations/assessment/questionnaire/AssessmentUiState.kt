package com.example.moodselector.presentations.assessment.questionnaire

import com.example.moodselector.domain.assessment.model.AssessmentDefinition
import com.example.moodselector.domain.assessment.model.AssessmentQuestion

data class AssessmentUiState(

    val assessment: AssessmentDefinition? = null,

    val currentQuestionIndex: Int = 0,

    /**
     * Maps question ID to selected score.
     * Example:
     * Question 1 -> 2 (More than half the days)
     */
    val selectedAnswers: Map<Int, Int> = emptyMap(),

    val isCompleted: Boolean = false,

    val totalScore: Int = 0,

    val severity: String = ""

) {

    val currentQuestion: AssessmentQuestion?
        get() = assessment
            ?.questions
            ?.getOrNull(currentQuestionIndex)
}