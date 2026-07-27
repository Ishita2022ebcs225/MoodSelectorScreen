package com.example.moodselector.domain.cbt.model

/**
 * Represents a single CBT activity.
 */
data class CBTActivity(
    val id: String,
    val title: String,
    val description: String,
    val category: CBTCategory,

    /**
     * Step-by-step guidance shown to the user.
     */
    /**
     * Guided activity steps.
     */
    val steps: List<ActivityStep>,

    /**
     * Expected benefits after completing this activity.
     */
    val benefits: List<String>,

    /**
     * Assessment outcomes or user concerns this activity is recommended for.
     */
    val recommendedFor: List<RecommendationTarget>,

    /**
     * Used for tracking user progress.
     */
    val isCompleted: Boolean = false
)