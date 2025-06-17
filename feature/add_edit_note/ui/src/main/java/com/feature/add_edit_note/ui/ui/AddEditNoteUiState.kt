package com.feature.add_edit_note.ui.ui

import com.feature.notes.domain.model.NoteWithTag

sealed class AddEditNoteUiState {
    object Idle : AddEditNoteUiState()

    object Loading : AddEditNoteUiState()

    data class NoteData(
        val note: NoteWithTag,
    ) : AddEditNoteUiState()

    data class Error(
        val message: String,
    ) : AddEditNoteUiState()
}

sealed class AddEditNoteSideEffect {
    data object Finish : AddEditNoteSideEffect()
}
