package com.example.moodselector.presentations.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodselector.R
import com.example.moodselector.presentations.auth.AuthViewModel

data class MoodOption(
    val label: String,
    val emoji: String
)

@Composable
fun MoodInsightsScreen(
    onLogout: () -> Unit = {},
    viewModel: MoodViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val moods by viewModel.moodList.collectAsState()

    var moodText by remember {
        mutableStateOf("")
    }

    var selectedMood by remember {
        mutableStateOf("Happy")
    }


    /*
     * ==========================================================
     * MOOD OPTIONS
     * ==========================================================
     *
     * These are real Unicode emoji characters rather than
     * Material icons, so Android can render them using its
     * native emoji font.
     */

    val moodOptions = listOf(

        MoodOption(
            label = "Happy",
            emoji = "😊"
        ),

        MoodOption(
            label = "Calm",
            emoji = "😌"
        ),

        MoodOption(
            label = "Neutral",
            emoji = "😐"
        ),

        MoodOption(
            label = "Sad",
            emoji = "😔"
        ),

        MoodOption(
            label = "Angry",
            emoji = "😠"
        )
    )


    /*
     * ==========================================================
     * COLORS
     * ==========================================================
     */

    val darkPurple =
        Color(0xFF6E63A8)

    val primaryPurple =
        Color(0xFF8F84C7)

    val softLavender =
        Color(0xFFE8E1F5)

    val mutedRose =
        Color(0xFFF1E2E8)

    val mistBlue =
        Color(0xFFE2EBF2)

    val warmGlass =
        Color(0xCCF4EFFA)

    val textDark =
        Color(0xFF1F1C24)


    /*
     * ==========================================================
     * MOOD CALCULATION
     * ==========================================================
     */

    val scores =
        moods.map {

            when (it.emoji) {

                "Happy" ->
                    5

                "Calm" ->
                    4

                "Neutral" ->
                    3

                "Sad" ->
                    2

                "Angry" ->
                    1

                else ->
                    3
            }
        }

    val averageMood =
        if (scores.isNotEmpty()) {
            scores.average()
        } else {
            0.0
        }

    val insight =
        when {

            averageMood >= 4 ->
                "Your emotional wellness is improving beautifully ✨"

            averageMood >= 3 ->
                "Your emotional state has remained balanced 🌸"

            else ->
                "You've been emotionally overwhelmed lately 💙"
        }


    /*
     * ==========================================================
     * BACKGROUND
     * ==========================================================
     */

    Box(
        modifier =
            Modifier.fillMaxSize()
    ) {

        Image(
            painter =
                painterResource(
                    id =
                        R.drawable.lavender_scenery
                ),

            contentDescription =
                null,

            modifier =
                Modifier.fillMaxSize(),

            contentScale =
                ContentScale.Crop
        )

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0x44000000),
                                Color(0x33000000),
                                Color(0x66000000)
                            )
                        )
                    )
        )


        /*
         * ======================================================
         * CONTENT
         * ======================================================
         */

        Scaffold(
            containerColor =
                Color.Transparent
        ) { paddingValues ->

            LazyColumn(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(
                            paddingValues
                        )
                        .statusBarsPadding()
                        .navigationBarsPadding(),

                contentPadding =
                    PaddingValues(
                        bottom = 90.dp
                    )
            ) {


                /*
                 * ==================================================
                 * HERO SECTION
                 * ==================================================
                 */

                item {

                    Box {

                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(245.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 30.dp,
                                            bottomEnd = 30.dp
                                        )
                                    )
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                darkPurple.copy(
                                                    alpha = 0.78f
                                                ),
                                                primaryPurple.copy(
                                                    alpha = 0.60f
                                                ),
                                                Color.Transparent
                                            )
                                        )
                                    )
                        ) {

                            Column(
                                modifier =
                                    Modifier.padding(
                                        horizontal = 18.dp,
                                        vertical = 16.dp
                                    )
                            ) {

                                Row(
                                    modifier =
                                        Modifier.fillMaxWidth(),

                                    horizontalArrangement =
                                        Arrangement.SpaceBetween,

                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Column {

                                        Text(
                                            text =
                                                "Hello 🌸",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .titleLarge,

                                            fontWeight =
                                                FontWeight.Bold,

                                            color =
                                                Color.White
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.height(2.dp)
                                        )

                                        Text(
                                            text =
                                                "Track your emotional wellbeing",

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodySmall,

                                            color =
                                                Color.White.copy(
                                                    alpha = 0.9f
                                                )
                                        )
                                    }

                                    Surface(
                                        shape =
                                            CircleShape,

                                        color =
                                            Color.White.copy(
                                                alpha = 0.16f
                                            )
                                    ) {

                                        Box(
                                            modifier =
                                                Modifier.size(40.dp),

                                            contentAlignment =
                                                Alignment.Center
                                        ) {

                                            Icon(
                                                imageVector =
                                                    Icons.Default.Notifications,

                                                contentDescription =
                                                    null,

                                                tint =
                                                    Color.White,

                                                modifier =
                                                    Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(
                                    modifier =
                                        Modifier.height(24.dp)
                                )

                                Text(
                                    text =
                                        String.format(
                                            "%.1f",
                                            averageMood
                                        ),

                                    style =
                                        MaterialTheme
                                            .typography
                                            .displaySmall,

                                    fontWeight =
                                        FontWeight.ExtraBold,

                                    color =
                                        Color.White
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(2.dp)
                                )

                                Text(
                                    text =
                                        "Current emotional score",

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodyMedium,

                                    color =
                                        Color.White.copy(
                                            alpha = 0.92f
                                        )
                                )
                            }
                        }


                        /*
                         * ==================================================
                         * FLOATING SUMMARY CARD
                         * ==================================================
                         */

                        Card(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    )
                                    .offset(
                                        y = 180.dp
                                    )
                                    .shadow(
                                        elevation = 8.dp,
                                        shape =
                                            RoundedCornerShape(
                                                22.dp
                                            )
                                    ),

                            shape =
                                RoundedCornerShape(
                                    22.dp
                                ),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        warmGlass
                                )
                        ) {

                            Row(

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 14.dp
                                        ),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Column {

                                    Text(
                                        text =
                                            "Average Mood",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall,

                                        color =
                                            Color.Gray
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(3.dp)
                                    )

                                    Text(
                                        text =
                                            String.format(
                                                "%.1f",
                                                averageMood
                                            ),

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge,

                                        fontWeight =
                                            FontWeight.Bold,

                                        color =
                                            textDark
                                    )
                                }

                                Column {

                                    Text(
                                        text =
                                            "Trend",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .bodySmall,

                                        color =
                                            Color.Gray
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(3.dp)
                                    )

                                    Text(
                                        text =
                                            when {

                                                averageMood >= 4 ->
                                                    "Positive"

                                                averageMood >= 3 ->
                                                    "Stable"

                                                else ->
                                                    "Low"
                                            },

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleMedium,

                                        fontWeight =
                                            FontWeight.Bold,

                                        color =
                                            darkPurple
                                    )
                                }
                            }
                        }
                    }
                }


                item {

                    Spacer(
                        modifier =
                            Modifier.height(105.dp)
                    )
                }


                /*
                 * ==================================================
                 * MOOD PICKER
                 * ==================================================
                 */

                item {

                    Card(

                        modifier =
                            Modifier.padding(
                                horizontal = 16.dp
                            ),

                        shape =
                            RoundedCornerShape(
                                24.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    softLavender.copy(
                                        alpha = 0.82f
                                    )
                            )
                    ) {

                        Column(

                            modifier =
                                Modifier.padding(
                                    16.dp
                                )
                        ) {

                            Text(
                                text =
                                    "How do you feel today?",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    textDark
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(18.dp)
                            )


                            /*
                             * --------------------------------------------------
                             * EMOJI MOOD SELECTOR
                             * --------------------------------------------------
                             */

                            Row(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween,

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                moodOptions.forEach { mood ->

                                    val selected =
                                        selectedMood ==
                                                mood.label

                                    Box(

                                        modifier =
                                            Modifier
                                                .size(
                                                    if (selected) {
                                                        56.dp
                                                    } else {
                                                        48.dp
                                                    }
                                                )
                                                .clip(
                                                    CircleShape
                                                )
                                                .background(

                                                    if (selected) {

                                                        Brush.linearGradient(
                                                            listOf(
                                                                darkPurple,
                                                                primaryPurple
                                                            )
                                                        )

                                                    } else {

                                                        Brush.linearGradient(
                                                            listOf(
                                                                Color(0xFFF1ECFA),
                                                                Color(0xFFE5DEF4)
                                                            )
                                                        )
                                                    }
                                                )
                                                .clickable {

                                                    selectedMood =
                                                        mood.label
                                                },

                                        contentAlignment =
                                            Alignment.Center
                                    ) {

                                        Text(

                                            text =
                                                mood.emoji,

                                            fontSize =
                                                if (selected) {
                                                    27.sp
                                                } else {
                                                    24.sp
                                                }
                                        )
                                    }
                                }
                            }


                            Spacer(
                                modifier =
                                    Modifier.height(16.dp)
                            )


                            /*
                             * --------------------------------------------------
                             * EMOTION DESCRIPTION
                             * --------------------------------------------------
                             */

                            OutlinedTextField(

                                value =
                                    moodText,

                                onValueChange = {
                                    moodText = it
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                label = {
                                    Text(
                                        "Describe your emotions"
                                    )
                                },

                                textStyle =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                shape =
                                    RoundedCornerShape(
                                        16.dp
                                    ),

                                colors =
                                    TextFieldDefaults.colors(

                                        focusedContainerColor =
                                            Color.White.copy(
                                                alpha = 0.35f
                                            ),

                                        unfocusedContainerColor =
                                            Color.White.copy(
                                                alpha = 0.18f
                                            ),

                                        focusedIndicatorColor =
                                            Color.Transparent,

                                        unfocusedIndicatorColor =
                                            Color.Transparent
                                    )
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(16.dp)
                            )


                            /*
                             * --------------------------------------------------
                             * SAVE MOOD
                             * --------------------------------------------------
                             */

                            Button(

                                onClick = {

                                    if (
                                        moodText.isNotBlank()
                                    ) {

                                        viewModel.addMood(

                                            mood =
                                                moodText,

                                            emoji =
                                                selectedMood
                                        )

                                        moodText = ""
                                    }
                                },

                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),

                                shape =
                                    RoundedCornerShape(
                                        16.dp
                                    ),

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            darkPurple
                                    )
                            ) {

                                Text(
                                    text =
                                        "Save Mood",

                                    fontSize =
                                        14.sp,

                                    fontWeight =
                                        FontWeight.SemiBold,

                                    color =
                                        Color.White
                                )
                            }
                        }
                    }
                }


                /*
                 * ==================================================
                 * AI INSIGHT
                 * ==================================================
                 */

                item {

                    Card(

                        modifier =
                            Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 14.dp
                            ),

                        shape =
                            RoundedCornerShape(
                                22.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    mistBlue.copy(
                                        alpha = 0.78f
                                    )
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    16.dp
                                )
                        ) {

                            Text(
                                text =
                                    "AI Insight ✨",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleMedium,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    textDark
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(6.dp)
                            )

                            Text(
                                text =
                                    insight,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    textDark
                            )
                        }
                    }
                }


                /*
                 * ==================================================
                 * RECENT ENTRIES
                 * ==================================================
                 */

                item {

                    Text(
                        text =
                            "Recent Entries",

                        modifier =
                            Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 4.dp
                            ),

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.White
                    )
                }


                items(
                    moods.takeLast(5).reversed()
                ) { mood ->

                    Card(

                        modifier =
                            Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 5.dp
                            ),

                        shape =
                            RoundedCornerShape(
                                20.dp
                            ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    mutedRose.copy(
                                        alpha = 0.80f
                                    )
                            )
                    ) {

                        Row(

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 14.dp,
                                        vertical = 12.dp
                                    ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Box(

                                modifier =
                                    Modifier
                                        .size(46.dp)
                                        .clip(
                                            CircleShape
                                        )
                                        .background(
                                            Color.White.copy(
                                                alpha = 0.35f
                                            )
                                        ),

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(

                                    text =
                                        when (mood.emoji) {

                                            "Happy" ->
                                                "✨"

                                            "Calm" ->
                                                "🌿"

                                            "Neutral" ->
                                                "☁️"

                                            "Sad" ->
                                                "💙"

                                            "Angry" ->
                                                "🔥"

                                            else ->
                                                "🌸"
                                        },

                                    fontSize =
                                        22.sp
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.width(12.dp)
                            )

                            Column {

                                Text(
                                    text =
                                        mood.mood,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodyLarge,

                                    fontWeight =
                                        FontWeight.SemiBold,

                                    color =
                                        textDark
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(2.dp)
                                )

                                Text(
                                    text =
                                        mood.timestamp,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodySmall,

                                    color =
                                        Color.Gray
                                )
                            }
                        }
                    }
                }


                /*
                 * ==================================================
                 * LOGOUT
                 * ==================================================
                 */

                item {

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    OutlinedButton(

                        onClick = {

                            authViewModel.signOut()

                            onLogout()
                        },

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                )
                                .height(48.dp),

                        shape =
                            RoundedCornerShape(
                                18.dp
                            ),

                        colors =
                            ButtonDefaults
                                .outlinedButtonColors(
                                    contentColor =
                                        Color(0xFFB44A5A)
                                )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Logout,

                            contentDescription =
                                "Log out",

                            modifier =
                                Modifier.size(19.dp)
                        )

                        Spacer(
                            modifier =
                                Modifier.width(7.dp)
                        )

                        Text(
                            text =
                                "Log Out",

                            fontSize =
                                14.sp,

                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }
                }


                item {

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )
                }
            }
        }
    }
}

