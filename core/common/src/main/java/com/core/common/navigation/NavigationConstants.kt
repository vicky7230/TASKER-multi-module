package com.core.common.navigation

import kotlinx.serialization.Serializable

@Serializable
object NotesGraph

@Serializable
object NotesScreen

@Serializable
object AddEditNoteGraph

@Serializable
data class AddEditNoteScreen(val noteId: Long)
