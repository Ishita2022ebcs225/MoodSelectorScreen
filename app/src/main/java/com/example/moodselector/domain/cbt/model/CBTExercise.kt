package com.example.moodselector.domain.cbt.model

/**
 * Represents a single CBT exercise.
 */
data class CBTExercise(
    val id: String,
    val title: String,
    val description: String,
    val category: CBTCategory,

    /**
     * Step-by-step guidance shown to the user.
     */
    val instructions: List<String>,

    /**
     * Expected benefits after completing this exercise.
     */
    val benefits: List<String>,

    /**
     * Conditions or assessment outcomes this exercise is recommended for.
     *
     * Examples:
     * - "Mild Anxiety"
     * - "Moderate Depression"
     * - "High Stress"
     */
    val recommendedFor: List<String>,

    /**
     * Used for tracking user progress.
     * This defaults to false until the exercise is completed.
     */
    val isCompleted: Boolean = false
)