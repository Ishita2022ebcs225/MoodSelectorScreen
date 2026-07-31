package com.example.moodselector.domain.assessment.model

/**
 * Represents the severity of an assessment result.
 *
 * This common model is shared by different assessment tools
 * (e.g., PHQ-9 and GAD-7) to enable consistent recommendations
 * throughout the application.
 */
enum class AssessmentSeverity(
    val displayName: String
) {

    /**
     * Little to no clinically significant symptoms.
     */
    MINIMAL("Minimal"),

    /**
     * Mild symptoms.
     */
    MILD("Mild"),

    /**
     * Moderate symptoms.
     */
    MODERATE("Moderate"),

    /**
     * Moderately severe symptoms.
     */
    MODERATELY_SEVERE("Moderately Severe"),

    /**
     * Severe symptoms.
     */
    SEVERE("Severe")
}