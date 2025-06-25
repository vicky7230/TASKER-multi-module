package com.feature.notes.ui.screen

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.core.domain.model.NoteWithTag
import com.core.domain.model.TagWithNotes
import kotlinx.collections.immutable.persistentListOf

class NotesPreviewParameterProvider : PreviewParameterProvider<NotesUiState> {
    override val values =
        sequenceOf(
            NotesUiState.Idle,
            NotesUiState.Loading,
            NotesUiState.Error("Error, something went wrong!"),
            NotesUiState.NotesLoaded(notes, tags),
        )
}

val notes =
    persistentListOf(
        NoteWithTag(
            id = 1,
            content = "Welcome to your notes app! This is your first note.",
            timestamp = 0,
            tagId = 1,
            done = false,
            tagName = "Work",
            tagColor = "#61DEA4",
            date = "2025-06-25",
            time = "00:00:00",
        ),
        NoteWithTag(
            id = 2,
            content = "You can add, edit, and delete notes here.",
            timestamp = 0,
            tagId = 1,
            done = false,
            tagName = "Shopping",
            tagColor = "#F45E6D",
            date = "2025-06-25",
            time = "00:00:00",
        ),
        NoteWithTag(
            id = 3,
            content = "Try creating your own note by tapping the add button!",
            timestamp = 0,
            tagId = 1,
            done = false,
            tagName = "Personal",
            tagColor = "#B678FF",
            date = "2025-06-25",
            time = "00:00:00",
        ),
        NoteWithTag(
            id = 4,
            content = "This app uses Room database to store your notes locally.",
            timestamp = 0,
            tagId = 1,
            done = false,
            tagName = "Family",
            tagColor = "#006CFF",
            date = "2025-06-25",
            time = "00:00:00",
        ),
    )

val tags =
    persistentListOf(
        TagWithNotes(
            id = 1,
            name = "Work",
            color = "#61DEA4",
            notes = persistentListOf(),
        ),
        TagWithNotes(
            id = 2,
            name = "Shopping",
            color = "#F45E6D",
            notes = persistentListOf(),
        ),
        TagWithNotes(
            id = 3,
            name = "Personal",
            color = "#B678FF",
            notes = persistentListOf(),
        ),
        TagWithNotes(
            id = 4,
            name = "Family",
            color = "#006CFF",
            notes = persistentListOf(),
        ),
    )
