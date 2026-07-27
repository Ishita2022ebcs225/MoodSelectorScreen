package com.example.moodselector.domain.assessment.utils

import com.example.moodselector.domain.assessment.model.AssessmentSeverity

object GAD7SeverityCalculator {

    /**
     * Returns the clinical severity for a GAD-7 score.
     */
    fun getSeverity(score: Int): AssessmentSeverity {
        return when (score) {
            in 0..4 -> AssessmentSeverity.MINIMAL
            in 5..9 -> AssessmentSeverity.MILD
            in 10..14 -> AssessmentSeverity.MODERATE
            in 15..21 -> AssessmentSeverity.SEVERE
            else -> throw IllegalArgumentException("Invalid GAD-7 score: $score")
        }
    }

    /**
     * Returns a user-friendly severity label.
     */
    fun getSeverityLabel(score: Int): String {
        return when (getSeverity(score)) {
            AssessmentSeverity.MINIMAL -> "Minimal anxiety"
            AssessmentSeverity.MILD -> "Mild anxiety"
            AssessmentSeverity.MODERATE -> "Moderate anxiety"
            AssessmentSeverity.MODERATELY_SEVERE -> "Severe anxiety"
            AssessmentSeverity.SEVERE -> "Severe anxiety"
        }
    }

    fun requiresFurtherEvaluation(score: Int): Boolean {
        return score >= 10
    }
}