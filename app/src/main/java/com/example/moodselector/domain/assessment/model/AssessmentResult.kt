package com.example.moodselector.domain.assessment.model

data class AssessmentResult(
    val assessmentType: AssessmentType,
    val totalScore: Int,
    val severity: String,
    val completedAt: Long = System.currentTimeMillis()
)