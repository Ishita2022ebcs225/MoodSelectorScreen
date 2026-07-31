package com.example.moodselector.presentations.assessment.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AssessmentResultsScreen(

    onContinueClicked: () -> Unit,

    modifier: Modifier = Modifier,

    viewModel: AssessmentResultsViewModel = hiltViewModel()

) {

    val result by viewModel.latestResult.collectAsStateWithLifecycle()

    result?.let { assessment ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Assessment Complete",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Depression Screening (PHQ-9)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Score: ${assessment.phq9Score}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Severity: ${assessment.phq9Severity.displayName} depression",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Divider()

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = "Anxiety Screening (GAD-7)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Score: ${assessment.gad7Score}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Severity: ${assessment.gad7Severity.displayName} anxiety",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text =
                    "These questionnaires are validated screening tools and are not intended to provide a clinical diagnosis.\n\n" +
                            "If your symptoms are affecting your daily life, consider discussing your results with a qualified mental health professional.",

                style = MaterialTheme.typography.bodyMedium,

                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Button(
                onClick = onContinueClicked,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Continue"
                )
            }
        }
    }
}