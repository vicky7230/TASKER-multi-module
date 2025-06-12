package com.feature.notes.ui.screen

import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.model.TagWithNotes

sealed class NotesUiState {
    object Idle : NotesUiState()
    object Loading : NotesUiState()
    data class NotesList(val notes: List<NoteWithTag>, val tags: List<TagWithNotes> ) : NotesUiState()
    data class Error(val message: String) : NotesUiState()
}