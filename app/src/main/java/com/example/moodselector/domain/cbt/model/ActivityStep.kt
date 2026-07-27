package com.example.moodselector.domain.cbt.model

/**
 * Represents a single step in a guided CBT activity.
 *
 * This model is intentionally designed to support:
 * - Text instructions
 * - Android Text-to-Speech
 * - Recorded voiceovers
 * - Background music
 * - Future animations
 */
data class ActivityStep(

    /**
     * Unique identifier for the step.
     */
    val id: String,

    /**
     * Text displayed to the user.
     */
    val instruction: String,

    /**
     * Optional pause after this step (milliseconds).
     *
     * Useful for breathing exercises and meditation.
     */
    val pauseDurationMillis: Long = 0L,

    /**
     * Optional voice-over resource.
     *
     * Null while using Text-to-Speech.
     */
    val audioResId: Int? = null
)