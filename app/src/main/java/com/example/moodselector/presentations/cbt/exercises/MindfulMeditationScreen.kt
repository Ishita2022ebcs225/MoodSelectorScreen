package com.example.moodselector.presentations.cbt.exercises

import android.media.MediaPlayer
import android.net.Uri
import android.view.ViewGroup
import android.widget.VideoView
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moodselector.R
import com.example.moodselector.presentations.cbt.media.MindfulMeditationMedia
import kotlinx.coroutines.delay

private data class TranscriptSegment(
    val startTimeMillis: Long,
    val text: String
)

@Composable
fun MindfulMeditationScreen(
    onBackClick: () -> Unit,
    onComplete: () -> Unit,
    viewModel: MindfulMeditationViewModel =
        hiltViewModel()
) {

    val context =
        LocalContext.current

    val uiState by
    viewModel
        .uiState
        .collectAsStateWithLifecycle()

    /*
     * --------------------------------------------------
     * THEME-AWARE MEDITATION TEXT COLOR
     * --------------------------------------------------
     */

    val meditationTextColor =
        if (
            MaterialTheme.colorScheme.background.luminance() < 0.5f
        ) {
            androidx.compose.ui.graphics.Color(
                0xFF7A4FA3
            )
        } else {
            MaterialTheme.colorScheme.onBackground
        }

    /*
     * --------------------------------------------------
     * TRANSCRIPT
     * --------------------------------------------------
     */

    val transcript =
        remember {

            listOf(

                TranscriptSegment(
                    0,
                    "Welcome.\n\n" +
                            "Take a moment to settle into a comfortable position."
                ),

                TranscriptSegment(
                    5_000,
                    "You can sit upright, rest your hands gently in your lap, " +
                            "and allow your shoulders to soften."
                ),

                TranscriptSegment(
                    11_000,
                    "If it feels comfortable, gently close your eyes.\n\n" +
                            "And if you would rather keep them open,"
                ),

                TranscriptSegment(
                    17_000,
                    "simply let your gaze rest softly on one point in front of you."
                ),

                TranscriptSegment(
                    22_000,
                    "There is nothing you need to accomplish right now."
                ),

                TranscriptSegment(
                    28_000,
                    "For the next few minutes, this time is simply for you.\n\n" +
                            "Begin by noticing your breathing.\n\n" +
                            "You don't need to change it yet."
                ),

                TranscriptSegment(
                    40_000,
                    "Just notice the air moving in,\n\n" +
                            "and moving out."
                ),

                TranscriptSegment(
                    57_000,
                    "Now, take a slow, comfortable breath in through your nose,\n\n" +
                            "and slowly breathe out."
                ),

                TranscriptSegment(
                    81_000,
                    "Again, breathe in slowly,\n\n" +
                            "and breathe out gently.\n\n" +
                            "Let your shoulders become a little softer with each exhale."
                ),

                TranscriptSegment(
                    87_000,
                    "Let your shoulders become a little softer with each exhale.\n\n" +
                            "You don't need to force your breathing."
                ),

                TranscriptSegment(
                    91_000,
                    "Just allow it to become slow and comfortable.\n\n" +
                            "Now bring your attention to the physical sensation of breathing."
                ),

                TranscriptSegment(
                    101_000,
                    "Notice the movement of your chest,\n\n" +
                            "or the gentle rise and fall of your stomach."
                ),

                TranscriptSegment(
                    108_000,
                    "Notice the feeling of air entering through your nose,\n\n" +
                            "and leaving again."
                ),

                TranscriptSegment(
                    126_000,
                    "Your attention may wander.\n\n" +
                            "That's completely normal."
                ),

                TranscriptSegment(
                    130_000,
                    "There is no need to judge yourself."
                ),

                TranscriptSegment(
                    136_000,
                    "Simply notice it,\n\n" +
                            "and gently return your attention to your breath."
                ),

                TranscriptSegment(
                    164_000,
                    "Now allow your breathing to return to its natural rhythm.\n\n" +
                            "You don't need to control it."
                ),

                TranscriptSegment(
                    171_000,
                    "Simply observe it."
                ),

                TranscriptSegment(
                    186_000,
                    "Thoughts may appear.\n\n" +
                            "You might think about something that happened earlier today,\n\n" +
                            "something you need to do later,\n\n" +
                            "or something that has been worrying you."
                ),

                TranscriptSegment(
                    192_000,
                    "You don't need to solve anything right now."
                ),

                TranscriptSegment(
                    199_000,
                    "Instead, imagine each thought as something that can gently pass by."
                ),

                TranscriptSegment(
                    215_000,
                    "If another thought appears,\n\n" +
                            "that's okay.\n\n" +
                            "Notice it.\n\n" +
                            "Allow it to be there,\n\n" +
                            "and gently come back to the breath."
                ),

                TranscriptSegment(
                    233_000,
                    "There is no perfect way to meditate.\n\n" +
                            "You don't have to clear your mind.\n\n" +
                            "You don't have to stop thinking."
                ),

                TranscriptSegment(
                    240_000,
                    "The practice is simply noticing where your attention has gone,\n\n" +
                            "and kindly bringing it back."
                ),

                TranscriptSegment(
                    246_000,
                    "Now bring your attention to your body.\n\n" +
                            "Notice how you are sitting."
                ),

                TranscriptSegment(
                    264_000,
                    "Notice where your body is supported.\n\n" +
                            "Feel the surface beneath you."
                ),

                TranscriptSegment(
                    273_000,
                    "Notice your hands,\n\n" +
                            "your shoulders,\n\n" +
                            "your face,\n\n" +
                            "and your jaw."
                ),

                TranscriptSegment(
                    289_000,
                    "If you notice any tension, you don't have to make it disappear.\n\n" +
                            "Simply notice it."
                ),

                TranscriptSegment(
                    309_000,
                    "Take one slow, comfortable breath in,\n\n" +
                            "and let the breath leave your body slowly."
                ),

                TranscriptSegment(
                    341_000,
                    "For a few moments, simply be here,\n\n" +
                            "breathing,\n\n" +
                            "noticing,\n\n" +
                            "allowing."
                ),

                TranscriptSegment(
                    348_000,
                    "You are not required to change anything.\n\n" +
                            "You are simply giving yourself a few quiet moments to pause."
                ),

                TranscriptSegment(
                    365_000,
                    "Now notice your surroundings,\n\n" +
                            "the sounds around you,\n\n" +
                            "the temperature of the air,\n\n" +
                            "and the feeling of the space you are in."
                ),

                TranscriptSegment(
                    380_000,
                    "Let yourself become aware of the present moment.\n\n" +
                            "Take one final slow breath in,\n\n" +
                            "and slowly breathe out."
                ),

                TranscriptSegment(
                    400_000,
                    "Begin to notice your body again.\n\n" +
                            "Feel your hands,\n\n" +
                            "feel your feet,\n\n" +
                            "and when you are ready,\n\n" +
                            "gently open your eyes."
                ),

                TranscriptSegment(
                    408_000,
                    "Take your time.\n\n" +
                            "There is no need to rush back into the rest of your day."
                ),

                TranscriptSegment(
                    415_000,
                    "Carry this small moment of calm with you.\n\n" +
                            "And remember, you can always return to your breath whenever you need a moment to pause."
                )
            )
        }

    var currentTranscriptIndex by
    remember {
        mutableIntStateOf(0)
    }

    /*
     * --------------------------------------------------
     * AUDIO CONTROLS
     * --------------------------------------------------
     */

    var narrationVolume by
    remember {
        mutableFloatStateOf(1.0f)
    }

    var musicVolume by
    remember {
        mutableFloatStateOf(0.35f)
    }

    /*
     * --------------------------------------------------
     * NARRATION PLAYER
     * --------------------------------------------------
     */

    val narrationPlayer =
        remember {

            MediaPlayer.create(
                context,
                MindfulMeditationMedia
                    .media
                    .narrationResId!!
            )?.apply {

                setVolume(
                    narrationVolume,
                    narrationVolume
                )

                setOnCompletionListener {

                    viewModel.markCompleted()

                    currentTranscriptIndex =
                        transcript.lastIndex
                }
            }
        }

    /*
     * --------------------------------------------------
     * AMBIENT MUSIC PLAYER
     * --------------------------------------------------
     */

    val musicPlayer =
        remember {

            MediaPlayer.create(
                context,
                MindfulMeditationMedia
                    .media
                    .backgroundMusicResId!!
            )?.apply {

                isLooping = true

                setVolume(
                    musicVolume,
                    musicVolume
                )
            }
        }

    /*
     * --------------------------------------------------
     * RELEASE AUDIO
     * --------------------------------------------------
     */

    DisposableEffect(Unit) {

        onDispose {

            narrationPlayer?.apply {

                if (isPlaying) {
                    stop()
                }

                release()
            }

            musicPlayer?.apply {

                if (isPlaying) {
                    stop()
                }

                release()
            }

            viewModel.reset()
        }
    }

    /*
     * --------------------------------------------------
     * AUDIO PLAY / PAUSE / RESUME
     * --------------------------------------------------
     */

    LaunchedEffect(
        uiState.isRunning,
        uiState.isCompleted
    ) {

        if (uiState.isCompleted) {

            narrationPlayer?.let {

                if (it.isPlaying) {
                    it.pause()
                }
            }

            musicPlayer?.let {

                if (it.isPlaying) {
                    it.pause()
                }
            }

            return@LaunchedEffect
        }

        if (uiState.isRunning) {

            narrationPlayer?.let {

                if (!it.isPlaying) {
                    it.start()
                }
            }

            musicPlayer?.let {

                if (!it.isPlaying) {
                    it.start()
                }
            }

        } else {

            narrationPlayer?.let {

                if (it.isPlaying) {
                    it.pause()
                }
            }

            musicPlayer?.let {

                if (it.isPlaying) {
                    it.pause()
                }
            }
        }
    }

    /*
     * --------------------------------------------------
     * VOLUME
     * --------------------------------------------------
     */

    LaunchedEffect(narrationVolume) {

        narrationPlayer?.setVolume(
            narrationVolume,
            narrationVolume
        )
    }

    LaunchedEffect(musicVolume) {

        musicPlayer?.setVolume(
            musicVolume,
            musicVolume
        )
    }

    /*
     * --------------------------------------------------
     * SYNCHRONIZE TRANSCRIPT
     * --------------------------------------------------
     */

    LaunchedEffect(
        uiState.isRunning,
        uiState.isCompleted
    ) {

        while (
            uiState.isRunning &&
            !uiState.isCompleted
        ) {

            val currentPosition =
                narrationPlayer
                    ?.currentPosition
                    ?: 0

            var latestIndex = 0

            transcript.forEachIndexed {
                    index,
                    segment ->

                if (
                    currentPosition >=
                    segment.startTimeMillis
                ) {

                    latestIndex =
                        index
                }
            }

            currentTranscriptIndex =
                latestIndex

            delay(100L)
        }
    }

    /*
     * --------------------------------------------------
     * VIDEO BACKGROUND
     * --------------------------------------------------
     */

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                )
    ) {

        AndroidView(

            modifier =
                Modifier
                    .fillMaxSize()
                    .scale(1.25f),

            factory = { ctx ->

                VideoView(ctx).apply {

                    layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                    setVideoURI(
                        Uri.parse(
                            "android.resource://${ctx.packageName}/${R.raw.glowing_orb}"
                        )
                    )

                    setOnPreparedListener {
                            mediaPlayer ->

                        mediaPlayer.isLooping =
                            true

                        mediaPlayer.setVolume(
                            0f,
                            0f
                        )

                        mediaPlayer.setVideoScalingMode(
                            MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                        )

                        start()
                    }
                }
            }
        )

        /*
         * ------------------------------------------------
         * MAIN CONTENT
         * ------------------------------------------------
         */

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(
                        horizontal = 24.dp
                    )
                    .navigationBarsPadding(),

            verticalArrangement =
                Arrangement.Top
        ) {

            /*
             * --------------------------------------------
             * TOP BAR
             * --------------------------------------------
             */

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp
                        ),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription =
                            "Back",

                        tint =
                            meditationTextColor
                    )
                }

                Spacer(
                    modifier =
                        Modifier.weight(1f)
                )
            }

            /*
             * --------------------------------------------
             * TITLE
             * --------------------------------------------
             */

            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp
                        ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text =
                        "Mindful Meditation",

                    color =
                        meditationTextColor,

                    fontSize =
                        27.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )

                Text(
                    text =
                        when {

                            uiState.isSaved ->
                                "Meditation saved"

                            uiState.isCompleted ->
                                "Meditation complete"

                            uiState.isRunning ->
                                "Breathe. Notice. Allow."

                            uiState.hasStarted ->
                                "Meditation paused"

                            else ->
                                "Breathe. Notice. Allow."
                        },

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant,

                    fontSize =
                        14.sp
                )
            }

            /*
             * --------------------------------------------
             * TRANSCRIPT / COMPLETION CONTENT
             * --------------------------------------------
             */

            Box(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),

                contentAlignment =
                    Alignment.Center
            ) {

                if (uiState.isCompleted) {

                    Column(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 8.dp
                                ),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Check,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme.colorScheme.primary,

                            modifier =
                                Modifier.size(44.dp)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(14.dp)
                        )

                        Text(
                            text =
                                "You have completed your meditation.",

                            color =
                                meditationTextColor,

                            fontSize =
                                20.sp,

                            fontWeight =
                                FontWeight.SemiBold,

                            textAlign =
                                TextAlign.Center
                        )

                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )

                        Text(
                            text =
                                "Take a moment to notice how you feel.",

                            color =
                                MaterialTheme.colorScheme.onSurfaceVariant,

                            fontSize =
                                15.sp,

                            textAlign =
                                TextAlign.Center
                        )

                        Spacer(
                            modifier =
                                Modifier.height(22.dp)
                        )

                        OutlinedTextField(

                            value =
                                uiState.reflection,

                            onValueChange =
                                viewModel::updateReflection,

                            modifier =
                                Modifier.fillMaxWidth(),

                            label = {
                                Text(
                                    "Reflection (optional)"
                                )
                            },

                            placeholder = {
                                Text(
                                    "How do you feel after meditating?"
                                )
                            },

                            minLines = 3,

                            maxLines = 5,

                            shape =
                                RoundedCornerShape(
                                    16.dp
                                ),

                            enabled =
                                !uiState.isSaved
                        )
                    }

                } else {

                    Text(

                        text =
                            transcript[
                                currentTranscriptIndex
                            ].text,

                        color =
                            meditationTextColor,

                        fontSize =
                            19.sp,

                        lineHeight =
                            29.sp,

                        fontWeight =
                            FontWeight.Medium,

                        textAlign =
                            TextAlign.Center,

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 18.dp
                                )
                    )
                }
            }

            /*
             * --------------------------------------------
             * AUDIO CONTROLS
             * --------------------------------------------
             */

            if (!uiState.isCompleted) {

                Column(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        text =
                            "Voice Volume",

                        color =
                            meditationTextColor,

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.SemiBold,

                        modifier =
                            Modifier.padding(
                                start = 4.dp,
                                bottom = 2.dp
                            )
                    )

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.VolumeDown,

                            contentDescription =
                                "Narration volume",

                            tint =
                                meditationTextColor,

                            modifier =
                                Modifier.size(18.dp)
                        )

                        Slider(

                            value =
                                narrationVolume,

                            onValueChange = {
                                narrationVolume = it
                            },

                            valueRange =
                                0f..1f,

                            modifier =
                                Modifier.weight(1f)
                        )

                        Icon(
                            imageVector =
                                Icons.Default.VolumeUp,

                            contentDescription =
                                null,

                            tint =
                                meditationTextColor,

                            modifier =
                                Modifier.size(18.dp)
                        )
                    }
                }

                Column(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        text =
                            "Music Volume",

                        color =
                            meditationTextColor,

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.SemiBold,

                        modifier =
                            Modifier.padding(
                                start = 4.dp,
                                bottom = 2.dp
                            )
                    )

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.VolumeDown,

                            contentDescription =
                                "Ambient music volume",

                            tint =
                                meditationTextColor,

                            modifier =
                                Modifier.size(18.dp)
                        )

                        Slider(

                            value =
                                musicVolume,

                            onValueChange = {
                                musicVolume = it
                            },

                            valueRange =
                                0f..1f,

                            modifier =
                                Modifier.weight(1f)
                        )

                        Icon(
                            imageVector =
                                Icons.Default.VolumeUp,

                            contentDescription =
                                null,

                            tint =
                                meditationTextColor,

                            modifier =
                                Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            /*
             * --------------------------------------------
             * MAIN ACTION
             * --------------------------------------------
             */

            Button(

                onClick = {

                    when {

                        uiState.isSaved -> {

                            onComplete()
                        }

                        uiState.isCompleted -> {

                            viewModel.saveCompletion(
                                onSaved = onComplete
                            )
                        }

                        uiState.isRunning -> {

                            viewModel.pauseMeditation()
                        }

                        uiState.hasStarted -> {

                            viewModel.resumeMeditation()
                        }

                        else -> {

                            narrationPlayer?.seekTo(0)

                            musicPlayer?.seekTo(0)

                            currentTranscriptIndex = 0

                            viewModel.startMeditation()
                        }
                    }
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                shape =
                    RoundedCornerShape(18.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.primary
                    )
            ) {

                Icon(

                    imageVector =
                        when {

                            uiState.isSaved ->
                                Icons.Default.Check

                            uiState.isCompleted ->
                                Icons.Default.Check

                            uiState.isRunning ->
                                Icons.Default.Pause

                            uiState.hasStarted ->
                                Icons.Default.PlayArrow

                            else ->
                                Icons.Default.PlayArrow
                        },

                    contentDescription =
                        null
                )

                Spacer(
                    modifier =
                        Modifier.size(8.dp)
                )

                Text(

                    text =
                        when {

                            uiState.isSaved ->
                                "Completed"

                            uiState.isCompleted ->
                                "Complete Meditation"

                            uiState.isRunning ->
                                "Pause Meditation"

                            uiState.hasStarted ->
                                "Resume Meditation"

                            else ->
                                "Begin Meditation"
                        },

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )
        }
    }
}
