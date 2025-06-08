package com.feature.notes.ui.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.feature.notes.domain.model.Note

class NotesPreviewParameterProvider : PreviewParameterProvider<NotesUiState> {
    override val values = sequenceOf(
        NotesUiState.Idle,
        NotesUiState.Loading,
        NotesUiState.Error("Error, something went wrong!"),
        NotesUiState.NotesList(notes)
    )
}

val notes = listOf(
    Note(
        id = 1,
        content = "Content 1"
    ),
    Note(
        id = 2,
        content = "Content 2"
    ),
    Note(
        id = 3,
        content = "Content 3"
    ),
    Note(
        id = 4,
        content = "Content 4"
    ),
)
