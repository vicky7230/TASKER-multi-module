package com.feature.notes.ui.screen

import com.feature.notes.domain.model.Note

sealed class NotesUiState {
    object Idle : NotesUiState()
    object Loading : NotesUiState()
    data class NotesList(val notes: List<Note>) : NotesUiState()
    data class Error(val message: String) : NotesUiState()
}