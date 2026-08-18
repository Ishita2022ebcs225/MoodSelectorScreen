package com.example.moodselector.presentations.mood.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InsightCard(
    insight: String
) {

    Card(

        shape = RoundedCornerShape(30.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Text(
                text = "AI Insight ✨",

                style = MaterialTheme
                    .typography
                    .titleLarge,

                fontWeight = FontWeight.Bold,

                color =
                    MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = insight,

                style = MaterialTheme
                    .typography
                    .bodyLarge,

                color =
                    MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}