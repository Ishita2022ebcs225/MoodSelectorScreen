package com.example.moodselector.presentations.journal

import com.example.moodselector.data.local.entity.JournalEntity

data class JournalUiState(

    // 📝 Current editor content
    val content: String = "",

    // 🎭 Optional emotional context
    val selectedMood: String? = null,

    // 🏷 Optional reflection tags
    val emotionTags: String = "",

    // 📓 Full journal timeline
    val journals: List<JournalEntity> = emptyList(),

    // ⏳ Loading state
    val isLoading: Boolean = false,

    // ✅ Entry successfully saved
    val isSaved: Boolean = false,

    // ⚠ Error handling
    val errorMessage: String? = null
)