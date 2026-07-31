package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.ActivityStep
import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.domain.cbt.model.RecommendationTarget

/**
 * Defines a CBT activity and its metadata.
 */
interface CBTActivityDefinition {

    /**
     * Unique identifier for the activity.
     */
    val id: String

    /**
     * Display title.
     */
    val title: String

    /**
     * Brief description.
     */
    val description: String

    /**
     * Activity category.
     */
    val category: CBTCategory

    /**
     * Guided activity steps.
     */
    val steps: List<ActivityStep>

    /**
     * Expected therapeutic benefits.
     */
    val benefits: List<String>

    /**
     * Assessment outcomes this activity is recommended for.
     */
    val recommendedFor: List<RecommendationTarget>

    /**
     * Converts this definition into a CBTActivity.
     */
    fun toActivity(): CBTActivity =
        CBTActivity(
            id = id,
            title = title,
            description = description,
            category = category,
            steps = steps,
            benefits = benefits,
            recommendedFor = recommendedFor
        )
}