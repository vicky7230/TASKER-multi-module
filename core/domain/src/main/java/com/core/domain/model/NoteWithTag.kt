package com.core.domain.model

data class NoteWithTag(
    val id: Long = 0,
    val content: String,
    val timestamp: Long,
    val done: Boolean,
    val tagId: Long,
    val tagName: String,
    val tagColor: String,
)
