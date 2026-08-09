package com.example.moodselector.domain.cbt.engine

import com.example.moodselector.domain.assessment.model.AssessmentSeverity
import com.example.moodselector.domain.cbt.definitions.CBTActivityProvider
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory

/**
 * Generates personalized CBT exercise recommendations based on
 * the user's assessment severities.
 *
 * The engine is completely independent of Android framework classes
 * and only relies on domain models.
 */
object CBTRecommendationEngine {

    /**
     * Returns a personalized list of CBT activities based on the
     * user's PHQ-9 and GAD-7 severity levels.
     *
     * Duplicate activities are removed automatically.
     */
    fun recommend(
        phq9Severity: AssessmentSeverity,
        gad7Severity: AssessmentSeverity
    ): List<CBTActivity> {

        val recommendedActivities = mutableListOf<CBTActivity>()

        recommendedActivities += depressionActivities(phq9Severity)
        recommendedActivities += anxietyActivities(gad7Severity)

        return recommendedActivities.distinctBy { it.id }
    }

    /**
     * Selects activities that target depressive symptoms.
     */
    private fun depressionActivities(
        severity: AssessmentSeverity
    ): List<CBTActivity> {

        return when (severity) {

            AssessmentSeverity.MINIMAL ->
                emptyList()

            AssessmentSeverity.MILD ->
                CBTActivityProvider.getActivitiesByCategory(
                    CBTCategory.BEHAVIORAL
                )

            AssessmentSeverity.MODERATE ->
                CBTActivityProvider.getActivitiesByCategory(
                    CBTCategory.BEHAVIORAL
                ) +
                        CBTActivityProvider.getActivitiesByCategory(
                            CBTCategory.MINDFULNESS
                        )

            AssessmentSeverity.MODERATELY_SEVERE,
            AssessmentSeverity.SEVERE ->
                CBTActivityProvider.getActivitiesByCategory(
                    CBTCategory.BEHAVIORAL
                ) +
                        CBTActivityProvider.getActivitiesByCategory(
                            CBTCategory.MINDFULNESS
                        ) +
                        CBTActivityProvider.getActivitiesByCategory(
                            CBTCategory.COGNITIVE
                        )
        }
    }

    /**
     * Selects activities that target anxiety symptoms.
     */
    private fun anxietyActivities(
        severity: AssessmentSeverity
    ): List<CBTActivity> {

        return when (severity) {

            AssessmentSeverity.MINIMAL ->
                emptyList()

            AssessmentSeverity.MILD ->
                CBTActivityProvider.getActivitiesByCategory(
                    CBTCategory.MINDFULNESS
                )

            AssessmentSeverity.MODERATE ->
                CBTActivityProvider.getActivitiesByCategory(
                    CBTCategory.MINDFULNESS
                ) +
                        CBTActivityProvider.getActivitiesByCategory(
                            CBTCategory.COGNITIVE
                        )

            AssessmentSeverity.MODERATELY_SEVERE,
            AssessmentSeverity.SEVERE ->
                CBTActivityProvider.getActivitiesByCategory(
                    CBTCategory.MINDFULNESS
                ) +
                        CBTActivityProvider.getActivitiesByCategory(
                            CBTCategory.COGNITIVE
                        ) +
                        CBTActivityProvider.getActivitiesByCategory(
                            CBTCategory.BEHAVIORAL
                        )
        }
    }
}