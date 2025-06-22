package com.feature.notes.ui.screen

import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes

sealed class NotesUiState {
    object Idle : NotesUiState()

    object Loading : NotesUiState()

    data class NotesLoaded(
        val notes: List<NoteWithTag>,
        val tags: List<TagWithNotes>,
    ) : NotesUiState()

    data class Error(
        val message: String,
    ) : NotesUiState()
}
