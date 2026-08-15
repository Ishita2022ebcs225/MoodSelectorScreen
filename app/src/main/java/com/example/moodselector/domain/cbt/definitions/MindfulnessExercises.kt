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
 * interactive guided experience is implemented in the presentation layer.
 */
object MindfulnessExercises {

    /**
     * All currently available Mindfulness activities.
     *
     * The activities are intentionally kept distinct to avoid
     * unnecessary overlap between exercises.
     */
    val activities: List<CBTActivity>
        get() = listOf(
            mindfulMeditation,
            grounding54321
        )


    /**
     * A guided mindfulness meditation that incorporates slow,
     * comfortable breathing to help the user settle before
     * practicing present-moment awareness.
     *
     * Deep breathing is intentionally part of this exercise
     * rather than being a separate activity.
     */
    val mindfulMeditation = CBTActivity(
        id = "mindful_meditation",
        title = "Mindful Meditation",
        description =
            "Use slow, gentle breathing to settle your body, then observe " +
                    "your thoughts, emotions and sensations without judgment.",

        category = CBTCategory.MINDFULNESS,

        steps = listOf(

            ActivityStep(
                id = "meditation_step_1",
                instruction =
                    "Find a comfortable position in a quiet place. " +
                            "Allow your body to settle and gently close your eyes " +
                            "if you feel comfortable.",
                pauseDurationMillis = 5000
            ),

            ActivityStep(
                id = "meditation_step_2",
                instruction =
                    "Take a slow, deep breath in through your nose, " +
                            "allowing your body to relax.",
                pauseDurationMillis = 4000
            ),

            ActivityStep(
                id = "meditation_step_3",
                instruction =
                    "Slowly breathe out through your mouth. " +
                            "Let your shoulders and body soften as you exhale.",
                pauseDurationMillis = 6000
            ),

            ActivityStep(
                id = "meditation_step_4",
                instruction =
                    "Continue taking slow, comfortable breaths. " +
                            "Notice the movement of your chest or stomach " +
                            "as you breathe.",
                pauseDurationMillis = 8000
            ),

            ActivityStep(
                id = "meditation_step_5",
                instruction =
                    "Bring your attention to the present moment. " +
                            "Notice any thoughts, feelings or sensations " +
                            "that arise without judging them.",
                pauseDurationMillis = 8000
            ),

            ActivityStep(
                id = "meditation_step_6",
                instruction =
                    "If your attention wanders, gently acknowledge it " +
                            "and return your attention to your breathing.",
                pauseDurationMillis = 8000
            ),

            ActivityStep(
                id = "meditation_step_7",
                instruction =
                    "Take one final slow breath. Notice how your body " +
                            "and mind feel now, then gently open your eyes " +
                            "when you are ready.",
                pauseDurationMillis = 6000
            )
        ),

        benefits = listOf(
            "Promotes relaxation",
            "Reduces stress and anxiety",
            "Builds present-moment awareness",
            "Helps reduce rumination",
            "Improves emotional awareness"
        ),

        recommendedFor = listOf(
            RecommendationTarget.MILD_ANXIETY,
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.SEVERE_ANXIETY,
            RecommendationTarget.STRESS,
            RecommendationTarget.OVERWHELM,
            RecommendationTarget.BURNOUT
        )
    )


    /**
     * A grounding exercise that uses the five senses
     * to reconnect with the present moment.
     *
     * This exercise is intentionally different from Mindful Meditation:
     * rather than focusing primarily on breathing and internal awareness,
     * it actively engages the user's external senses.
     */
    val grounding54321 = CBTActivity(
        id = "grounding_54321",
        title = "5-4-3-2-1 Grounding",
        description =
            "Reconnect with the present by engaging each of your five senses.",

        category = CBTCategory.MINDFULNESS,

        steps = listOf(

            ActivityStep(
                id = "grounding_step_1",
                instruction =
                    "Name five things you can see around you.",
                pauseDurationMillis = 8000
            ),

            ActivityStep(
                id = "grounding_step_2",
                instruction =
                    "Notice four things you can touch or feel.",
                pauseDurationMillis = 8000
            ),

            ActivityStep(
                id = "grounding_step_3",
                instruction =
                    "Identify three things you can hear.",
                pauseDurationMillis = 8000
            ),

            ActivityStep(
                id = "grounding_step_4",
                instruction =
                    "Notice two things you can smell.",
                pauseDurationMillis = 8000
            ),

            ActivityStep(
                id = "grounding_step_5",
                instruction =
                    "Identify one thing you can taste or imagine tasting.",
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

