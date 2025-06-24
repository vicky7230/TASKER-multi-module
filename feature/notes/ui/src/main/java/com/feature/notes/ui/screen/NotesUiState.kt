package com.feature.notes.ui.screen

import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.PersistentList

sealed class NotesUiState {
    object Idle : NotesUiState()

    object Loading : NotesUiState()

    data class NotesLoaded(
        val notes: PersistentList<NoteWithTag>,
        val tags: PersistentList<TagWithNotes>,
    ) : NotesUiState()

    data class Error(
        val message: String,
    ) : NotesUiState()
}
