package com.example.moodselector.data.assessment.provider

import com.example.moodselector.domain.assessment.definitions.GAD7Definition
import com.example.moodselector.domain.assessment.definitions.PHQ9Definition
import com.example.moodselector.domain.assessment.model.AssessmentDefinition
import com.example.moodselector.domain.assessment.model.AssessmentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAssessmentDefinitionProvider @Inject constructor() :
    AssessmentDefinitionProvider {

    override fun getAssessmentDefinition(
        type: AssessmentType
    ): AssessmentDefinition {

        return when (type) {

            AssessmentType.PHQ9 ->
                PHQ9Definition.assessment

            AssessmentType.GAD7 ->
                GAD7Definition.assessment
        }
    }
}