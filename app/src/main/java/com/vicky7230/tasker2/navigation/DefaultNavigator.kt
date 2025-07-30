package com.vicky7230.tasker2.navigation

import com.feature.add_edit_note.ui.navigation.AddEditNoteFeature
import com.feature.notes.ui.navigation.NotesFeature
import com.feature.tags.ui.navigation.TagsFeature

data class DefaultNavigator(
    val notesFeature: NotesFeature,
    val addEditNoteFeature: AddEditNoteFeature,
    val tagsFeature: TagsFeature,
)
