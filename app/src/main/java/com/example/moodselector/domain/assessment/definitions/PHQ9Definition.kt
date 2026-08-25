package com.example.moodselector.domain.assessment.definitions

import com.example.moodselector.domain.assessment.model.AssessmentDefinition
import com.example.moodselector.domain.assessment.model.AssessmentType
import com.example.moodselector.domain.assessment.questions.PHQ9Questions

object PHQ9Definition {

    val assessment = AssessmentDefinition(
        type = AssessmentType.PHQ9,

        title = "Patient Health Questionnaire (PHQ-9)",

        description =
            "The PHQ-9 is a widely used screening tool that helps identify and measure " +
                    "symptoms of depression over the past two weeks. It is intended as a " +
                    "screening instrument and should not be used as a substitute for a " +
                    "professional diagnosis.",

        instructions =
            PHQ9Questions.INSTRUCTIONS,

        questions =
            PHQ9Questions.questions
    )
}