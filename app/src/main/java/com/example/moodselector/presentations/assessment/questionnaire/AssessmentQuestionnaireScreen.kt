package com.example.moodselector.presentations.assessment.questionnaire

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodselector.presentations.assessment.questionnaire.components.AssessmentProgressIndicator
import com.example.moodselector.presentations.assessment.questionnaire.components.QuestionCard

@Composable
fun AssessmentQuestionnaireScreen(
    viewModel: AssessmentViewModel = hiltViewModel(),
    onAssessmentCompleted: (
        phq9Score: Int,
        phq9Severity: String,
        gad7Score: Int,
        gad7Severity: String
    ) -> Unit = { _, _, _, _ -> }
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isCompleted) {

        LaunchedEffect(Unit) {

            onAssessmentCompleted(
                uiState.phq9Score,
                uiState.phq9Severity,
                uiState.gad7Score,
                uiState.gad7Severity
            )
        }

        return
    }

    val assessment = uiState.assessment ?: return

    val currentQuestion =
        uiState.currentQuestion ?: return

    val selectedScore =
        uiState.selectedAnswers[currentQuestion.id]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = assessment.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = assessment.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = assessment.instructions,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        AssessmentProgressIndicator(
            currentQuestion = uiState.currentQuestionIndex,
            totalQuestions = uiState.totalQuestions
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            item {

                QuestionCard(
                    question = currentQuestion,
                    selectedScore = selectedScore,
                    onAnswerSelected = { score ->

                        viewModel.selectAnswer(
                            questionId = currentQuestion.id,
                            score = score
                        )
                    }
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (uiState.currentQuestionIndex > 0) {

                OutlinedButton(
                    onClick = {
                        viewModel.previousQuestion()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Previous")
                }
            }

            Button(
                onClick = {
                    viewModel.nextQuestion()
                },
                enabled = selectedScore != null,
                modifier = Modifier.fillMaxWidth()
            ) {

                val buttonText = if (
                    uiState.currentQuestionIndex ==
                    assessment.questions.lastIndex
                ) {

                    if (uiState.isPHQ9) {
                        "Continue to GAD-7"
                    } else {
                        "Finish Assessment"
                    }

                } else {
                    "Next Question"
                }

                Text(buttonText)
            }
        }
    }
}