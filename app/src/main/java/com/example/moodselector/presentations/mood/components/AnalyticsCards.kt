package com.example.moodselector.presentations.mood.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@SuppressLint("DefaultLocale")
@Composable
fun AnalyticsCards(
    averageMood: Double
) {

    val pastelGreen = Color(0xFFE2F8E7)
    val pastelPink = Color(0xFFFFE0EB)

    Row(
        horizontalArrangement =
            Arrangement.spacedBy(14.dp)
    ) {

        Card(

            modifier = Modifier.weight(1f),

            shape = RoundedCornerShape(28.dp),

            colors = CardDefaults.cardColors(
                containerColor = pastelGreen
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Average",

                    fontWeight =
                        FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        String.format(
                            "%.1f",
                            averageMood
                        ),

                    style = MaterialTheme
                        .typography
                        .headlineMedium,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Mood score"
                )
            }
        }

        Card(

            modifier = Modifier.weight(1f),

            shape = RoundedCornerShape(28.dp),

            colors = CardDefaults.cardColors(
                containerColor = pastelPink
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Trend",

                    fontWeight =
                        FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text =
                        if (averageMood >= 4)
                            "Positive"
                        else if (averageMood >= 3)
                            "Stable"
                        else
                            "Low",

                    style = MaterialTheme
                        .typography
                        .titleLarge,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "This week"
                )
            }
        }
    }
}