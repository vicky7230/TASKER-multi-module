package com.core.domain.model

import com.core.domain.model.Note

data class TagWithNotes(
    val id: Long = 0,
    val name: String,
    val color: String,
    val notes: List<Note>,
)
