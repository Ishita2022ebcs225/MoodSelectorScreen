package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.ActivityStep
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.domain.cbt.model.RecommendationTarget

/**
 * Collection of Cognitive CBT activities.
 *
 * These activities help users understand the relationship between
 * situations, thoughts, beliefs, emotions and behaviours, while
 * developing more balanced and compassionate ways of thinking.
 *
 * The guided exercise experiences are implemented in the
 * presentation layer.
 */
object REBTExercises {

    /**
     * All Cognitive CBT activities.
     */
    val activities: List<CBTActivity>
        get() = listOf(
            abcModel,
            balancedThinking,
            selfCompassionReflection
        )

    /**
     * The ABC Model helps users understand how their beliefs
     * influence their emotional and behavioural responses.
     */
    val abcModel = CBTActivity(
        id = "abc_model",
        title = "ABC Model",
        description =
            "Understand how your thoughts and beliefs influence your emotional reactions.",
        category = CBTCategory.COGNITIVE,
        steps = listOf(
            ActivityStep(
                id = "abc_step_1",
                instruction =
                    "Think of a recent situation that caused emotional distress."
            ),
            ActivityStep(
                id = "abc_step_2",
                instruction =
                    "Describe what happened as objectively as possible."
            ),
            ActivityStep(
                id = "abc_step_3",
                instruction =
                    "Identify the thoughts or beliefs you had about the situation."
            ),
            ActivityStep(
                id = "abc_step_4",
                instruction =
                    "Notice the emotions and behaviours that followed those beliefs."
            ),
            ActivityStep(
                id = "abc_step_5",
                instruction =
                    "Reflect on how a different belief or perspective might have changed your response."
            )
        ),
        benefits = listOf(
            "Improves self-awareness",
            "Recognises thinking patterns",
            "Builds emotional insight",
            "Supports healthier reactions"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_DEPRESSION,
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.MILD_ANXIETY,
            RecommendationTarget.MODERATE_ANXIETY
        )
    )

    /**
     * Helps users examine negative automatic thoughts and develop
     * more realistic and balanced alternatives.
     */
    val balancedThinking = CBTActivity(
        id = "balanced_thinking",
        title = "Balanced Thinking",
        description =
            "Examine an unhelpful thought and develop a more realistic and balanced perspective.",
        category = CBTCategory.COGNITIVE,
        steps = listOf(
            ActivityStep(
                id = "balanced_step_1",
                instruction =
                    "Notice a negative or upsetting automatic thought."
            ),
            ActivityStep(
                id = "balanced_step_2",
                instruction =
                    "Identify any thinking trap or cognitive distortion that may be influencing the thought."
            ),
            ActivityStep(
                id = "balanced_step_3",
                instruction =
                    "Look at the facts that support the thought and the facts that do not support it."
            ),
            ActivityStep(
                id = "balanced_step_4",
                instruction =
                    "Write a more realistic, balanced and compassionate interpretation."
            ),
            ActivityStep(
                id = "balanced_step_5",
                instruction =
                    "Reflect on how this new perspective changes how you feel or what you might do."
            )
        ),
        benefits = listOf(
            "Reduces negative thinking",
            "Recognises cognitive distortions",
            "Encourages realistic perspectives",
            "Builds emotional resilience"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_DEPRESSION,
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.MILD_ANXIETY,
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.STRESS,
            RecommendationTarget.OVERWHELM
        )
    )

    /**
     * Helps users replace harsh self-criticism with a more
     * understanding and compassionate response to themselves.
     */
    val selfCompassionReflection = CBTActivity(
        id = "self_compassion_reflection",
        title = "Self-Compassion Reflection",
        description =
            "Practice responding to yourself with understanding, kindness and encouragement.",
        category = CBTCategory.COGNITIVE,
        steps = listOf(
            ActivityStep(
                id = "compassion_step_1",
                instruction =
                    "Think about a recent mistake, setback or difficult experience."
            ),
            ActivityStep(
                id = "compassion_step_2",
                instruction =
                    "Notice how you usually speak to yourself when something goes wrong."
            ),
            ActivityStep(
                id = "compassion_step_3",
                instruction =
                    "Imagine what you would say to someone you deeply care about in the same situation."
            ),
            ActivityStep(
                id = "compassion_step_4",
                instruction =
                    "Offer yourself the same understanding, kindness and encouragement."
            ),
            ActivityStep(
                id = "compassion_step_5",
                instruction =
                    "Write one supportive and encouraging statement you can carry forward."
            )
        ),
        benefits = listOf(
            "Reduces self-criticism",
            "Builds self-compassion",
            "Encourages healthier self-talk",
            "Supports emotional resilience"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_DEPRESSION,
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.MILD_ANXIETY,
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.STRESS,
            RecommendationTarget.BURNOUT
        )
    )
}