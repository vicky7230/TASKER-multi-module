package com.feature.notes.domain.model

data class Note(
    val id: Long = 0,
    val content: String,
    val timestamp: Long,
    val tagId: Long,
    val done: Boolean,
)
