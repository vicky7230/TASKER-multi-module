package com.feature.notes.domain.model

data class TagWithNotes(
    val id: Long = 0,
    val name: String,
    val color: String,
    val notes: List<Note>,
)
