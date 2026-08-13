package com.example.moodselector.presentations.cbt.media

import com.example.moodselector.R

data class CBTMedia(
    val backgroundMusicResId: Int? = null,
    val narrationResId: Int? = null
)

object MindfulMeditationMedia {

    val media = CBTMedia(
        backgroundMusicResId =
            R.raw.mindful_meditation,

        narrationResId =
            R.raw.mindful_meditation_voice
    )
}