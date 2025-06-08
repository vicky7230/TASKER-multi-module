package com.feature.add_edit_note.ui.ui

import com.feature.notes.domain.model.Note

sealed class AddEditNoteUiState {
    object Idle : AddEditNoteUiState()
    object Loading : AddEditNoteUiState()
    data class NoteData(val note: Note) : AddEditNoteUiState()
    data class Error(val message: String) : AddEditNoteUiState()
}