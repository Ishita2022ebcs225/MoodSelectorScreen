package com.example.moodselector.domain.assessment.utils

object PHQ9SeverityCalculator {

    fun getSeverity(score: Int): String {
        return when (score) {
            in 0..4 -> "Minimal depression"
            in 5..9 -> "Mild depression"
            in 10..14 -> "Moderate depression"
            in 15..19 -> "Moderately severe depression"
            in 20..27 -> "Severe depression"
            else -> "Invalid score"
        }
    }

    fun requiresFurtherEvaluation(score: Int): Boolean {
        return score >= 10
    }
}