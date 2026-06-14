package com.example.moodselector.domain.assessment.utils

object GAD7SeverityCalculator {

    fun getSeverity(score: Int): String {
        return when (score) {
            in 0..4 -> "Minimal anxiety"
            in 5..9 -> "Mild anxiety"
            in 10..14 -> "Moderate anxiety"
            in 15..21 -> "Severe anxiety"
            else -> "Invalid score"
        }
    }

    fun requiresFurtherEvaluation(score: Int): Boolean {
        return score >= 10
    }
}