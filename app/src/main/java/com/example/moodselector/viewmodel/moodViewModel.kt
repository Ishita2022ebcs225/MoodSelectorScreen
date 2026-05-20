package com.example.moodselector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.MoodEntry
import com.example.moodselector.repository.MoodRepository
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

    val moodList = repository.allMoods
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addMood(mood: String, emoji: String) {

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
}