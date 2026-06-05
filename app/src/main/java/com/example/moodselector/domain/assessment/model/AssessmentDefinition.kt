package com.example.moodselector.domain.assessment.model

data class AssessmentDefinition(
    val type: AssessmentType,
    val title: String,
    val instructions: String,
    val questions: List<AssessmentQuestion>
)