package com.example.moodselector.domain.assessment.model

data class AssessmentQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val scores: List<Int>
)