package com.example.moodselector.presentations.assessment.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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

    val colorScheme =
        MaterialTheme.colorScheme

    if (result != null) {

        val assessment = result!!

        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(
                        colorScheme.background
                    )
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Text(
                text =
                    "Assessment Complete",

                style =
                    MaterialTheme.typography.headlineLarge,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    colorScheme.onBackground,

                textAlign =
                    TextAlign.Center
            )

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )


            /*
             * ==================================================
             * ASSESSMENT RESULTS
             * ==================================================
             */

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            colorScheme.surface
                    ),

                shape =
                    RoundedCornerShape(24.dp),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(24.dp)
                ) {

                    Text(
                        text =
                            "Depression Screening (PHQ-9)",

                        style =
                            MaterialTheme.typography.titleLarge,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            colorScheme.onSurface
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Text(
                        text =
                            "Score: ${assessment.phq9Score}",

                        style =
                            MaterialTheme.typography.titleMedium,

                        color =
                            colorScheme.onSurface
                    )

                    Text(
                        text =
                            "Severity: ${assessment.phq9Severity.displayName} depression",

                        style =
                            MaterialTheme.typography.bodyLarge,

                        color =
                            colorScheme.onSurfaceVariant
                    )

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )

                    HorizontalDivider(
                        color =
                            colorScheme.outlineVariant
                    )

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )

                    Text(
                        text =
                            "Anxiety Screening (GAD-7)",

                        style =
                            MaterialTheme.typography.titleLarge,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            colorScheme.onSurface
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Text(
                        text =
                            "Score: ${assessment.gad7Score}",

                        style =
                            MaterialTheme.typography.titleMedium,

                        color =
                            colorScheme.onSurface
                    )

                    Text(
                        text =
                            "Severity: ${assessment.gad7Severity.displayName} anxiety",

                        style =
                            MaterialTheme.typography.bodyLarge,

                        color =
                            colorScheme.onSurfaceVariant
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )


            /*
             * ==================================================
             * SCREENING DISCLAIMER
             * ==================================================
             */

            Text(
                text =
                    "These questionnaires are validated screening tools and are not intended to provide a clinical diagnosis.\n\n" +
                            "If your symptoms are affecting your daily life, consider discussing your results with a qualified mental health professional.",

                style =
                    MaterialTheme.typography.bodyMedium,

                color =
                    colorScheme.primary,

                fontWeight =
                    FontWeight.SemiBold,

                textAlign =
                    TextAlign.Center
            )


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )


            /*
             * ==================================================
             * CBT GUIDANCE
             * ==================================================
             */

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            colorScheme.secondaryContainer
                    ),

                shape =
                    RoundedCornerShape(24.dp),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.SelfImprovement,

                        contentDescription =
                            null,

                        tint =
                            colorScheme.onSecondaryContainer
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Text(
                        text =
                            "Personalized CBT support",

                        style =
                            MaterialTheme.typography.titleLarge,

                        fontWeight =
                            FontWeight.SemiBold,

                        color =
                            colorScheme.onSecondaryContainer,

                        textAlign =
                            TextAlign.Center
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Based on your assessment results, personalized CBT exercises will be available in the CBT section of the app.\n\n" +
                                    "You can explore them whenever you're ready and work through them at your own pace.",

                        style =
                            MaterialTheme.typography.bodyMedium,

                        color =
                            colorScheme.onSecondaryContainer,

                        textAlign =
                            TextAlign.Center
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(32.dp)
            )


            Button(
                onClick =
                    onContinueClicked,

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    text =
                        "Continue"
                )
            }
        }

    } else {

        /*
         * ==================================================
         * NO ASSESSMENT RESULT
         * ==================================================
         */

        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(
                        colorScheme.background
                    )
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            colorScheme.surface
                    ),

                shape =
                    RoundedCornerShape(24.dp),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(28.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector =
                            Icons.Outlined.SelfImprovement,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.height(52.dp),

                        tint =
                            colorScheme.primary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )

                    Text(
                        text =
                            "Assessment Not Completed",

                        style =
                            MaterialTheme.typography.headlineSmall,

                        fontWeight =
                            FontWeight.SemiBold,

                        color =
                            colorScheme.onSurface,

                        textAlign =
                            TextAlign.Center
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Text(
                        text =
                            "You haven't attempted the questionnaire yet.",

                        style =
                            MaterialTheme.typography.titleMedium,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            colorScheme.onSurface,

                        textAlign =
                            TextAlign.Center
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    Text(
                        text =
                            "Complete the questionnaire to see your depression and anxiety screening results and receive personalized CBT support.",

                        style =
                            MaterialTheme.typography.bodyMedium,

                        color =
                            colorScheme.onSurfaceVariant,

                        textAlign =
                            TextAlign.Center
                    )

                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )

                    Button(
                        onClick =
                            onContinueClicked,

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(14.dp)
                    ) {

                        Text(
                            text =
                                "Attempt the Questionnaire"
                        )
                    }
                }
            }
        }
    }
}