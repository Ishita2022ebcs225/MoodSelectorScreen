package com.example.moodselector.presentations.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.domain.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val repository: JournalRepository
) : ViewModel() {

    // 🧠 UI STATE
    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _selectedMood = MutableStateFlow("Calm")
    val selectedMood: StateFlow<String> = _selectedMood.asStateFlow()

    private val _selectedTags = MutableStateFlow<List<String>>(emptyList())
    val selectedTags: StateFlow<List<String>> = _selectedTags.asStateFlow()

    // 📓 LIVE FEED
    val journals: StateFlow<List<JournalEntity>> =
        repository.getAllJournals()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun updateContent(value: String) {
        _content.value = value
    }

    fun updateMood(mood: String) {
        _selectedMood.value = mood
    }

    fun toggleEmotionTag(tag: String) {
        _selectedTags.value =
            if (_selectedTags.value.contains(tag)) {
                _selectedTags.value - tag
            } else {
                _selectedTags.value + tag
            }
    }

    // 💾 SAVE JOURNAL (FIXED)
    fun saveJournal() {

        val text = _content.value.trim()

        if (text.isBlank()) return

        viewModelScope.launch {

            val journal = JournalEntity(
                content = text,
                mood = _selectedMood.value,
                timestamp = System.currentTimeMillis()
            )

            repository.insertJournal(journal)

            clearFields()
        }
    }

    fun deleteJournal(journal: JournalEntity) {
        viewModelScope.launch {
            repository.deleteJournal(journal)
        }
    }

    fun deleteAllJournals() {
        viewModelScope.launch {
            repository.deleteAllJournals()
        }
    }

    private fun clearFields() {
        _content.value = ""
        _selectedMood.value = "Calm"
        _selectedTags.value = emptyList()
    }
}