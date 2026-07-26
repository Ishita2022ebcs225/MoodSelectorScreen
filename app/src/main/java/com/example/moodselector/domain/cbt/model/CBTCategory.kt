package com.example.moodselector.domain.cbt.model

/**
 * Categories of Cognitive Behavioral Therapy (CBT) exercises.
 *
 * These categories are used to organize exercises,
 * support filtering, and power personalized recommendations.
 */
enum class CBTCategory {

    /**
     * Identifying and challenging unhelpful thoughts.
     */
    COGNITIVE,

    /**
     * Encourages healthy behaviors and positive daily activities.
     */
    BEHAVIORAL,

    /**
     * Promotes present-moment awareness and acceptance, reduces stress
     */
    MINDFULNESS,
}