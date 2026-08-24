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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.data.local.entity.MoodEntry
import com.example.moodselector.presentations.cbt.progress.CBTProgressItem
import com.example.moodselector.presentations.cbt.progress.CBTProgressViewModel
import com.github.mikephil.charting.animation.Easing
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


private fun getCompletionDate(
    timestamp: Long
): String {

    val formatter = SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    )

    return formatter.format(
        Date(timestamp)
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
 * RECURRING TRIGGER ANALYTICS
 * ==========================================================
 */

private data class RecurringTrigger(
    val trigger: String,
    val totalOccurrences: Int,
    val lowMoodOccurrences: Int
) {

    val lowMoodRate: Float
        get() =
            if (totalOccurrences > 0) {
                lowMoodOccurrences.toFloat() /
                        totalOccurrences.toFloat()
            } else {
                0f
            }
}


private fun getRecurringTriggers(
    moods: List<MoodEntry>
): List<RecurringTrigger> {

    val groupedTriggers =
        moods
            .filter {
                it.trigger.isNotBlank()
            }
            .groupBy {
                it.trigger
                    .trim()
                    .lowercase(Locale.getDefault())
            }

    return groupedTriggers
        .mapNotNull { (normalizedTrigger, entries) ->

            val totalOccurrences =
                entries.size

            if (totalOccurrences < 2) {
                return@mapNotNull null
            }

            val lowMoodOccurrences =
                entries.count { moodEntry ->

                    moodScore(
                        moodEntry.emoji
                    ) <= 2f
                }

            if (lowMoodOccurrences < 2) {
                return@mapNotNull null
            }

            val lowMoodRate =
                lowMoodOccurrences.toFloat() /
                        totalOccurrences.toFloat()

            if (lowMoodRate < 0.5f) {
                return@mapNotNull null
            }

            val displayTrigger =
                entries
                    .groupingBy {
                        it.trigger.trim()
                    }
                    .eachCount()
                    .maxByOrNull {
                        it.value
                    }
                    ?.key
                    ?: normalizedTrigger

            RecurringTrigger(
                trigger =
                    displayTrigger,

                totalOccurrences =
                    totalOccurrences,

                lowMoodOccurrences =
                    lowMoodOccurrences
            )
        }
        .sortedWith(
            compareByDescending<RecurringTrigger> {
                it.lowMoodRate
            }.thenByDescending {
                it.lowMoodOccurrences
            }.thenByDescending {
                it.totalOccurrences
            }
        )
}


/*
 * ==========================================================
 * RECURRING TRIGGER DESCRIPTION
 * ==========================================================
 */

private fun getRecurringTriggerDescription(
    trigger: RecurringTrigger
): String {

    val percentage =
        (trigger.lowMoodRate * 100).toInt()

    return when {

        trigger.lowMoodRate >= 0.75f ->
            "$percentage% of entries with this trigger were linked with low mood"

        trigger.lowMoodRate >= 0.5f ->
            "$percentage% of entries with this trigger were linked with low mood"

        else ->
            "$percentage% of entries with this trigger were linked with low mood"
    }
}


/*
 * ==========================================================
 * RECURRING TRIGGER LABEL
 * ==========================================================
 */

private fun getRecurringTriggerLabel(
    trigger: RecurringTrigger
): String {

    return when {

        trigger.lowMoodRate >= 0.75f ->
            "Frequently linked with low mood"

        else ->
            "Often linked with low mood"
    }
}


/*
 * ==========================================================
 * CBT ACTIVITY LABEL
 * ==========================================================
 */

private fun getCBTActivityLabel(
    item: CBTProgressItem
): String {

    return when (item) {

        is CBTProgressItem.ActivityCompletion ->
            item.completion.activityTitle

        is CBTProgressItem.FiveMinuteStarterCompletion ->
            "5-Minute Starter"

        is CBTProgressItem.MindfulMeditationCompletion ->
            "Mindful Meditation"

        is CBTProgressItem.Grounding54321Completion ->
            "5-4-3-2-1 Grounding"

        is CBTProgressItem.ABCModelCompletion ->
            "ABC Model"

        is CBTProgressItem.SelfCompassionReflectionCompletion ->
            "Self-Compassion Reflection"
    }
}


/*
 * ==========================================================
 * CBT ACTIVITY CATEGORY
 * ==========================================================
 */

private fun getCBTActivityCategory(
    item: CBTProgressItem
): String {

    return when (item) {

        is CBTProgressItem.ActivityCompletion ->
            item.completion.activityType.ifBlank {
                "Behavioral"
            }

        is CBTProgressItem.FiveMinuteStarterCompletion ->
            "Behavioral"

        is CBTProgressItem.MindfulMeditationCompletion ->
            "Mindfulness"

        is CBTProgressItem.Grounding54321Completion ->
            "Mindfulness"

        is CBTProgressItem.ABCModelCompletion ->
            "Cognitive"

        is CBTProgressItem.SelfCompassionReflectionCompletion ->
            "Mindfulness"
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
    viewModel: MoodViewModel = hiltViewModel(),
    cbtProgressViewModel: CBTProgressViewModel = hiltViewModel()
) {

    val moods by
    viewModel
        .moodList
        .collectAsState()

    val cbtProgressItems by
    cbtProgressViewModel
        .progressItems
        .collectAsState(
            initial = emptyList()
        )


    /*
     * ======================================================
     * THEME COLORS
     * ======================================================
     */

    val background =
        MaterialTheme.colorScheme.background

    val surface =
        MaterialTheme.colorScheme.surface

    val textDark =
        MaterialTheme.colorScheme.onBackground

    val textSecondary =
        MaterialTheme.colorScheme.onSurfaceVariant

    val lavender =
        MaterialTheme.colorScheme.primary

    val softLavender =
        MaterialTheme.colorScheme.secondaryContainer

    val onSoftLavender =
        MaterialTheme.colorScheme.onSecondaryContainer


    /*
     * ======================================================
     * LOCALE
     * ======================================================
     */

    val configuration =
        LocalConfiguration.current

    val currentLocale =
        configuration.locales[0]


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
     * RECURRING TRIGGERS
     * ======================================================
     */

    val recurringTriggers =
        getRecurringTriggers(
            moods
        )


    /*
     * ======================================================
     * GRAPH ENTRIES
     * ======================================================
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
     */

    val timeLabels =
        selectedDayMoods.map { mood ->

            getMoodTime(
                mood.timestamp
            )
        }


    /*
     * ======================================================
     * MOOD ANALYSIS
     * ======================================================
     */

    val averageMoodScore =
        if (selectedDayMoods.isNotEmpty()) {

            selectedDayMoods
                .map {
                    moodScore(it.emoji)
                }
                .average()
                .toFloat()

        } else {

            0f
        }


    val averageMoodLabel =
        when {

            averageMoodScore >= 4.5f ->
                "Mostly Happy"

            averageMoodScore >= 3.5f ->
                "Mostly Calm"

            averageMoodScore >= 2.5f ->
                "Mostly Neutral"

            averageMoodScore >= 1.5f ->
                "Mostly Sad"

            averageMoodScore > 0f ->
                "Mostly Angry"

            else ->
                "No mood data"
        }


    /*
     * ======================================================
     * CBT ANALYSIS
     * ======================================================
     */

    val selectedDayCBTItems =
        cbtProgressItems
            .filter { item ->

                getCompletionDate(
                    item.completedAt
                ) == selectedDate
            }


    /*
     * The completed CBT activity timeline is the
     * authoritative source for the selected day's
     * completed activity count.
     *
     * This ensures activities that were actually
     * completed are reflected immediately here,
     * without depending on a separate daily-progress
     * counter being updated.
     */

    val cbtActivityCount =
        selectedDayCBTItems.size


    val cbtCategories =
        selectedDayCBTItems
            .groupingBy { item ->
                getCBTActivityCategory(item)
            }
            .eachCount()


    val cbtActivityNames =
        selectedDayCBTItems
            .map { item ->
                getCBTActivityLabel(item)
            }
            .distinct()


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
     * ANDROID CHART COLORS
     * ======================================================
     */

    val chartPrimary =
        AndroidColor.argb(
            (lavender.alpha * 255).toInt(),
            (lavender.red * 255).toInt(),
            (lavender.green * 255).toInt(),
            (lavender.blue * 255).toInt()
        )

    val chartSecondary =
        AndroidColor.argb(
            (MaterialTheme.colorScheme.secondary.alpha * 255).toInt(),
            (MaterialTheme.colorScheme.secondary.red * 255).toInt(),
            (MaterialTheme.colorScheme.secondary.green * 255).toInt(),
            (MaterialTheme.colorScheme.secondary.blue * 255).toInt()
        )

    val chartText =
        AndroidColor.argb(
            (textSecondary.alpha * 255).toInt(),
            (textSecondary.red * 255).toInt(),
            (textSecondary.green * 255).toInt(),
            (textSecondary.blue * 255).toInt()
        )

    val chartGrid =
        AndroidColor.argb(
            (MaterialTheme.colorScheme.outlineVariant.alpha * 255).toInt(),
            (MaterialTheme.colorScheme.outlineVariant.red * 255).toInt(),
            (MaterialTheme.colorScheme.outlineVariant.green * 255).toInt(),
            (MaterialTheme.colorScheme.outlineVariant.blue * 255).toInt()
        )


    /*
     * ======================================================
     * SCREEN
     * ======================================================
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
                    .navigationBarsPadding()
                    .verticalScroll(
                        rememberScrollState()
                    ),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {


            /*
             * ==================================================
             * HEADER
             * ==================================================
             */

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(
                        bottomStart = 28.dp,
                        bottomEnd = 28.dp,
                        topStart = 22.dp,
                        topEnd = 22.dp
                    ),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.Transparent
                    ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
            ) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(

                                Brush.verticalGradient(

                                    listOf(

                                        MaterialTheme
                                            .colorScheme
                                            .primary
                                            .copy(
                                                alpha = 0.82f
                                            ),

                                        MaterialTheme
                                            .colorScheme
                                            .primary
                                            .copy(
                                                alpha = 0.58f
                                            ),

                                        MaterialTheme
                                            .colorScheme
                                            .secondary
                                            .copy(
                                                alpha = 0.42f
                                            )
                                    )
                                )
                            )
                            .padding(
                                horizontal = 20.dp,
                                vertical = 20.dp
                            )
                ) {

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
                            Color.White
                    )

                    Spacer(
                        modifier =
                            Modifier.height(5.dp)
                    )

                    Text(

                        text =
                            "View your mood changes for a specific day.",

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            Color.White.copy(
                                alpha = 0.88f
                            )
                    )
                }
            }


            /*
             * ==================================================
             * CONTENT
             * ==================================================
             */

            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 18.dp
                        ),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

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
                                surface
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
                                        onSoftLavender
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
                                surface
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                ) {

                    if (entries.isEmpty()) {

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

                                val dataSet =
                                    LineDataSet(
                                        entries,
                                        "Mood"
                                    ).apply {

                                        color =
                                            chartPrimary

                                        valueTextColor =
                                            chartText

                                        valueTextSize =
                                            8f

                                        lineWidth =
                                            2.5f

                                        circleRadius =
                                            4.5f

                                        setCircleColor(
                                            chartSecondary
                                        )

                                        setDrawValues(
                                            false
                                        )

                                        setDrawFilled(
                                            true
                                        )

                                        fillColor =
                                            chartSecondary

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

                                chart.description =
                                    Description().apply {
                                        text = ""
                                    }

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
                                        chartText

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

                                chart.axisLeft.apply {

                                    setDrawGridLines(
                                        true
                                    )

                                    gridColor =
                                        chartGrid

                                    textColor =
                                        chartText

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

                                chart.axisRight.isEnabled =
                                    false

                                chart.legend.isEnabled =
                                    false

                                chart.setTouchEnabled(
                                    true
                                )

                                chart.setPinchZoom(
                                    false
                                )

                                chart.animateX(
                                    1200,
                                    Easing.EaseInOutCubic
                                )

                                chart.invalidate()
                            }
                        )
                    }
                }


                /*
                 * ==================================================
                 * MOOD + CBT ANALYSIS
                 * ==================================================
                 */

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(20.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                surface
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 14.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {

                        Text(

                            text =
                                "Daily analysis",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.SemiBold,

                            color =
                                textDark
                        )

                        Row(

                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {

                            Card(

                                modifier =
                                    Modifier.weight(1f),

                                shape =
                                    RoundedCornerShape(16.dp),

                                colors =
                                    CardDefaults.cardColors(
                                        containerColor =
                                            softLavender
                                    )
                            ) {

                                Column(

                                    modifier =
                                        Modifier.padding(
                                            12.dp
                                        )
                                ) {

                                    Text(

                                        text =
                                            "Average mood",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .labelMedium,

                                        color =
                                            textSecondary
                                    )

                                    Text(

                                        text =
                                            if (
                                                averageMoodScore > 0f
                                            ) {

                                                String.format(
                                                    currentLocale,
                                                    "%.1f / 5",
                                                    averageMoodScore
                                                )

                                            } else {

                                                "—"
                                            },

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge,

                                        fontWeight =
                                            FontWeight.Bold,

                                        color =
                                            textDark
                                    )

                                    Text(

                                        text =
                                            averageMoodLabel,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall,

                                        color =
                                            textSecondary
                                    )
                                }
                            }


                            Card(

                                modifier =
                                    Modifier.weight(1f),

                                shape =
                                    RoundedCornerShape(16.dp),

                                colors =
                                    CardDefaults.cardColors(
                                        containerColor =
                                            softLavender
                                    )
                            ) {

                                Column(

                                    modifier =
                                        Modifier.padding(
                                            12.dp
                                        )
                                ) {

                                    Text(

                                        text =
                                            "CBT completed",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .labelMedium,

                                        color =
                                            textSecondary
                                    )

                                    Text(

                                        text =
                                            cbtActivityCount
                                                .toString(),

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge,

                                        fontWeight =
                                            FontWeight.Bold,

                                        color =
                                            textDark
                                    )

                                    Text(

                                        text =
                                            if (
                                                cbtActivityCount == 1
                                            ) {
                                                "exercise"
                                            } else {
                                                "exercises"
                                            },

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall,

                                        color =
                                            textSecondary
                                    )
                                }
                            }
                        }


                        if (
                            cbtActivityCount > 0
                        ) {

                            Text(

                                text =
                                    "CBT activity",

                                style =
                                    MaterialTheme
                                        .typography
                                        .labelLarge,

                                fontWeight =
                                    FontWeight.SemiBold,

                                color =
                                    textDark
                            )

                            cbtActivityNames.forEach { activityName ->

                                Text(

                                    text =
                                        "• $activityName",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodySmall,

                                    color =
                                        textSecondary
                                )
                            }


                            if (
                                cbtCategories.isNotEmpty()
                            ) {

                                Spacer(
                                    modifier =
                                        Modifier.height(2.dp)
                                )

                                Text(

                                    text =
                                        cbtCategories.entries
                                            .sortedByDescending {
                                                it.value
                                            }
                                            .joinToString(
                                                separator = "  •  "
                                            ) {
                                                "${it.key}: ${it.value}"
                                            },

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodySmall,

                                    color =
                                        textSecondary
                                )
                            }

                        } else {

                            Text(

                                text =
                                    "No CBT exercises were completed on this day.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    textSecondary
                            )
                        }


                        Text(

                            text =
                                when {

                                    selectedDayMoods.isEmpty() ->
                                        "Mood data will help build a clearer picture of how your day is changing."

                                    cbtActivityCount == 0 ->
                                        "You recorded your mood, but no CBT activity was completed on this day."

                                    averageMoodScore >= 4f ->
                                        "Your mood was generally positive on a day when you also engaged with CBT."

                                    averageMoodScore >= 3f ->
                                        "Your mood was around neutral while you also engaged with CBT."

                                    else ->
                                        "You recorded lower mood alongside CBT activity. This can help you track how exercises relate to difficult days."
                                },

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                textSecondary
                        )
                    }
                }


                /*
                 * ==================================================
                 * RECURRING TRIGGERS
                 * ==================================================
                 */

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(20.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                surface
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                ) {

                    Column(

                        modifier =
                            Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 14.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {

                        Text(

                            text =
                                "Recurring Triggers",

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
                                "Triggers that have repeatedly appeared alongside lower mood.",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodySmall,

                            color =
                                textSecondary
                        )

                        if (
                            recurringTriggers.isEmpty()
                        ) {

                            Text(

                                text =
                                    "No recurring low-mood triggers identified yet. Keep recording your moods and triggers so patterns can become clearer over time.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    textSecondary
                            )

                        } else {

                            recurringTriggers.forEach { trigger ->

                                Card(

                                    modifier =
                                        Modifier.fillMaxWidth(),

                                    shape =
                                        RoundedCornerShape(16.dp),

                                    colors =
                                        CardDefaults.cardColors(
                                            containerColor =
                                                softLavender
                                        )
                                ) {

                                    Column(

                                        modifier =
                                            Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 11.dp
                                            ),

                                        verticalArrangement =
                                            Arrangement.spacedBy(4.dp)
                                    ) {

                                        Text(

                                            text =
                                                trigger.trigger,

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .titleSmall,

                                            fontWeight =
                                                FontWeight.SemiBold,

                                            color =
                                                textDark
                                        )

                                        Text(

                                            text =
                                                "${trigger.lowMoodOccurrences} of ${trigger.totalOccurrences} entries linked with low mood",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodySmall,

                                            color =
                                                textSecondary
                                        )

                                        Text(

                                            text =
                                                getRecurringTriggerLabel(
                                                    trigger
                                                ),

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .labelMedium,

                                            fontWeight =
                                                FontWeight.SemiBold,

                                            color =
                                                lavender
                                        )

                                        Text(

                                            text =
                                                getRecurringTriggerDescription(
                                                    trigger
                                                ),

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodySmall,

                                            color =
                                                textSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


                /*
                 * ==================================================
                 * MOOD SCALE
                 * ==================================================
                 */

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

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

                Spacer(
                    modifier =
                        Modifier.height(90.dp)
                )
            }
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

    val scoreColor =
        MaterialTheme.colorScheme.primary

    val labelColor =
        MaterialTheme.colorScheme.onSurfaceVariant

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
                scoreColor
        )

        Text(

            text =
                label,

            fontSize =
                9.sp,

            color =
                labelColor
        )
    }
}