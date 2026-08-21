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

    private val userId: String?
        get() =
            authRepository
                .currentUser
                ?.uid

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

    private val _editingJournalId =
        MutableStateFlow<Int?>(null)

    val editingJournalId: StateFlow<Int?> =
        _editingJournalId.asStateFlow()

    private val _isLoadingJournal =
        MutableStateFlow(false)

    val isLoadingJournal: StateFlow<Boolean> =
        _isLoadingJournal.asStateFlow()

    /*
     * --------------------------------------------------
     * SAVING STATE
     * --------------------------------------------------
     *
     * Indicates that a journal is currently being saved.
     */

    private val _isSaving =
        MutableStateFlow(false)

    val isSaving: StateFlow<Boolean> =
        _isSaving.asStateFlow()

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

    fun updateContent(
        value: String
    ) {
        _content.value = value
    }

    fun updateMood(
        mood: String
    ) {
        _selectedMood.value = mood
    }

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

    fun loadJournal(
        journalId: Int
    ) {
        if (_editingJournalId.value == journalId) {
            return
        }

        val currentUserId =
            userId ?: return

        viewModelScope.launch {

            _isLoadingJournal.value = true

            try {

                val journal =
                    repository.getJournalById(
                        id = journalId,
                        userId = currentUserId
                    )

                if (journal != null) {

                    _editingJournalId.value =
                        journal.id

                    _content.value =
                        journal.content

                    _selectedMood.value =
                        journal.mood
                }

            } finally {

                _isLoadingJournal.value = false
            }
        }
    }

    /*
     * --------------------------------------------------
     * SAVE JOURNAL
     * --------------------------------------------------
     *
     * The save operation is completed before onComplete
     * is called.
     *
     * Room remains the local data source.
     * Firestore remains the cloud backup.
     */

    fun saveJournal(
        onComplete: (Boolean) -> Unit = {}
    ) {

        if (_isSaving.value) {
            return
        }

        val text =
            _content.value.trim()

        if (text.isBlank()) {
            onComplete(false)
            return
        }

        val currentUserId =
            userId ?: run {
                onComplete(false)
                return
            }

        viewModelScope.launch {

            _isSaving.value = true

            try {

                val editingId =
                    _editingJournalId.value

                if (editingId != null) {

                    /*
                     * --------------------------------------------------
                     * UPDATE EXISTING JOURNAL
                     * --------------------------------------------------
                     */

                    val existingJournal =
                        repository.getJournalById(
                            id = editingId,
                            userId = currentUserId
                        )

                    if (existingJournal == null) {

                        onComplete(false)
                        return@launch
                    }

                    val updatedJournal =
                        existingJournal.copy(
                            content = text,
                            mood = _selectedMood.value
                        )

                    repository.updateJournal(
                        updatedJournal
                    )

                } else {

                    /*
                     * --------------------------------------------------
                     * CREATE NEW JOURNAL
                     * --------------------------------------------------
                     */

                    val journal =
                        JournalEntity(
                            userId = currentUserId,
                            content = text,
                            mood = _selectedMood.value,
                            timestamp =
                                System.currentTimeMillis()
                        )

                    repository.insertJournal(
                        journal
                    )
                }

                /*
                 * --------------------------------------------------
                 * FIRESTORE BACKUP
                 * --------------------------------------------------
                 *
                 * Back up the updated Room data only after
                 * the local database operation has completed.
                 */

                val backupResult =
                    cloudBackupRepository
                        .backupUserData(
                            userId = currentUserId
                        )

                if (backupResult.isSuccess) {

                    clearFields()

                    onComplete(true)

                } else {

                    /*
                     * Room has already been updated, but the
                     * Firestore backup failed.
                     *
                     * Do not clear the editor state.
                     */

                    onComplete(false)
                }

            } catch (exception: Exception) {

                onComplete(false)

            } finally {

                _isSaving.value = false
            }
        }
    }

    fun deleteJournal(
        journal: JournalEntity
    ) {

        val currentUserId =
            userId ?: return

        /*
         * --------------------------------------------------
         * USER OWNERSHIP CHECK
         * --------------------------------------------------
         */

        if (journal.userId != currentUserId) {
            return
        }

        viewModelScope.launch {

            /*
             * Delete using the journal's ID and the
             * authenticated user's ID.
             */

            repository.deleteJournal(
                journalId = journal.id,
                userId = currentUserId
            )

            /*
             * Back up the updated local data after
             * deletion has completed.
             */

            cloudBackupRepository
                .backupUserData(
                    userId = currentUserId
                )
        }
    }

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

    private fun clearFields() {

        _content.value = ""

        _selectedMood.value =
            "Calm"

        _selectedTags.value =
            emptyList()

        _editingJournalId.value =
            null
    }
}

