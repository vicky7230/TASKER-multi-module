package com.feature.notes.ui.screen

import com.feature.notes.domain.model.NoteWithTag

sealed class NotesUiState {
    object Idle : NotesUiState()
    object Loading : NotesUiState()
    data class NotesList(val notes: List<NoteWithTag>) : NotesUiState()
    data class Error(val message: String) : NotesUiState()
}