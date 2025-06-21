package com.vicky7230.tasker2.navigation

import com.feature.add_edit_note.ui.navigation.AddEditNoteApi
import com.feature.notes.ui.navigation.NotesApi

data class NavigationProvider(
    val notesApi: NotesApi,
    val addEditNoteApi: AddEditNoteApi,
)
