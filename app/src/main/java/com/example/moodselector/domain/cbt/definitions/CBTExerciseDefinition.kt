package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.domain.cbt.model.CBTExercise

/**
 * Defines a CBT exercise and its metadata.
 *
 * Each concrete exercise (e.g., Thought Record, Behavioral Activation,
 * Grounding Exercise) implements this interface.
 */
interface CBTExerciseDefinition {

    /**
     * Unique identifier for the exercise.
     */
    val id: String

    /**
     * Display title shown in the UI.
     */
    val title: String

    /**
     * Brief explanation of the exercise.
     */
    val description: String

    /**
     * Category of CBT exercise.
     */
    val category: CBTCategory

    /**
     * Estimated time to complete.
     */
    val estimatedDurationMinutes: Int

    /**
     * Step-by-step instructions.
     */
    val instructions: List<String>

    /**
     * Expected therapeutic benefits.
     */
    val benefits: List<String>

    /**
     * Assessment outcomes or symptoms this exercise is recommended for.
     */
    val recommendedFor: List<String>

    /**
     * Converts the definition into a CBTExercise model.
     */
    fun toExercise(): CBTExercise =
        CBTExercise(
            id = id,
            title = title,
            description = description,
            category = category,
            instructions = instructions,
            benefits = benefits,
            recommendedFor = recommendedFor
        )
}