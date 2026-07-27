package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.ActivityStep
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.domain.cbt.model.RecommendationTarget

/**
 * Collection of Rational Emotive Behavior Therapy (REBT) activities.
 *
 * These activities help users identify irrational beliefs,
 * challenge unhelpful thinking patterns, and replace them
 * with healthier, more balanced alternatives.
 *
 * The guided worksheet experience will be implemented
 * later in the presentation layer.
 */
object REBTExercises {

    /**
     * All REBT activities.
     */
    val activities: List<CBTActivity>
        get() = listOf(
            abcModel,
            disputingBeliefs,
            balancedThinking,
            selfCompassionReflection
        )

    /**
     * The ABC Model helps users understand the relationship
     * between situations, beliefs and emotional consequences.
     */
    val abcModel = CBTActivity(
        id = "abc_model",
        title = "ABC Model",
        description = "Understand how your beliefs influence your emotional reactions.",
        category = CBTCategory.COGNITIVE,
        steps = listOf(
            ActivityStep(
                id = "abc_step_1",
                instruction = "Think of a recent situation that caused emotional distress."
            ),
            ActivityStep(
                id = "abc_step_2",
                instruction = "Describe the activating event as objectively as possible."
            ),
            ActivityStep(
                id = "abc_step_3",
                instruction = "Identify the thoughts or beliefs you had about the event."
            ),
            ActivityStep(
                id = "abc_step_4",
                instruction = "Notice the emotional and behavioural consequences of those beliefs."
            ),
            ActivityStep(
                id = "abc_step_5",
                instruction = "Reflect on how different beliefs might have changed the outcome."
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
     * Encourages users to question irrational beliefs
     * using evidence and logical thinking.
     */
    val disputingBeliefs = CBTActivity(
        id = "disputing_beliefs",
        title = "Disputing Irrational Beliefs",
        description = "Challenge thoughts that may not be accurate, helpful or realistic.",
        category = CBTCategory.COGNITIVE,
        steps = listOf(
            ActivityStep(
                id = "dispute_step_1",
                instruction = "Choose one negative or irrational belief."
            ),
            ActivityStep(
                id = "dispute_step_2",
                instruction = "Ask yourself whether there is evidence supporting this belief."
            ),
            ActivityStep(
                id = "dispute_step_3",
                instruction = "Consider evidence that contradicts the belief."
            ),
            ActivityStep(
                id = "dispute_step_4",
                instruction = "Think about what you would say to a close friend in the same situation."
            ),
            ActivityStep(
                id = "dispute_step_5",
                instruction = "Create a more balanced and realistic belief."
            )
        ),
        benefits = listOf(
            "Challenges irrational thinking",
            "Develops balanced thinking",
            "Reduces emotional distress",
            "Improves coping skills"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.MODERATE_ANXIETY,
            RecommendationTarget.STRESS,
            RecommendationTarget.OVERWHELM
        )
    )

    /**
     * Helps users replace unhelpful automatic thoughts
     * with healthier alternatives.
     */
    val balancedThinking = CBTActivity(
        id = "balanced_thinking",
        title = "Balanced Thinking",
        description = "Replace negative thoughts with realistic and compassionate alternatives.",
        category = CBTCategory.COGNITIVE,
        steps = listOf(
            ActivityStep(
                id = "balanced_step_1",
                instruction = "Notice a negative automatic thought."
            ),
            ActivityStep(
                id = "balanced_step_2",
                instruction = "Identify any thinking traps or cognitive distortions."
            ),
            ActivityStep(
                id = "balanced_step_3",
                instruction = "Look for facts that support and contradict the thought."
            ),
            ActivityStep(
                id = "balanced_step_4",
                instruction = "Write a more balanced interpretation of the situation."
            ),
            ActivityStep(
                id = "balanced_step_5",
                instruction = "Reflect on how this new perspective changes your emotions."
            )
        ),
        benefits = listOf(
            "Reduces negative thinking",
            "Builds resilience",
            "Encourages realistic perspectives",
            "Improves emotional wellbeing"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_DEPRESSION,
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.STRESS,
            RecommendationTarget.OVERWHELM
        )
    )

    /**
     * Encourages users to respond to themselves with
     * kindness rather than self-criticism.
     */
    val selfCompassionReflection = CBTActivity(
        id = "self_compassion_reflection",
        title = "Self-Compassion Reflection",
        description = "Practice responding to yourself with understanding, kindness and encouragement.",
        category = CBTCategory.COGNITIVE,
        steps = listOf(
            ActivityStep(
                id = "compassion_step_1",
                instruction = "Think about a recent mistake or difficult experience."
            ),
            ActivityStep(
                id = "compassion_step_2",
                instruction = "Notice how you usually speak to yourself in situations like this."
            ),
            ActivityStep(
                id = "compassion_step_3",
                instruction = "Imagine what you would say to someone you deeply care about."
            ),
            ActivityStep(
                id = "compassion_step_4",
                instruction = "Offer yourself the same kindness and understanding."
            ),
            ActivityStep(
                id = "compassion_step_5",
                instruction = "Finish by writing one encouraging statement for yourself."
            )
        ),
        benefits = listOf(
            "Reduces self-criticism",
            "Builds self-compassion",
            "Supports emotional resilience",
            "Encourages healthier self-talk"
        ),
        recommendedFor = listOf(
            RecommendationTarget.MILD_DEPRESSION,
            RecommendationTarget.MODERATE_DEPRESSION,
            RecommendationTarget.STRESS,
            RecommendationTarget.BURNOUT
        )
    )
}