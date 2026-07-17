package com.example.moodselector.domain.cbt.model

/**
 * Represents the different therapeutic approaches
 * available within the CBT module.
 */
enum class CBTCategory {

    /**
     * Mindfulness-based exercises such as
     * breathing, grounding and body scan.
     */
    MINDFULNESS,

    /**
     * Encourages participation in meaningful
     * and enjoyable activities.
     */
    BEHAVIORAL_ACTIVATION,

    /**
     * Rational Emotive Behavior Therapy exercises
     * for identifying and challenging irrational beliefs.
     */
    REBT,

    /**
     * Gradual exposure to feared situations
     * to reduce avoidance and anxiety.
     */
    EXPOSURE_THERAPY,

    /**
     * Reflection exercises such as
     * thought records and journaling.
     */
    REFLECTION
}