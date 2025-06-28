package com.vicky7230.tasker2.navigation

import com.feature.add_edit_note.ui.navigation.AddEditNoteApi
import com.feature.notes.ui.navigation.NotesApi
import com.feature.tags.ui.navigation.TagsApi

data class NavigationProvider(
    val notesApi: NotesApi,
    val addEditNoteApi: AddEditNoteApi,
    val tagsApi: TagsApi,
)
