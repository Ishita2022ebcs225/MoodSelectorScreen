package com.example.moodselector.presentations.cbt.exercises

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

private val BackgroundTop = Color(0xFFEDEBFF)
private val BackgroundMiddle = Color(0xFFF6EAF7)
private val BackgroundBottom = Color(0xFFE7F6F3)

private val DeepPurple = Color(0xFF5E4275)
private val Purple = Color(0xFF765A86)
private val SoftPurple = Color(0xFFE7D9F0)

private val TextPrimary = Color(0xFF292638)
private val TextSecondary = Color(0xFF777282)

private val SurfaceWhite = Color.White.copy(alpha = 0.80f)

private data class GroundingSense(
    val number: Int,
    val title: String,
    val instruction: String,
    val hint: String,
    val icon: ImageVector
)

@Composable
fun Grounding54321Screen(
    onBackClick: () -> Unit,
    onComplete: () -> Unit,
    viewModel: Grounding54321ViewModel = hiltViewModel()
) {

    val senses = remember {
        listOf(
            GroundingSense(
                number = 5,
                title = "Things you can see",
                instruction =
                    "Look around you and name five things you can see.",
                hint =
                    "For example: a window, a plant, a cup...",
                icon = Icons.Default.Visibility
            ),
            GroundingSense(
                number = 4,
                title = "Things you can touch",
                instruction =
                    "Notice four things you can touch or physically feel.",
                hint =
                    "For example: your clothing, the chair, your phone...",
                icon = Icons.Default.TouchApp
            ),
            GroundingSense(
                number = 3,
                title = "Things you can hear",
                instruction =
                    "Pause and identify three sounds you can hear.",
                hint =
                    "For example: traffic, birds, a fan...",
                icon = Icons.Default.Hearing
            ),
            GroundingSense(
                number = 2,
                title = "Things you can smell",
                instruction =
                    "Notice two things you can smell.",
                hint =
                    "For example: coffee, perfume, fresh air...",
                icon = Icons.Default.Air
            ),
            GroundingSense(
                number = 1,
                title = "Something you can taste",
                instruction =
                    "Notice one thing you can taste right now.",
                hint =
                    "You can also imagine a taste if nothing is available.",
                icon = Icons.Default.Restaurant
            )
        )
    }

    var currentStep by remember {
        mutableIntStateOf(0)
    }

    var isCompleted by remember {
        mutableStateOf(false)
    }

    /*
     * Temporary responses for each grounding sense.
     *
     * These are intentionally NOT persisted.
     * The completion record stores only the final reflection.
     */
    val responses = remember {
        mutableStateListOf(
            "",
            "",
            "",
            "",
            ""
        )
    }

    val currentSense = senses[currentStep]

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            BackgroundTop,
            BackgroundMiddle,
            BackgroundBottom
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
                .navigationBarsPadding()
        ) {

            /*
             * TOP BAR
             */

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text =
                        if (isCompleted) {
                            "Complete"
                        } else {
                            "${currentStep + 1} / ${senses.size}"
                        },
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            /*
             * TITLE
             */

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "5-4-3-2-1 Grounding",
                    color = TextPrimary,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text =
                        "Reconnect with the present, one sense at a time.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            /*
             * PROGRESS
             */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                senses.forEachIndexed { index, _ ->

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .clip(
                                RoundedCornerShape(50)
                            )
                            .background(
                                if (
                                    isCompleted ||
                                    index <= currentStep
                                ) {
                                    Purple
                                } else {
                                    SoftPurple
                                }
                            )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            if (isCompleted) {

                /*
                 * COMPLETION STATE
                 */

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(SoftPurple),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = DeepPurple,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    Text(
                        text = "Grounding complete",
                        color = DeepPurple,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text =
                            "Take a moment to notice how you feel now. " +
                                    "You have brought your attention back " +
                                    "to the present moment.",
                        color = TextSecondary,
                        fontSize = 15.sp,
                        lineHeight = 23.sp
                    )
                }

            } else {

                /*
                 * CURRENT SENSE
                 */

                AnimatedContent(
                    targetState = currentStep,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "grounding_step"
                ) { step ->

                    val sense = senses[step]

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(108.dp)
                                .clip(CircleShape)
                                .background(SoftPurple),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = sense.number.toString(),
                                color = DeepPurple,
                                fontSize = 52.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        Icon(
                            imageVector = sense.icon,
                            contentDescription = null,
                            tint = Purple,
                            modifier = Modifier.size(30.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = sense.title,
                            color = DeepPurple,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = sense.instruction,
                            color = TextSecondary,
                            fontSize = 15.sp,
                            lineHeight = 23.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(26.dp)
                )

                /*
                 * RESPONSE FIELD
                 */

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(
                            RoundedCornerShape(22.dp)
                        )
                        .background(SurfaceWhite)
                        .padding(18.dp)
                ) {

                    BasicTextField(
                        value = responses[currentStep],

                        onValueChange = {
                            responses[currentStep] = it
                        },

                        modifier = Modifier.fillMaxSize(),

                        textStyle = TextStyle(
                            color = TextPrimary,
                            fontSize = 15.sp,
                            lineHeight = 23.sp
                        ),

                        decorationBox = { innerTextField ->

                            if (
                                responses[currentStep].isEmpty()
                            ) {

                                Text(
                                    text = currentSense.hint,
                                    color =
                                        TextSecondary.copy(
                                            alpha = 0.65f
                                        ),
                                    fontSize = 14.sp
                                )
                            }

                            innerTextField()
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                /*
                 * NEXT / FINISH BUTTON
                 */

                Button(
                    onClick = {

                        if (
                            currentStep < senses.lastIndex
                        ) {

                            currentStep++

                        } else {

                            /*
                             * All five grounding stages
                             * have been completed.
                             *
                             * No persistence happens here.
                             */
                            isCompleted = true

                            viewModel.markCompleted()
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepPurple
                    )
                ) {

                    Text(
                        text =
                            if (
                                currentStep ==
                                senses.lastIndex
                            ) {
                                "Finish Grounding"
                            } else {
                                "Next"
                            },

                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            /*
             * FINAL COMPLETION BUTTON
             *
             * This is the ONLY point at which persistence occurs.
             */

            if (isCompleted) {

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Button(
                    onClick = {

                        /*
                         * Save the completion through the ViewModel.
                         *
                         * Navigation occurs only after the repository
                         * operation has completed successfully.
                         */
                        viewModel.saveCompletion {

                            onComplete()
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepPurple
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )

                    Text(
                        text = "Complete Grounding",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )
        }
    }
}

