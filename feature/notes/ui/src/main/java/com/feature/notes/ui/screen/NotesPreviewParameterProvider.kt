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
        content = "Welcome to your notes app! This is your first note."
    ),
    Note(
        id = 2,
        content = "You can add, edit, and delete notes here."
    ),
    Note(
        id = 3,
        content = "Try creating your own note by tapping the add button!"
    ),
    Note(
        id = 4,
        content = "This app uses Room database to store your notes locally."
    ),
)
