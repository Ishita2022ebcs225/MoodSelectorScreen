package com.example.moodselector.presentations.mood.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp

@Composable
fun EmptyMoodState() {

    val isDarkTheme =
        MaterialTheme.colorScheme.background.luminance() < 0.5f

    val textColor =
        if (isDarkTheme) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    Card(

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            )
    ) {

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),

            contentAlignment =
                Alignment.Center
        ) {

            Text(

                text =
                    "No mood entries yet 🌸",

                color =
                    textColor
            )
        }
    }
}