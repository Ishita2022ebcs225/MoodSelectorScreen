package com.example.moodselector.domain.assessment.questions

import com.example.moodselector.domain.assessment.model.AssessmentQuestion

object PHQ9Questions {

    const val INSTRUCTIONS =
        "Over the last 2 weeks, how often have you been bothered by any of the following problems?"

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
            question = "Little interest or pleasure in doing things",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 2,
            question = "Feeling down, depressed, or hopeless",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 3,
            question = "Trouble falling or staying asleep, or sleeping too much",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 4,
            question = "Feeling tired or having little energy",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 5,
            question = "Poor appetite or overeating",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 6,
            question = "Feeling bad about yourself — or that you are a failure or have let yourself or your family down",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 7,
            question = "Trouble concentrating on things, such as reading the newspaper or watching television",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 8,
            question = "Moving or speaking so slowly that other people could have noticed? Or the opposite — being so fidgety or restless that you have been moving around a lot more than usual",
            options = responseOptions,
            scores = responseScores
        ),

        AssessmentQuestion(
            id = 9,
            question = "Thoughts that you would be better off dead or of hurting yourself in some way",
            options = responseOptions,
            scores = responseScores
        )
    )

    val functionalImpairmentOptions = listOf(
        "Not difficult at all",
        "Somewhat difficult",
        "Very difficult",
        "Extremely difficult"
    )

    const val FUNCTIONAL_IMPAIRMENT_QUESTION =
        "If you checked off any problems, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people?"
}