package com.example.moodselector.domain.assessment.model

/**
 * Represents the severity of an assessment result.
 *
 * This common model is shared by different assessment tools
 * (e.g., PHQ-9 and GAD-7) to enable consistent recommendations
 * throughout the application.
 */
enum class AssessmentSeverity {

    /**
     * Little to no clinically significant symptoms.
     */
    MINIMAL,

    /**
     * Mild symptoms.
     */
    MILD,

    /**
     * Moderate symptoms.
     */
    MODERATE,

    /**
     * Moderately severe symptoms.
     */
    MODERATELY_SEVERE,

    /**
     * Severe symptoms.
     */
    SEVERE
}