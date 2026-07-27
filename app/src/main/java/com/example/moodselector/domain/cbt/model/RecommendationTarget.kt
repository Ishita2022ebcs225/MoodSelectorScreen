package com.example.moodselector.domain.cbt.model

/**
 * Represents assessment outcomes or user concerns
 * that a CBT activity may be recommended for.
 */
enum class RecommendationTarget {

    // Depression
    MILD_DEPRESSION,
    MODERATE_DEPRESSION,
    SEVERE_DEPRESSION,

    // Anxiety
    MILD_ANXIETY,
    MODERATE_ANXIETY,
    SEVERE_ANXIETY,

    // Common concerns
    LOW_MOTIVATION,
    PROCRASTINATION,
    SOCIAL_WITHDRAWAL,
    STRESS,
    OVERWHELM,
    BURNOUT
}