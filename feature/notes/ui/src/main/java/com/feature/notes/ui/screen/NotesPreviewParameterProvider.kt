package com.feature.notes.ui.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.feature.notes.domain.model.NoteWithTag
import com.feature.notes.domain.model.TagWithNotes

class NotesPreviewParameterProvider : PreviewParameterProvider<NotesUiState> {
    override val values = sequenceOf(
        NotesUiState.Idle,
        NotesUiState.Loading,
        NotesUiState.Error("Error, something went wrong!"),
        NotesUiState.NotesList(notes, tags)
    )
}

val notes = listOf(
    NoteWithTag(
        id = 1,
        content = "Welcome to your notes app! This is your first note.",
        timestamp = 0,
        tagId = 1,
        done = false,
        tagName = "Work",
        tagColor = "#61DEA4"
    ),
    NoteWithTag(
        id = 2,
        content = "You can add, edit, and delete notes here.",
        timestamp = 0,
        tagId = 1,
        done = false,
        tagName = "Shopping",
        tagColor = "#F45E6D"
    ),
    NoteWithTag(
        id = 3,
        content = "Try creating your own note by tapping the add button!",
        timestamp = 0,
        tagId = 1,
        done = false,
        tagName = "Personal",
        tagColor = "#B678FF"
    ),
    NoteWithTag(
        id = 4,
        content = "This app uses Room database to store your notes locally.",
        timestamp = 0,
        tagId = 1,
        done = false,
        tagName = "Family",
        tagColor = "#FFE761"
    ),
)

val tags = listOf(
    TagWithNotes(
        id = 1,
        name = "Work",
        color = "#61DEA4",
        notes = emptyList()
    ),
    TagWithNotes(
        id = 1,
        name = "Shopping",
        color = "#F45E6D",
        notes = emptyList()
    ),
    TagWithNotes(
        id = 1,
        name = "Personal",
        color = "#B678FF",
        notes = emptyList()
    ),
    TagWithNotes(
        id = 1,
        name = "Family",
        color = "#FFE761",
        notes = emptyList()
    ),
)
