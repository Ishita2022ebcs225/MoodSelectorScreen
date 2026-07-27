package com.example.moodselector.domain.cbt.engine

import com.example.moodselector.domain.assessment.model.AssessmentSeverity
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.RecommendationTarget
import com.example.moodselector.domain.cbt.utils.CBTActivityProvider

/**
 * Generates personalized CBT activity recommendations
 * based on assessment outcomes.
 *
 * Initially, recommendations are driven by PHQ-9 and GAD-7
 * severity levels. The engine can later be expanded to
 * include mood history, journal entries, hormonal health,
 * and completed activities.
 */
object CBTRecommendationEngine {

    /**
     * Returns a list of recommended CBT activities.
     *
     * @param depressionSeverity PHQ-9 severity.
     * @param anxietySeverity GAD-7 severity.
     */
    fun recommendActivities(
        depressionSeverity: AssessmentSeverity?,
        anxietySeverity: AssessmentSeverity?
    ): List<CBTActivity> {

        val recommendationTargets = mutableSetOf<RecommendationTarget>()

        // Depression recommendations
        when (depressionSeverity) {
            AssessmentSeverity.MINIMAL -> {}

            AssessmentSeverity.MILD ->
                recommendationTargets.add(
                    RecommendationTarget.MILD_DEPRESSION
                )

            AssessmentSeverity.MODERATE ->
                recommendationTargets.add(
                    RecommendationTarget.MODERATE_DEPRESSION
                )

            AssessmentSeverity.MODERATELY_SEVERE,
            AssessmentSeverity.SEVERE ->
                recommendationTargets.add(
                    RecommendationTarget.SEVERE_DEPRESSION
                )

            null -> {}
        }

        // Anxiety recommendations
        when (anxietySeverity) {
            AssessmentSeverity.MINIMAL -> {}

            AssessmentSeverity.MILD ->
                recommendationTargets.add(
                    RecommendationTarget.MILD_ANXIETY
                )

            AssessmentSeverity.MODERATE ->
                recommendationTargets.add(
                    RecommendationTarget.MODERATE_ANXIETY
                )

            AssessmentSeverity.MODERATELY_SEVERE,
            AssessmentSeverity.SEVERE ->
                recommendationTargets.add(
                    RecommendationTarget.SEVERE_ANXIETY
                )

            null -> {}
        }

        return CBTActivityProvider.allActivities
            .filter { activity ->
                activity.recommendedFor.any {
                    it in recommendationTargets
                }
            }
            .distinctBy { it.id }
    }
}