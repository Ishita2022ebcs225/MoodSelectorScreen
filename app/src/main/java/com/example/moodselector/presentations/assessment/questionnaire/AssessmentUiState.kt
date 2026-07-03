package com.example.moodselector.presentations.assessment.questionnaire

import com.example.moodselector.domain.assessment.model.AssessmentDefinition
import com.example.moodselector.domain.assessment.model.AssessmentQuestion
import com.example.moodselector.domain.assessment.model.AssessmentType

data class AssessmentUiState(

    /**
     * Currently displayed assessment.
     * (PHQ-9 initially, then GAD-7)
     */
    val assessment: AssessmentDefinition? = null,

    /**
     * Which assessment is currently active.
     */
    val currentAssessmentType: AssessmentType = AssessmentType.PHQ9,

    /**
     * Current question index within the active assessment.
     */
    val currentQuestionIndex: Int = 0,

    /**
     * Answers for the currently displayed assessment.
     *
     * Maps:
     * Question ID -> Selected score
     */
    val selectedAnswers: Map<Int, Int> = emptyMap(),

    /**
     * PHQ-9 results
     */
    val phq9Score: Int = 0,

    val phq9Severity: String = "",

    /**
     * GAD-7 results
     */
    val gad7Score: Int = 0,

    val gad7Severity: String = "",

    /**
     * Whether PHQ-9 has been completed.
     */
    val phq9Completed: Boolean = false,

    /**
     * Whether GAD-7 has been completed.
     */
    val gad7Completed: Boolean = false,

    /**
     * Entire assessment session completed.
     */
    val isCompleted: Boolean = false

) {

    /**
     * Currently displayed question.
     */
    val currentQuestion: AssessmentQuestion?
        get() = assessment
            ?.questions
            ?.getOrNull(currentQuestionIndex)

    /**
     * Progress through the current assessment.
     */
    val totalQuestions: Int
        get() = assessment
            ?.questions
            ?.size
            ?: 0

    /**
     * Returns true when currently displaying PHQ-9.
     */
    val isPHQ9: Boolean
        get() = currentAssessmentType == AssessmentType.PHQ9

    /**
     * Returns true when currently displaying GAD-7.
     */
    val isGAD7: Boolean
        get() = currentAssessmentType == AssessmentType.GAD7
}