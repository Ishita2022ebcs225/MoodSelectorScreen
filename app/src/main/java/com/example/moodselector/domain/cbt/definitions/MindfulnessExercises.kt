package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.ActivityStep
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.domain.cbt.model.RecommendationTarget

/**
 * Collection of Mindfulness activities.
 *
 * These activities are inspired by evidence-based mindfulness practices
 * commonly incorporated into Cognitive Behavioral Therapy (CBT).
 *
 * The metadata defined here describes each activity, while the
 * interactive guided experience will be implemented later
 * in the presentation layer.
 */
object MindfulnessExercises {

    /**
     * All mindfulness activities.
     */
    val activities: List<CBTActivity>
        get() = listOf(
            deepBreathing,
            mindfulnessMeditation,
            bodyScan,
            grounding54321
        )

    /**
     * A guided breathing exercise that helps calm the nervous system
     * and reduce feelings of stress or anxiety.
     */
    val deepBreathing = CBTActivity(
        id = "deep_breathing",
        title = "Deep Breathing",
        description = "Slow your breathing and bring your attention to the present moment.",
        category = CBTCategory.MINDFULNESS,
        steps = listOf(
            ActivityStep(
                id = "deep_breathing_step_1",
                instruction = "Sit comfortably and gently close your eyes if you feel comfortable.",
                pauseDurationMillis = 3000
            ),
            ActivityStep(
                id = "deep_breathing_step_2",
                instruction = "Take a slow, deep breath in through your nose.",
                pauseDurationMillis = 4000
            ),
            ActivityStep(
                id = "deep_breathing_step_3",
                instruction = "Hold your breath for a brief moment.",
                pauseDurationMillis = 2000
            ),
            ActivityStep(
                id = "deep_breathing_step_4",
                instruction = "Slowly breathe out through your mouth.",
                pauseDurationMillis = 6000
            ),
            ActivityStep(
                id = "deep_breathing_step_5",
                instruction = "Continue this breathing pattern for several cycles while noticing how your body feels.",
                pauseDurationMillis = 5000
            )
        ),
        benefits = listOf(
            "Reduces stress",
            "Helps manage anxiety",
            "Promotes relaxation",
            "Improves focus"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_ANXIETY,
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.SEVERE_ANXIETY,
            RecommendationTarget.STRESS,
            RecommendationTarget.OVERWHELM
        )
    )

    /**
     * Encourages non-judgmental awareness of thoughts,
     * emotions and bodily sensations.
     */
    val mindfulnessMeditation = CBTActivity(
        id = "mindfulness_meditation",
        title = "Mindfulness Meditation",
        description = "Observe your thoughts and emotions without judging or trying to change them.",
        category = CBTCategory.MINDFULNESS,
        steps = listOf(
            ActivityStep(
                id = "meditation_step_1",
                instruction = "Find a quiet place and sit in a comfortable position.",
                pauseDurationMillis = 5000
            ),
            ActivityStep(
                id = "meditation_step_2",
                instruction = "Bring your attention to your breathing.",
                pauseDurationMillis = 5000
            ),
            ActivityStep(
                id = "meditation_step_3",
                instruction = "Notice any thoughts that arise without judging them.",
                pauseDurationMillis = 7000
            ),
            ActivityStep(
                id = "meditation_step_4",
                instruction = "Allow the thoughts to pass and gently return your focus to your breath.",
                pauseDurationMillis = 7000
            ),
            ActivityStep(
                id = "meditation_step_5",
                instruction = "Take one final deep breath before opening your eyes.",
                pauseDurationMillis = 4000
            )
        ),
        benefits = listOf(
            "Improves emotional awareness",
            "Reduces rumination",
            "Builds mindfulness skills",
            "Promotes inner calm"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_ANXIETY,
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.STRESS,
            RecommendationTarget.OVERWHELM,
            RecommendationTarget.BURNOUT
        )
    )

    /**
     * Gradually brings awareness to different parts
     * of the body to promote relaxation.
     */
    val bodyScan = CBTActivity(
        id = "body_scan",
        title = "Body Scan",
        description = "Bring gentle awareness to each part of your body and notice physical sensations without judgment.",
        category = CBTCategory.MINDFULNESS,
        steps = listOf(
            ActivityStep(
                id = "body_scan_step_1",
                instruction = "Lie down or sit comfortably and take a slow breath.",
                pauseDurationMillis = 5000
            ),
            ActivityStep(
                id = "body_scan_step_2",
                instruction = "Notice the sensations in your feet and legs.",
                pauseDurationMillis = 7000
            ),
            ActivityStep(
                id = "body_scan_step_3",
                instruction = "Move your attention to your stomach, chest and back.",
                pauseDurationMillis = 7000
            ),
            ActivityStep(
                id = "body_scan_step_4",
                instruction = "Bring awareness to your shoulders, arms and hands.",
                pauseDurationMillis = 7000
            ),
            ActivityStep(
                id = "body_scan_step_5",
                instruction = "Finally, notice your neck, face and head before taking another deep breath.",
                pauseDurationMillis = 7000
            )
        ),
        benefits = listOf(
            "Promotes relaxation",
            "Improves body awareness",
            "Reduces physical tension",
            "Encourages present-moment awareness"
        ),
        recommendedFor = listOf(
            RecommendationTarget.STRESS,
            RecommendationTarget.BURNOUT,
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.OVERWHELM
        )
    )

    /**
     * A grounding exercise that uses the five senses
     * to reconnect with the present moment.
     */
    val grounding54321 = CBTActivity(
        id = "grounding_54321",
        title = "5-4-3-2-1 Grounding",
        description = "Reconnect with the present by engaging each of your five senses.",
        category = CBTCategory.MINDFULNESS,
        steps = listOf(
            ActivityStep(
                id = "grounding_step_1",
                instruction = "Name five things you can see around you.",
                pauseDurationMillis = 8000
            ),
            ActivityStep(
                id = "grounding_step_2",
                instruction = "Notice four things you can touch.",
                pauseDurationMillis = 8000
            ),
            ActivityStep(
                id = "grounding_step_3",
                instruction = "Identify three things you can hear.",
                pauseDurationMillis = 8000
            ),
            ActivityStep(
                id = "grounding_step_4",
                instruction = "Notice two things you can smell.",
                pauseDurationMillis = 8000
            ),
            ActivityStep(
                id = "grounding_step_5",
                instruction = "Identify one thing you can taste or imagine tasting.",
                pauseDurationMillis = 8000
            )
        ),
        benefits = listOf(
            "Reduces anxiety",
            "Interrupts spiraling thoughts",
            "Improves present-moment awareness",
            "Promotes emotional grounding"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.SEVERE_ANXIETY,
            RecommendationTarget.STRESS,
            RecommendationTarget.OVERWHELM
        )
    )
}