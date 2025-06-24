package com.core.domain.model

import kotlinx.collections.immutable.PersistentList

data class TagWithNotes(
    val id: Long = 0,
    val name: String,
    val color: String,
    val notes: PersistentList<Note>,
)
