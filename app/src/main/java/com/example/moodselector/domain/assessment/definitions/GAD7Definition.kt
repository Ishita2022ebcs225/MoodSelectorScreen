package com.example.moodselector.domain.assessment.definitions

import com.example.moodselector.domain.assessment.model.AssessmentDefinition
import com.example.moodselector.domain.assessment.model.AssessmentType
import com.example.moodselector.domain.assessment.questions.GAD7Questions

object GAD7Definition {

    val assessment = AssessmentDefinition(
        type = AssessmentType.GAD7,

        title = "Generalized Anxiety Disorder Scale (GAD-7)",

        description = """
            The GAD-7 is a widely used screening tool that helps identify and measure
            symptoms of anxiety over the past two weeks. It is intended as a screening
            instrument and should not be used as a substitute for a professional diagnosis.
        """.trimIndent(),

        instructions = GAD7Questions.INSTRUCTIONS,

        questions = GAD7Questions.questions
    )
}