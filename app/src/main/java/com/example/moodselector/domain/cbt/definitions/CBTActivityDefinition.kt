package com.example.moodselector.domain.cbt.definitions

import com.example.moodselector.domain.cbt.model.CBTActivity
import com.example.moodselector.domain.cbt.model.CBTCategory
import com.example.moodselector.domain.cbt.model.RecommendationTarget

/**
 * Defines a CBT activity and its metadata.
 */
interface CBTActivityDefinition {

    val id: String

    val title: String

    val description: String

    val category: CBTCategory

    val instructions: List<String>

    val benefits: List<String>

    val recommendedFor: List<RecommendationTarget>

    fun toActivity(): CBTActivity =
        CBTActivity(
            id = id,
            title = title,
            description = description,
            category = category,
            instructions = instructions,
            benefits = benefits,
            recommendedFor = recommendedFor
        )
}