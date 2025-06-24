package com.feature.add_edit_note.ui.ui

import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.PersistentList

sealed class AddEditNoteUiState {
    object Idle : AddEditNoteUiState()

    object Loading : AddEditNoteUiState()

    data class NoteAndTags(
        val note: NoteWithTag,
        val tags: PersistentList<TagWithNotes>,
    ) : AddEditNoteUiState()

    data class Error(
        val message: String,
    ) : AddEditNoteUiState()
}

sealed class AddEditNoteSideEffect {
    data object Finish : AddEditNoteSideEffect()
}
