package com.example.moodselector.domain.cbt.utils

import com.example.moodselector.domain.cbt.definitions.BehavioralActivationExercises
import com.example.moodselector.domain.cbt.definitions.MindfulnessExercises
import com.example.moodselector.domain.cbt.definitions.REBTExercises
import com.example.moodselector.domain.cbt.model.CBTActivity

/**
 * Provides access to all CBT exercises available in the application.
 *
 * The recommendation engine should obtain exercises through this provider
 * rather than referencing individual definition files directly.
 */
object CBTActivityProvider {

    /**
     * Behavioral Activation exercises.
     */
    val behavioralActivationExercises: List<CBTActivity>
        get() = BehavioralActivationExercises.activities
    /**
     * Mindfulness exercises.
     */
    val mindfulnessExercises: List<CBTActivity>
        get() = MindfulnessExercises.activities

    /**
     * REBT exercises.
     */
    val rebtExercises: List<CBTActivity>
        get() = REBTExercises.activities

    /**
     * Every CBT exercise available in the app.
     */
    val allActivities: List<CBTActivity>
        get() = buildList {
            addAll(behavioralActivationExercises)
            addAll(mindfulnessExercises)
            addAll(rebtExercises)
        }
}