package com.example.moodselector.presentations.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.MoodEntry
import com.example.moodselector.domain.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val repository: MoodRepository
) : ViewModel() {

    // Observe all moods
    val moodList = repository.getAllMoods()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Add mood entry
    fun addMood(
        mood: String,
        emoji: String
    ) {

        val timestamp = SimpleDateFormat(
            "dd MMM yyyy • hh:mm a",
            Locale.getDefault()
        ).format(Date())

        viewModelScope.launch {

            repository.insertMood(
                MoodEntry(
                    mood = mood,
                    emoji = emoji,
                    timestamp = timestamp
                )
            )
        }
    }

    // Optional (good to have later)
    fun deleteMood(
        mood: MoodEntry
    ) {

        viewModelScope.launch {
            repository.deleteMood(mood)
        }
    }
}