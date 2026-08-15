package com.example.moodselector.presentations.mood

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.data.local.entity.MoodEntry
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/*
 * ==========================================================
 * TIMESTAMP HELPERS
 * ==========================================================
 *
 * MoodEntry stores timestamps as:
 *
 * dd MMM yyyy • hh:mm a
 *
 * Example:
 *
 * 15 Aug 2026 • 01:42 PM
 */


/*
 * Convert the stored timestamp into a date string.
 */
private fun getMoodDate(
    timestamp: String
): String {

    return try {

        val inputFormat = SimpleDateFormat(
            "dd MMM yyyy • hh:mm a",
            Locale.getDefault()
        )

        val outputFormat = SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        )

        val date =
            inputFormat.parse(timestamp)

        if (date != null) {

            outputFormat.format(date)

        } else {

            ""
        }

    } catch (exception: Exception) {

        ""
    }
}


/*
 * Convert the stored timestamp into a time string.
 *
 * The graph X-axis displays ONLY this value.
 */
private fun getMoodTime(
    timestamp: String
): String {

    return try {

        val inputFormat = SimpleDateFormat(
            "dd MMM yyyy • hh:mm a",
            Locale.getDefault()
        )

        val outputFormat = SimpleDateFormat(
            "hh:mm a",
            Locale.getDefault()
        )

        val date =
            inputFormat.parse(timestamp)

        if (date != null) {

            outputFormat.format(date)

        } else {

            timestamp
        }

    } catch (exception: Exception) {

        timestamp
    }
}


/*
 * Convert the DatePicker value into the same
 * date format used by MoodEntry.
 */
private fun formatSelectedDate(
    millis: Long
): String {

    val formatter = SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    )

    return formatter.format(
        Date(millis)
    )
}


/*
 * ==========================================================
 * MOOD SCORE
 * ==========================================================
 */

private fun moodScore(
    moodValue: String
): Float {

    return when (moodValue) {

        "Happy",
        "😊" ->
            5f

        "Calm",
        "😌" ->
            4f

        "Neutral",
        "😐" ->
            3f

        "Sad",
        "😔" ->
            2f

        "Angry",
        "😡" ->
            1f

        else ->
            3f
    }
}


/*
 * ==========================================================
 * MOOD GRAPH SCREEN
 * ==========================================================
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodGraphScreen(
    viewModel: MoodViewModel = hiltViewModel()
) {

    val moods by
    viewModel
        .moodList
        .collectAsState()


    /*
     * ======================================================
     * COLORS
     * ======================================================
     */

    val background =
        Color(0xFFF8F5FF)

    val textDark =
        Color(0xFF292638)

    val textSecondary =
        Color(0xFF777282)

    val lavender =
        Color(0xFF6C63FF)

    val softLavender =
        Color(0xFFEDE7FF)


    /*
     * ======================================================
     * DATE PICKER STATE
     * ======================================================
     */

    var selectedDateMillis by
    rememberSaveable {

        mutableStateOf(
            System.currentTimeMillis()
        )
    }

    var showDatePicker by
    rememberSaveable {

        mutableStateOf(false)
    }


    /*
     * ======================================================
     * SELECTED DATE
     * ======================================================
     */

    val selectedDate =
        formatSelectedDate(
            selectedDateMillis
        )


    /*
     * ======================================================
     * FILTER MOODS
     * ======================================================
     *
     * The selected date is chosen by the user.
     *
     * Only entries from that date are displayed.
     */

    val selectedDayMoods: List<MoodEntry> =
        moods
            .filter { mood ->

                getMoodDate(
                    mood.timestamp
                ) == selectedDate
            }
            .reversed()


    /*
     * ======================================================
     * GRAPH ENTRIES
     * ======================================================
     *
     * Entries are chronological:
     *
     * oldest -> newest
     */

    val entries =
        selectedDayMoods.mapIndexed { index, mood ->

            Entry(
                index.toFloat(),
                moodScore(
                    mood.emoji
                )
            )
        }


    /*
     * ======================================================
     * TIME LABELS
     * ======================================================
     *
     * IMPORTANT:
     *
     * The date has already been selected above the graph.
     *
     * Therefore the X-axis displays ONLY time.
     */

    val timeLabels =
        selectedDayMoods.map { mood ->

            getMoodTime(
                mood.timestamp
            )
        }


    /*
     * ======================================================
     * DATE PICKER
     * ======================================================
     */

    if (showDatePicker) {

        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis =
                    selectedDateMillis
            )

        DatePickerDialog(

            onDismissRequest = {

                showDatePicker =
                    false
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        datePickerState
                            .selectedDateMillis
                            ?.let { millis ->

                                selectedDateMillis =
                                    millis
                            }

                        showDatePicker =
                            false
                    }
                ) {

                    Text(
                        text = "Select",
                        color = lavender
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showDatePicker =
                            false
                    }
                ) {

                    Text(
                        text = "Cancel",
                        color = textSecondary
                    )
                }
            }

        ) {

            DatePicker(
                state =
                    datePickerState
            )
        }
    }


    /*
     * ======================================================
     * SCREEN
     * ======================================================
     *
     * IMPORTANT:
     *
     * The complete screen is vertically scrollable.
     *
     * This guarantees that the Mood scale remains accessible
     * below the graph even on smaller screens.
     */

    Scaffold(

        containerColor =
            background

    ) { paddingValues ->

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        background
                    )
                    .padding(
                        paddingValues
                    )
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 18.dp,
                        vertical = 12.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {


            /*
             * ==================================================
             * HEADER
             * ==================================================
             */

            Column {

                Text(

                    text =
                        "Mood Analytics 📈",

                    style =
                        MaterialTheme
                            .typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        textDark
                )

                Text(

                    text =
                        "View your mood changes for a specific day.",

                    style =
                        MaterialTheme
                            .typography
                            .bodySmall,

                    color =
                        textSecondary,

                    modifier =
                        Modifier.padding(
                            top = 3.dp
                        )
                )
            }


            /*
             * ==================================================
             * DATE SELECTOR
             * ==================================================
             */

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(18.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
            ) {

                Row(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 14.dp,
                                vertical = 10.dp
                            ),

                    verticalAlignment =
                        Alignment.CenterVertically,

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Column {

                        Text(

                            text =
                                "Selected date",

                            style =
                                MaterialTheme
                                    .typography
                                    .labelMedium,

                            color =
                                textSecondary
                        )

                        Text(

                            text =
                                selectedDate,

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold,

                            color =
                                textDark
                        )
                    }

                    Button(

                        onClick = {

                            showDatePicker =
                                true
                        },

                        modifier =
                            Modifier.height(42.dp),

                        shape =
                            RoundedCornerShape(14.dp),

                        contentPadding =
                            PaddingValues(
                                horizontal = 14.dp
                            ),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    softLavender,

                                contentColor =
                                    lavender
                            )
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.CalendarMonth,

                            contentDescription =
                                "Choose date",

                            modifier =
                                Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier =
                                Modifier.width(6.dp)
                        )

                        Text(
                            text =
                                "Change"
                        )
                    }
                }
            }


            /*
             * ==================================================
             * GRAPH CARD
             * ==================================================
             */

            Card(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(350.dp),

                shape =
                    RoundedCornerShape(24.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
            ) {

                if (entries.isEmpty()) {

                    /*
                     * ------------------------------------------
                     * EMPTY STATE
                     * ------------------------------------------
                     */

                    Column(

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(24.dp),

                        verticalArrangement =
                            Arrangement.Center,

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(

                            text =
                                "No mood entries",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold,

                            color =
                                textDark
                        )

                        Text(

                            text =
                                "There are no mood entries recorded for $selectedDate.",

                            modifier =
                                Modifier.padding(
                                    top = 6.dp
                                ),

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                textSecondary
                        )
                    }

                } else {

                    /*
                     * ------------------------------------------
                     * LINE CHART
                     * ------------------------------------------
                     */

                    AndroidView(

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(12.dp),

                        factory = { context ->

                            LineChart(
                                context
                            )

                        },

                        update = { chart ->


                            /*
                             * ----------------------------------
                             * DATASET
                             * ----------------------------------
                             */

                            val dataSet =
                                LineDataSet(
                                    entries,
                                    "Mood"
                                ).apply {

                                    color =
                                        AndroidColor.rgb(
                                            108,
                                            99,
                                            255
                                        )

                                    valueTextColor =
                                        AndroidColor.rgb(
                                            90,
                                            86,
                                            105
                                        )

                                    valueTextSize =
                                        8f

                                    lineWidth =
                                        2.5f

                                    circleRadius =
                                        4.5f

                                    setCircleColor(
                                        AndroidColor.rgb(
                                            142,
                                            124,
                                            255
                                        )
                                    )

                                    /*
                                     * Do not show numeric values
                                     * over the graph points.
                                     */
                                    setDrawValues(
                                        false
                                    )

                                    setDrawFilled(
                                        true
                                    )

                                    fillColor =
                                        AndroidColor.rgb(
                                            232,
                                            225,
                                            245
                                        )

                                    fillAlpha =
                                        70

                                    mode =
                                        LineDataSet.Mode
                                            .CUBIC_BEZIER
                                }


                            chart.data =
                                LineData(
                                    dataSet
                                )


                            /*
                             * ----------------------------------
                             * DESCRIPTION
                             * ----------------------------------
                             */

                            chart.description =
                                Description().apply {

                                    text = ""
                                }


                            /*
                             * ----------------------------------
                             * X-AXIS
                             * ----------------------------------
                             *
                             * TIME ONLY.
                             */

                            chart.xAxis.apply {

                                position =
                                    XAxis.XAxisPosition.BOTTOM

                                setDrawGridLines(
                                    false
                                )

                                setDrawAxisLine(
                                    true
                                )

                                textColor =
                                    AndroidColor.rgb(
                                        100,
                                        96,
                                        112
                                    )

                                textSize =
                                    8f

                                granularity =
                                    1f

                                labelCount =
                                    minOf(
                                        timeLabels.size,
                                        5
                                    )

                                spaceMin =
                                    0.2f

                                spaceMax =
                                    0.2f

                                valueFormatter =
                                    object :
                                        ValueFormatter() {

                                        override fun
                                                getFormattedValue(
                                            value: Float
                                        ): String {

                                            val index =
                                                value.toInt()

                                            return if (
                                                index >= 0 &&
                                                index <
                                                timeLabels.size
                                            ) {

                                                timeLabels[
                                                    index
                                                ]

                                            } else {

                                                ""
                                            }
                                        }
                                    }
                            }


                            /*
                             * ----------------------------------
                             * LEFT Y-AXIS
                             * ----------------------------------
                             *
                             * 1 = 😡
                             * 2 = 😔
                             * 3 = 😐
                             * 4 = 😌
                             * 5 = 😊
                             */

                            chart.axisLeft.apply {

                                setDrawGridLines(
                                    true
                                )

                                gridColor =
                                    AndroidColor.rgb(
                                        235,
                                        231,
                                        242
                                    )

                                textColor =
                                    AndroidColor.rgb(
                                        100,
                                        96,
                                        112
                                    )

                                textSize =
                                    13f

                                axisMinimum =
                                    1f

                                axisMaximum =
                                    5f

                                granularity =
                                    1f

                                labelCount =
                                    5

                                valueFormatter =
                                    object :
                                        ValueFormatter() {

                                        override fun
                                                getFormattedValue(
                                            value: Float
                                        ): String {

                                            return when (
                                                value.toInt()
                                            ) {

                                                1 ->
                                                    "😡"

                                                2 ->
                                                    "😔"

                                                3 ->
                                                    "😐"

                                                4 ->
                                                    "😌"

                                                5 ->
                                                    "😊"

                                                else ->
                                                    ""
                                            }
                                        }
                                    }
                            }


                            /*
                             * ----------------------------------
                             * RIGHT Y-AXIS
                             * ----------------------------------
                             */

                            chart.axisRight.isEnabled =
                                false


                            /*
                             * ----------------------------------
                             * LEGEND
                             * ----------------------------------
                             *
                             * "Mood" is hidden because the
                             * graph is already self-explanatory.
                             */

                            chart.legend.isEnabled =
                                false


                            /*
                             * ----------------------------------
                             * INTERACTION
                             * ----------------------------------
                             *
                             * Pinch zoom is disabled.
                             *
                             * IMPORTANT:
                             *
                             * Do NOT use setScaleEnabled().
                             * That method is unavailable in the
                             * MPAndroidChart version being used.
                             */

                            chart.setTouchEnabled(
                                true
                            )

                            chart.setPinchZoom(
                                false
                            )


                            /*
                             * ----------------------------------
                             * ANIMATION
                             * ----------------------------------
                             */

                            chart.animateX(
                                500
                            )


                            /*
                             * ----------------------------------
                             * REFRESH
                             * ----------------------------------
                             */

                            chart.invalidate()
                        }
                    )
                }
            }


            /*
             * ==================================================
             * MOOD SCALE
             * ==================================================
             *
             * THIS IS DEFINITELY PRESERVED.
             *
             * It appears directly below the graph.
             */

            Card(

                modifier =
                    Modifier
                        .fillMaxWidth(),

                shape =
                    RoundedCornerShape(20.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            softLavender
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
            ) {

                Column(

                    modifier =
                        Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 11.dp
                        ),

                    verticalArrangement =
                        Arrangement.spacedBy(7.dp)
                ) {

                    Text(

                        text =
                            "Mood scale",

                        style =
                            MaterialTheme
                                .typography
                                .titleSmall,

                        fontWeight =
                            FontWeight.SemiBold,

                        color =
                            textDark
                    )

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        MoodScaleItem(
                            score = "1",
                            emoji = "😡",
                            label = "Angry"
                        )

                        MoodScaleItem(
                            score = "2",
                            emoji = "😔",
                            label = "Sad"
                        )

                        MoodScaleItem(
                            score = "3",
                            emoji = "😐",
                            label = "Neutral"
                        )

                        MoodScaleItem(
                            score = "4",
                            emoji = "😌",
                            label = "Calm"
                        )

                        MoodScaleItem(
                            score = "5",
                            emoji = "😊",
                            label = "Happy"
                        )
                    }
                }
            }


            /*
             * ==================================================
             * BOTTOM SPACING
             * ==================================================
             *
             * Gives the Mood scale some breathing room above
             * the bottom navigation bar.
             */

            Spacer(
                modifier =
                    Modifier.height(90.dp)
            )
        }
    }
}


/*
 * ==========================================================
 * MOOD SCALE ITEM
 * ==========================================================
 */

@Composable
private fun MoodScaleItem(
    score: String,
    emoji: String,
    label: String
) {

    Column(

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.spacedBy(2.dp)
    ) {

        Text(

            text =
                emoji,

            fontSize =
                20.sp
        )

        Text(

            text =
                score,

            fontSize =
                11.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Color(0xFF6C63FF)
        )

        Text(

            text =
                label,

            fontSize =
                9.sp,

            color =
                Color(0xFF777282)
        )
    }
}

