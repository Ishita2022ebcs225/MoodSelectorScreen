package com.example.moodselector.presentations.mood

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.presentations.mood.MoodViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun MoodGraphScreen(
    viewModel: MoodViewModel = hiltViewModel()
) {

    val moods by viewModel.moodList.collectAsState()

    val entries = moods.mapIndexed { index, mood ->

        val score = when (mood.emoji) {

            "😊" -> 5f
            "😌" -> 4f
            "😐" -> 3f
            "😔" -> 2f
            "😡" -> 1f

            else -> 3f
        }

        Entry(index.toFloat(), score)
    }

    Scaffold(

        containerColor = Color(0xFFF8F5FF)

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F5FF))
                .padding(paddingValues)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp),

            verticalArrangement =
                Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Mood Analytics 📈",

                style = MaterialTheme
                    .typography
                    .headlineMedium,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF1D1B20)
            )

            Card(

                shape = RoundedCornerShape(30.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {

                AndroidView(

                    modifier = Modifier
                        .fillMaxSize()
                        .height(400.dp)
                        .padding(16.dp),

                    factory = { context ->

                        LineChart(context).apply {

                            val dataSet = LineDataSet(
                                entries,
                                "Mood Trend"
                            )

                            dataSet.color =
                                AndroidColor.rgb(
                                    108,
                                    99,
                                    255
                                )

                            dataSet.valueTextColor =
                                AndroidColor.BLACK

                            dataSet.lineWidth = 3f

                            dataSet.circleRadius = 6f

                            dataSet.setCircleColor(
                                AndroidColor.rgb(
                                    142,
                                    124,
                                    255
                                )
                            )

                            dataSet.setDrawFilled(true)

                            dataSet.fillColor =
                                AndroidColor.rgb(
                                    221,
                                    242,
                                    255
                                )

                            val lineData =
                                LineData(dataSet)

                            data = lineData

                            description = Description().apply {
                                text = ""
                            }

                            setTouchEnabled(true)

                            setPinchZoom(true)

                            animateX(1200)

                            axisRight.isEnabled = false

                            legend.isEnabled = true

                            xAxis.setDrawGridLines(false)

                            axisLeft.setDrawGridLines(false)

                            invalidate()
                        }
                    }
                )
            }
        }
    }
}