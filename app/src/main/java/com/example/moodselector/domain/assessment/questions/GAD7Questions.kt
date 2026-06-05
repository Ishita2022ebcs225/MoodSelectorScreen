package com.example.moodselector.domain.assessment.questions

import com.example.moodselector.domain.assessment.model.AssessmentQuestion

object GAD7Questions {

    const val INSTRUCTIONS =
        "Over the last 2 weeks, how often have you been bothered by the following problems?"

    private val responseOptions = listOf(
        "Not at all",
        "Several days",
        "More than half the days",
        "Nearly every day"
    )

    private val responseScores = listOf(
        0,
        1,
        2,
        3
    )

    val questions = listOf(

        AssessmentQuestion(
            id = 1,
            question = "Feeling nervous, anxious, or on edge",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 2,
            question = "Not being able to stop or control worrying",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 3,
            question = "Worrying too much about different things",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 4,
            question = "Trouble relaxing",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 5,
            question = "Being so restless that it is hard to sit still",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 6,
            question = "Becoming easily annoyed or irritable",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 7,
            question = "Feeling afraid as if something awful might happen",
            options = responseOptions,
            scores = responseScores
        )
    )

    const val FUNCTIONAL_IMPAIRMENT_QUESTION =
        "If you checked off any problems, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people?"

    val functionalImpairmentOptions = listOf(
        "Not difficult at all",
        "Somewhat difficult",
        "Very difficult",
        "Extremely difficult"
    )
}