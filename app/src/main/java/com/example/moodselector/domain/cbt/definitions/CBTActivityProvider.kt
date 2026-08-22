package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory

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

    /**
     * Returns all exercises belonging to the given CBT category.
     */
    fun getActivitiesByCategory(
        category: CBTCategory
    ): List<CBTActivity> {
        return allActivities.filter {
            it.category == category
        }
    }

    /**
     * Returns a CBT exercise by its unique ID.
     */
    fun getActivityById(
        id: String
    ): CBTActivity? {
        return allActivities.find {
            it.id == id
        }
    }
}

