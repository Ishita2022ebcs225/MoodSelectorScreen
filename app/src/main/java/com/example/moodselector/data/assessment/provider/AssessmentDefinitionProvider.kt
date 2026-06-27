package com.example.moodselector.data.assessment.provider

import com.example.moodselector.domain.assessment.model.AssessmentDefinition
import com.example.moodselector.domain.assessment.model.AssessmentType

interface AssessmentDefinitionProvider {

    fun getAssessmentDefinition(
        type: AssessmentType
    ): AssessmentDefinition
}