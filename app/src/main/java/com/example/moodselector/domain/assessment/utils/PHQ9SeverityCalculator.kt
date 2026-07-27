package com.example.moodselector.domain.assessment.utils

import com.example.moodselector.domain.assessment.model.AssessmentSeverity

object PHQ9SeverityCalculator {

    /**
     * Returns the clinical severity for a PHQ-9 score.
     */
    fun getSeverity(score: Int): AssessmentSeverity {
        return when (score) {
            in 0..4 -> AssessmentSeverity.MINIMAL
            in 5..9 -> AssessmentSeverity.MILD
            in 10..14 -> AssessmentSeverity.MODERATE
            in 15..19 -> AssessmentSeverity.MODERATELY_SEVERE
            in 20..27 -> AssessmentSeverity.SEVERE
            else -> throw IllegalArgumentException("Invalid PHQ-9 score: $score")
        }
    }

    /**
     * Returns a user-friendly severity label.
     */
    fun getSeverityLabel(score: Int): String {
        return when (getSeverity(score)) {
            AssessmentSeverity.MINIMAL -> "Minimal depression"
            AssessmentSeverity.MILD -> "Mild depression"
            AssessmentSeverity.MODERATE -> "Moderate depression"
            AssessmentSeverity.MODERATELY_SEVERE -> "Moderately severe depression"
            AssessmentSeverity.SEVERE -> "Severe depression"
        }
    }

    fun requiresFurtherEvaluation(score: Int): Boolean {
        return score >= 10
    }
}