package com.example.moodselector.presentations.assessment.questionnaire

import com.example.moodselector.domain.assessment.model.AssessmentDefinition

data class AssessmentUiState(

    val assessment: AssessmentDefinition? = null,

    val currentQuestionIndex: Int = 0,

    /**
     * Maps question ID to selected score (0-3)
     */
    val selectedAnswers: Map<Int, Int> = emptyMap(),

    val isCompleted: Boolean = false,

    val totalScore: Int = 0,

    val severity: String = ""
)