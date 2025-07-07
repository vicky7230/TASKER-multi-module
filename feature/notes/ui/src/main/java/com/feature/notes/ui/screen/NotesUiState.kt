package com.feature.notes.ui.screen

import androidx.compose.ui.graphics.Color
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.PersistentList

sealed class NotesUiState {
    data object Idle : NotesUiState()

    data object Loading : NotesUiState()

    data class NotesLoaded(
        val notes: PersistentList<NoteWithTag>,
        val tags: PersistentList<TagWithNotes>,
        val fabExpanded: Boolean = false,
        val bottomSheet: NotesUiBottomSheet = NotesUiBottomSheet.None,
    ) : NotesUiState()

    data class Error(
        val message: String,
    ) : NotesUiState()
}

sealed class NotesUiBottomSheet {
    data object None : NotesUiBottomSheet()

    data class CreateTagBottomSheet(
        val selectedColor: Color,
    ) : NotesUiBottomSheet()
}
