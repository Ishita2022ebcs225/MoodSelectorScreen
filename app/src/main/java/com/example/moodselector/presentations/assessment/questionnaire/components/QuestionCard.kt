package com.example.moodselector.presentations.assessment.questionnaire.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodselector.domain.assessment.model.AssessmentQuestion

@Composable
fun QuestionCard(
    question: AssessmentQuestion,
    selectedScore: Int?,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = question.question,
                style = MaterialTheme.typography.bodyLarge
            )

            question.options.forEachIndexed { index, option ->

                AnswerOption(
                    text = option,
                    selected = selectedScore == question.scores[index],
                    onClick = {
                        onAnswerSelected(question.scores[index])
                    }
                )
            }
        }
    }
}