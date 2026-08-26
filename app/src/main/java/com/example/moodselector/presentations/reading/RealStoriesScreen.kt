package com.example.moodselector.presentations.reading

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class RealStory(
    val title: String,
    val summary: String,
    val source: String,
    val url: String
)

private val realStories = listOf(

    RealStory(
        title = "Learning to live with anxiety",
        summary =
            "A personal experience of living with anxiety and gradually finding ways to understand it, talk about it, and manage difficult moments.",
        source = "Mind · Shiva",
        url =
            "https://www.mind.org.uk/information-support/your-stories/how-i-learnt-to-accept-my-anxiety/"
    ),

    RealStory(
        title = "Living with depression and anxiety",
        summary =
            "A woman's experience of living with depression and anxiety and discovering how treatment, including CBT, could help her regain parts of everyday life.",
        source = "Mind · Rachel",
        url =
            "https://www.mind.org.uk/information-support/your-stories/how-treatment-helped-me-to-live-with-depression-and-anxiety/"
    ),

    RealStory(
        title = "CBT and me",
        summary =
            "A personal account of anxiety and depression, seeking therapy, and discovering how CBT techniques became useful beyond the therapy sessions.",
        source = "Mind · Sophie",
        url =
            "https://www.mind.org.uk/information-support/your-stories/cbt-and-me/"
    ),

    RealStory(
        title = "When anxiety became difficult to talk about",
        summary =
            "A woman's experience of anxiety, therapy and learning that talking about mental health could be part of finding ways to cope.",
        source = "Mind · Shetal",
        url =
            "https://www.mind.org.uk/information-support/your-stories/my-culture-stopped-me-talking-about-my-anxiety/"
    ),

    RealStory(
        title = "How the menopause affected my mental health",
        summary =
            "Gillian's personal experience of perimenopause, anxiety, low mood, poor sleep and difficult life circumstances, and how reaching out for support helped her gradually rebuild her wellbeing.",
        source = "Mind · Gillian",
        url =
            "https://www.mind.org.uk/information-support/your-stories/how-the-menopause-affected-my-mental-health/"
    ),

    RealStory(
        title = "Depression and anxiety: how running helped me",
        summary =
            "Claire's personal experience of anxiety and depression, including living with an underactive thyroid, seeking support and discovering that running became an important part of her wellbeing journey.",
        source = "Mind · Claire",
        url =
            "https://www.mind.org.uk/information-support/your-stories/depression-and-anxiety-how-running-helped-me/"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealStoriesScreen(
    onBackClick: () -> Unit
) {

    val context =
        LocalContext.current

    /*
     * --------------------------------------------------
     * THEME COLORS
     * --------------------------------------------------
     *
     * The screen background is taken directly from the
     * active Material theme so it follows the app's
     * light/dark appearance automatically.
     */

    val background =
        MaterialTheme.colorScheme.background

    val textDark =
        MaterialTheme.colorScheme.onSurface

    val secondaryText =
        MaterialTheme.colorScheme.onSurfaceVariant

    val darkPurple =
        Color(0xFF6E63A8)

    val primaryPurple =
        Color(0xFF8F84C7)

    val softRose =
        if (background.luminance() > 0.5f) {
            Color(0xFFF1E2E8)
        } else {
            Color(0xFF3A2D38)
        }

    val glassWhite =
        if (background.luminance() > 0.5f) {
            Color(0xEAF9F6FC)
        } else {
            Color(0xE82B2633)
        }

    /*
     * --------------------------------------------------
     * SCREEN
     * --------------------------------------------------
     */

    Scaffold(

        containerColor =
            background,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            "Real Stories",

                        fontWeight =
                            FontWeight.Bold
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick =
                            onBackClick
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription =
                                "Back"
                        )
                    }
                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(

                            containerColor =
                                background
                        )
            )
        }

    ) { paddingValues ->

        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    ),

            contentPadding =
                PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(
                    16.dp
                )
        ) {

            /*
             * ==================================================
             * INTRODUCTION
             * ==================================================
             */

            item {

                Card(

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(
                            26.dp
                        ),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                glassWhite
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                ) {

                    Box(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            darkPurple.copy(
                                                alpha = 0.12f
                                            ),
                                            primaryPurple.copy(
                                                alpha = 0.05f
                                            )
                                        )
                                    )
                                )
                    ) {

                        Column(

                            modifier =
                                Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 20.dp
                                )
                        ) {

                            Row(
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
                                                darkPurple.copy(
                                                    alpha = 0.12f
                                                )
                                            ),

                                    contentAlignment =
                                        Alignment.Center
                                ) {

                                    Icon(

                                        imageVector =
                                            Icons.Default.MenuBook,

                                        contentDescription =
                                            null,

                                        tint =
                                            darkPurple,

                                        modifier =
                                            Modifier.size(
                                                23.dp
                                            )
                                    )
                                }

                                Spacer(
                                    modifier =
                                        Modifier.width(
                                            12.dp
                                        )
                                )

                                Text(

                                    text =
                                        "Real stories, real experiences",

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

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        14.dp
                                    )
                            )

                            Text(

                                text =
                                    "Everyone's experience with mental health is different. Explore personal stories about anxiety, depression, CBT, and finding ways to cope.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium,

                                color =
                                    secondaryText
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        10.dp
                                    )
                            )

                            Text(

                                text =
                                    "These are personal experiences, not medical advice.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodySmall,

                                color =
                                    secondaryText.copy(
                                        alpha = 0.85f
                                    )
                            )
                        }
                    }
                }
            }

            /*
             * ==================================================
             * STORIES
             * ==================================================
             */

            items(
                items =
                    realStories
            ) { story ->

                RealStoryCard(

                    story =
                        story,

                    onClick = {

                        val intent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(
                                    story.url
                                )
                            )

                        context.startActivity(
                            intent
                        )
                    },

                    textDark =
                        textDark,

                    secondaryText =
                        secondaryText,

                    darkPurple =
                        darkPurple,

                    softRose =
                        softRose
                )
            }

            item {

                Spacer(
                    modifier =
                        Modifier.height(
                            24.dp
                        )
                )
            }
        }
    }
}

@Composable
private fun RealStoryCard(
    story: RealStory,
    onClick: () -> Unit,
    textDark: Color,
    secondaryText: Color,
    darkPurple: Color,
    softRose: Color
) {

    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(
                    onClick =
                        onClick
                ),

        shape =
            RoundedCornerShape(
                22.dp
            ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    softRose.copy(
                        alpha = 0.88f
                    )
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(

            modifier =
                Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 18.dp
                )
        ) {

            Row(
                verticalAlignment =
                    Alignment.Top
            ) {

                Box(

                    modifier =
                        Modifier
                            .size(44.dp)
                            .clip(
                                CircleShape
                            )
                            .background(
                                MaterialTheme
                                    .colorScheme
                                    .surface
                                    .copy(
                                        alpha = 0.65f
                                    )
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.AutoAwesome,

                        contentDescription =
                            null,

                        tint =
                            darkPurple,

                        modifier =
                            Modifier.size(
                                21.dp
                            )
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(
                            12.dp
                        )
                )

                Column(
                    modifier =
                        Modifier.weight(
                            1f
                        )
                ) {

                    Text(

                        text =
                            story.title,

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
                            Modifier.height(
                                4.dp
                            )
                    )

                    Text(

                        text =
                            story.source,

                        style =
                            MaterialTheme
                                .typography
                                .labelSmall,

                        color =
                            darkPurple,

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            Text(

                text =
                    story.summary,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                color =
                    secondaryText
            )

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.End,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(

                    text =
                        "Read the full story",

                    style =
                        MaterialTheme
                            .typography
                            .labelLarge,

                    color =
                        darkPurple,

                    fontWeight =
                        FontWeight.SemiBold
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            4.dp
                        )
                )

                Icon(

                    imageVector =
                        Icons.Default.ChevronRight,

                    contentDescription =
                        "Read full story",

                    tint =
                        darkPurple,

                    modifier =
                        Modifier.size(
                            20.dp
                        )
                )
            }
        }
    }
}
