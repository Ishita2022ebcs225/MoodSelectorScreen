package com.example.moodselector.presentations.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodselector.data.local.entity.JournalEntity
import com.example.moodselector.domain.repository.AuthRepository
import com.example.moodselector.domain.repository.CloudBackupRepository
import com.example.moodselector.domain.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val repository: JournalRepository,
    private val authRepository: AuthRepository,
    private val cloudBackupRepository: CloudBackupRepository
) : ViewModel() {

    /*
     * --------------------------------------------------
     * CURRENT USER ID
     * --------------------------------------------------
     */

    private val userId: String?
        get() = authRepository.currentUser?.uid


    // 🧠 UI STATE

    private val _content =
        MutableStateFlow("")

    val content: StateFlow<String> =
        _content.asStateFlow()


    private val _selectedMood =
        MutableStateFlow("Calm")

    val selectedMood: StateFlow<String> =
        _selectedMood.asStateFlow()


    private val _selectedTags =
        MutableStateFlow<List<String>>(emptyList())

    val selectedTags: StateFlow<List<String>> =
        _selectedTags.asStateFlow()


    // 📓 LIVE FEED

    val journals: StateFlow<List<JournalEntity>> =
        repository
            .getAllJournals(
                userId = userId ?: ""
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    /*
     * --------------------------------------------------
     * UPDATE CONTENT
     * --------------------------------------------------
     */

    fun updateContent(
        value: String
    ) {
        _content.value = value
    }


    /*
     * --------------------------------------------------
     * UPDATE MOOD
     * --------------------------------------------------
     */

    fun updateMood(
        mood: String
    ) {
        _selectedMood.value = mood
    }


    /*
     * --------------------------------------------------
     * TOGGLE EMOTION TAG
     * --------------------------------------------------
     */

    fun toggleEmotionTag(
        tag: String
    ) {

        _selectedTags.value =
            if (_selectedTags.value.contains(tag)) {

                _selectedTags.value - tag

            } else {

                _selectedTags.value + tag
            }
    }


    /*
     * --------------------------------------------------
     * SAVE JOURNAL
     * --------------------------------------------------
     */

    fun saveJournal() {

        val text =
            _content.value.trim()

        if (text.isBlank()) {
            return
        }

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            val journal =
                JournalEntity(
                    userId = currentUserId,
                    content = text,
                    mood = _selectedMood.value,
                    timestamp = System.currentTimeMillis()
                )

            repository.insertJournal(
                journal
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )

            clearFields()
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ONE JOURNAL
     * --------------------------------------------------
     */

    fun deleteJournal(
        journal: JournalEntity
    ) {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteJournal(
                journal
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }


    /*
     * --------------------------------------------------
     * DELETE ALL JOURNALS
     * --------------------------------------------------
     */

    fun deleteAllJournals() {

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            repository.deleteAllJournals(
                currentUserId
            )

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }


    /*
     * --------------------------------------------------
     * CLEAR FIELDS
     * --------------------------------------------------
     */

    private fun clearFields() {

        _content.value = ""

        _selectedMood.value =
            "Calm"

        _selectedTags.value =
            emptyList()
    }
}

