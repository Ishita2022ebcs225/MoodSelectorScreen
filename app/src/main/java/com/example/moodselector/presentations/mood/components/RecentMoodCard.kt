package com.example.moodselector.presentations.mood.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moodselector.data.local.entity.MoodEntry

@Composable
fun RecentMoodCard(
    mood: MoodEntry
) {

    val pastelPurple = Color(0xFFEDE7FF)
    val textDark = Color(0xFF1D1B20)

    Card(

        shape = RoundedCornerShape(30.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Row(

            modifier = Modifier
                .padding(22.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(

                modifier = Modifier
                    .size(62.dp)
                    .background(
                        pastelPurple,
                        CircleShape
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = mood.emoji,

                    style = MaterialTheme
                        .typography
                        .headlineSmall
                )
            }

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            Column {

                Text(
                    text = mood.mood,

                    style = MaterialTheme
                        .typography
                        .titleLarge,

                    fontWeight = FontWeight.Bold,

                    color = textDark
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = mood.timestamp,

                    style = MaterialTheme
                        .typography
                        .bodyMedium,

                    color = Color.Gray
                )
            }
        }
    }
}